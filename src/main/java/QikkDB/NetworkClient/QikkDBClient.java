package QikkDB.NetworkClient;

import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import QikkDB.NetworkClient.Message.*;
import QikkDB.NetworkClient.Message.BulkImportMessageOuterClass.BulkImportMessage;
import QikkDB.NetworkClient.Message.CSVImportMessageOuterClass.DataType;
import QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage;
import QikkDB.NetworkClient.Message.QueryMessageOuterClass.QueryMessage;
import QikkDB.NetworkClient.Message.QueryResponseMessageOuterClass.QueryResponseMessage;
import QikkDB.NetworkClient.Message.QueryResponseMessageOuterClass.QueryResponsePayload;
import QikkDB.NetworkClient.Message.SetDatabaseMessageOuterClass.SetDatabaseMessage;
import QikkDB.Types.QComplexPolygon;
import QikkDB.Types.QPoint;
import QikkDB.Types.ComplexPolygonOuterClass.ComplexPolygon;
import QikkDB.Types.PointOuterClass.Point;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public class QikkDBClient {
    private String mServerIP;
    private int mServerPort;
    private Socket mSocket;
    private static final int BULK_IMPORT_FRAGMENT_SIZE = 8192 * 1024;

    public QikkDBClient(String serverIP, int serverPort) {
        mServerIP = serverIP;
        mServerPort = serverPort;
    }

    public void Connect() throws IOException {
        mSocket = new Socket(mServerIP, mServerPort);
        mSocket.setTcpNoDelay(true);

        InfoMessage msg = InfoMessage.newBuilder().setCode(InfoMessage.StatusCode.CONN_ESTABLISH).setMessage("")
                .build();

        NetworkMessage.WriteToNetwork(msg, mSocket.getOutputStream());
        Any responsePackedMsg = NetworkMessage.ReadFromNetwork(mSocket.getInputStream());

        try {
            try {
                InfoMessage responseMsg = responsePackedMsg.unpack(InfoMessage.class);
                if (responseMsg.getCode() != InfoMessage.StatusCode.OK) {
                    throw new IOException("Invalid response received from server");
                }
            } catch (InvalidProtocolBufferException ex) {
                throw new IOException("Invalid response received from server");
            }
        } catch (IOException ex) {
            CloseWithoutNotify();
            throw ex;
        }

    }

    public void Query(String query) throws IOException, QueryException {
        QueryMessage message = QueryMessage.newBuilder().setQuery(query).build();

        try {
            NetworkMessage.WriteToNetwork(message, mSocket.getOutputStream());

            Any serverPackedMessage = NetworkMessage.ReadFromNetwork(mSocket.getInputStream());
            try {
                InfoMessage responseMsg = serverPackedMessage.unpack(InfoMessage.class);
                if (responseMsg.getCode() != InfoMessage.StatusCode.WAIT) {
                    throw new IOException("Invalid response received from server");
                }
            } catch (InvalidProtocolBufferException ex) {
                throw new IOException("Invalid response received from server");
            }

            Any inputPackedMessage = NetworkMessage.ReadFromNetwork(mSocket.getInputStream());
            try {
                InfoMessage inResponse = inputPackedMessage.unpack(InfoMessage.class);
                if (inResponse.getCode() != InfoMessage.StatusCode.GET_NEXT_RESULT) {
                    throw new QueryException(inResponse.getMessage());
                }
            } catch (InvalidProtocolBufferException ex) {
                throw new IOException("Invalid response received from server");
            }

        } catch (IOException e) {
            CloseWithoutNotify();
            throw e;
        }
    }

    public void UseDatabase(String databaseName) throws IOException, QueryException {
        SetDatabaseMessage message = SetDatabaseMessage.newBuilder().setDatabaseName(databaseName).build();

        try {
            NetworkMessage.WriteToNetwork(message, mSocket.getOutputStream());
            Any responsePackedMessage = NetworkMessage.ReadFromNetwork(mSocket.getInputStream());

            try {
                InfoMessage response = responsePackedMessage.unpack(InfoMessage.class);
                if (response.getCode() != InfoMessage.StatusCode.OK) {
                    throw new QueryException(response.getMessage());
                }
            } catch (InvalidProtocolBufferException ex) {
                throw new IOException("Invalid response received from server");
            }

        } catch (IOException e) {
            CloseWithoutNotify();
            throw e;
        }

    }

    private <T extends Object> T ValIfNotNulled(T val, int idx, List<Long> nullMask) {
        int shiftOffset = idx % 64;
        int byteOffset = idx / 64;
        if ((nullMask.get(byteOffset) >> shiftOffset & 1) == 0) {
            return val;
        } else {
            return null;
        }
    }

    private ColumnarDataTable ConvertToDictionaries(QueryResponseMessage response) {
        ArrayList<String> columnNames = new ArrayList<String>();
        Hashtable<String, ArrayList> columnDatas = new Hashtable<String, ArrayList>();
        Hashtable<String, Class> columnTypes = new Hashtable<String, Class>();
        ArrayList<String> orderedColumnNames = new ArrayList<String>();

        for (String column : response.getColumnOrderList()) {
            orderedColumnNames.add(column);
        }

        for (Map.Entry<String, QueryResponseMessageOuterClass.QueryResponsePayload> columnData : response
                .getPayloadsMap().entrySet()) {
            columnNames.add(columnData.getKey());
            switch (columnData.getValue().getPayloadCase()) {
                case INTPAYLOAD:
                    columnDatas.put(columnData.getKey(),
                            new ArrayList<Integer>(columnData.getValue().getIntPayload().getIntDataList()));
                    List<Integer> valuesInt = (List<Integer>) columnDatas.get(columnData.getKey());
                    if (response.getNullBitMasksMap().containsKey(columnData.getKey())) {
                        List<Long> nullMask = response.getNullBitMasksMap().get(columnData.getKey()).getNullMaskList();
                        for (int i = 0; i < valuesInt.size(); i++) {
                            valuesInt.set(i, ValIfNotNulled(valuesInt.get(i), i, nullMask));
                        }
                    }
                    break;
                case INT64PAYLOAD:
                    columnDatas.put(columnData.getKey(),
                            new ArrayList<Long>(columnData.getValue().getInt64Payload().getInt64DataList()));
                    List<Long> valuesInt64 = (List<Long>) columnDatas.get(columnData.getKey());
                    if (response.getNullBitMasksMap().containsKey(columnData.getKey())) {
                        List<Long> nullMask = response.getNullBitMasksMap().get(columnData.getKey()).getNullMaskList();
                        for (int i = 0; i < valuesInt64.size(); i++) {
                            valuesInt64.set(i, ValIfNotNulled(valuesInt64.get(i), i, nullMask));
                        }
                    }
                    break;
                case FLOATPAYLOAD:
                    columnDatas.put(columnData.getKey(),
                            new ArrayList<Float>(columnData.getValue().getFloatPayload().getFloatDataList()));
                    List<Float> valuesFloat = (List<Float>) columnDatas.get(columnData.getKey());
                    if (response.getNullBitMasksMap().containsKey(columnData.getKey())) {
                        List<Long> nullMask = response.getNullBitMasksMap().get(columnData.getKey()).getNullMaskList();
                        for (int i = 0; i < valuesFloat.size(); i++) {
                            valuesFloat.set(i, ValIfNotNulled(valuesFloat.get(i), i, nullMask));
                        }
                    }
                    break;
                case DOUBLEPAYLOAD:
                    columnDatas.put(columnData.getKey(),
                            new ArrayList<Double>(columnData.getValue().getDoublePayload().getDoubleDataList()));
                    List<Double> valuesDouble = (List<Double>) columnDatas.get(columnData.getKey());
                    if (response.getNullBitMasksMap().containsKey(columnData.getKey())) {
                        List<Long> nullMask = response.getNullBitMasksMap().get(columnData.getKey()).getNullMaskList();
                        for (int i = 0; i < valuesDouble.size(); i++) {
                            valuesDouble.set(i, ValIfNotNulled(valuesDouble.get(i), i, nullMask));
                        }
                    }
                    break;
                case POINTPAYLOAD:
                    columnDatas.put(columnData.getKey(),
                            new ArrayList<Point>(columnData.getValue().getPointPayload().getPointDataList()));
                    List<Point> valuesPoint = (List<Point>) columnDatas.get(columnData.getKey());
                    if (response.getNullBitMasksMap().containsKey(columnData.getKey())) {
                        List<Long> nullMask = response.getNullBitMasksMap().get(columnData.getKey()).getNullMaskList();
                        for (int i = 0; i < valuesPoint.size(); i++) {
                            valuesPoint.set(i, ValIfNotNulled(valuesPoint.get(i), i, nullMask));
                        }
                    }
                    break;
                case POLYGONPAYLOAD:
                    columnDatas.put(columnData.getKey(), new ArrayList<ComplexPolygon>(
                            columnData.getValue().getPolygonPayload().getPolygonDataList()));
                    List<ComplexPolygon> valuesPolygon = (List<ComplexPolygon>) columnDatas.get(columnData.getKey());
                    if (response.getNullBitMasksMap().containsKey(columnData.getKey())) {
                        List<Long> nullMask = response.getNullBitMasksMap().get(columnData.getKey()).getNullMaskList();
                        for (int i = 0; i < valuesPolygon.size(); i++) {
                            valuesPolygon.set(i, ValIfNotNulled(valuesPolygon.get(i), i, nullMask));
                        }
                    }
                    break;
                case STRINGPAYLOAD:
                    columnDatas.put(columnData.getKey(),
                            new ArrayList<String>(columnData.getValue().getStringPayload().getStringDataList()));
                    List<String> valuesString = (List<String>) columnDatas.get(columnData.getKey());
                    if (response.getNullBitMasksMap().containsKey(columnData.getKey())) {
                        List<Long> nullMask = response.getNullBitMasksMap().get(columnData.getKey()).getNullMaskList();
                        for (int i = 0; i < valuesString.size(); i++) {
                            valuesString.set(i, ValIfNotNulled(valuesString.get(i), i, nullMask));
                        }
                    }
                    break;
            }
        }

        ColumnarDataTable ret = new ColumnarDataTable(columnNames, columnDatas, columnTypes, orderedColumnNames);
        return ret;
    }

    public Pair<ColumnarDataTable, Dictionary<String, Float>> GetNextQueryResult() throws IOException, QueryException {
        InfoMessage msg = InfoMessage.newBuilder().setCode(InfoMessage.StatusCode.GET_NEXT_RESULT).setMessage("")
                .build();

        try {
            NetworkMessage.WriteToNetwork(msg, mSocket.getOutputStream());
            Any resultPackedMsg = NetworkMessage.ReadFromNetwork(mSocket.getInputStream());
            try {
                QueryResponseMessage queryResult = resultPackedMsg.unpack(QueryResponseMessage.class);

                ColumnarDataTable resultSet = ConvertToDictionaries(queryResult);
                Hashtable<String, Float> timingResult = new Hashtable<String, Float>();
                for (Map.Entry<String, Float> timing : queryResult.getTimingMap().entrySet()) {
                    timingResult.put(timing.getKey(), timing.getValue());
                }
                return new Pair(resultSet, timingResult);

            } catch (InvalidProtocolBufferException ex1) {
                try {
                    InfoMessage errorResponse = resultPackedMsg.unpack(InfoMessage.class);
                    if (errorResponse.getCode() != InfoMessage.StatusCode.OK) {
                        throw new QueryException(errorResponse.getMessage());
                    } else {
                        return new Pair<ColumnarDataTable, Dictionary<String, Float>>(null, null);
                    }
                } catch (InvalidProtocolBufferException ex2) {
                    throw new IOException("Invalid response received from server");
                }
            }

        } catch (IOException e) {
            CloseWithoutNotify();
            throw e;
        }
    }

    public static final int SIZE_OF_BYTE = 1;
    public static final int SIZE_OF_INT = 4;
    public static final int SIZE_OF_LONG = 8;
    public static final int SIZE_OF_FLOAT = 4;
    public static final int SIZE_OF_DOUBLE = 8;

    public void BulkImport(ColumnarDataTable dataTable) throws QueryException {
        ArrayList<String> columnNames = dataTable.GetColumnNames();
        String tableName = dataTable.GetTableName();
        Dictionary<String, Class> types = dataTable.GetColumnTypes();

        for (String column : columnNames) {
            Class type = types.get(column);
            DataType columnType = DataType.COLUMN_INT;
            int size = dataTable.GetSize();
            int elementCount = size;
            int typeSize = 1;
            byte[] dataBuffer = null;
            byte[] nullMask = null;
            int i = 0;

            if (type == Boolean.class) {
                columnType = DataType.COLUMN_INT8_T;
                dataBuffer = new byte[size];
                ArrayList<Boolean> list = (ArrayList<Boolean>) dataTable.GetColumnData().get(column);
                for (i = 0; i < size; i++) {
                    if (list.get(i) == null) {
                        if (nullMask == null) {
                            int nullMaskSize = (size + SIZE_OF_BYTE * 8 - 1) / (SIZE_OF_BYTE * 8);
                            nullMask = new byte[nullMaskSize];
                            for (int j = 0; j < nullMaskSize; j++) {
                                nullMask[j] = 0;
                            }
                        }
                        int byteIdx = i / (SIZE_OF_BYTE * 8);
                        int shiftIdx = i % (SIZE_OF_BYTE * 8);
                        nullMask[byteIdx] |= (byte) (1 << shiftIdx);
                        dataBuffer[i] = 0;
                    } else {
                        dataBuffer[i] = list.get(i) ? (byte) 1 : (byte) 0;
                    }
                }
            } else if (type == Byte.class) {
                columnType = DataType.COLUMN_INT8_T;
                dataBuffer = new byte[size];
                ArrayList<Byte> list = (ArrayList<Byte>) dataTable.GetColumnData().get(column);
                for (i = 0; i < size; i++) {
                    if (list.get(i) == null) {
                        if (nullMask == null) {
                            int nullMaskSize = (size + SIZE_OF_BYTE * 8 - 1) / (SIZE_OF_BYTE * 8);
                            nullMask = new byte[nullMaskSize];
                            for (int j = 0; j < nullMaskSize; j++) {
                                nullMask[j] = 0;
                            }
                        }
                        int byteIdx = i / (SIZE_OF_BYTE * 8);
                        int shiftIdx = i % (SIZE_OF_BYTE * 8);
                        nullMask[byteIdx] |= (byte) (1 << shiftIdx);
                        dataBuffer[i] = 0;
                    } else {
                        dataBuffer[i] = list.get(i);
                    }
                }
            } else if (type == Integer.class) {
                columnType = DataType.COLUMN_INT;
                size *= SIZE_OF_INT;
                typeSize = SIZE_OF_INT;
                dataBuffer = new byte[size];
                ArrayList<Integer> list = (ArrayList<Integer>) dataTable.GetColumnData().get(column);
                for (i = 0; i < elementCount; i++) {
                    if (list.get(i) == null) {
                        if (nullMask == null) {
                            int nullMaskSize = (elementCount + SIZE_OF_BYTE * 8 - 1) / (SIZE_OF_BYTE * 8);
                            nullMask = new byte[nullMaskSize];
                            for (int j = 0; j < nullMaskSize; j++) {
                                nullMask[j] = 0;
                            }
                        }
                        int byteIdx = i / (SIZE_OF_BYTE * 8);
                        int shiftIdx = i % (SIZE_OF_BYTE * 8);
                        nullMask[byteIdx] |= (byte) (1 << shiftIdx);
                        for (int j = 0; j < SIZE_OF_INT; j++) {
                            dataBuffer[SIZE_OF_INT * i + j] = 0;
                        }
                    } else {
                        int elem = list.get(i);
                        byte[] elemBytes = ByteBuffer.allocate(SIZE_OF_INT).order(ByteOrder.LITTLE_ENDIAN).putInt(elem)
                                .array();

                        for (int j = 0; j < SIZE_OF_INT; j++) {
                            dataBuffer[SIZE_OF_INT * i + j] = elemBytes[j];
                        }
                    }
                }
            } else if (type == Long.class) {
                columnType = DataType.COLUMN_LONG;
                size *= SIZE_OF_LONG;
                typeSize = SIZE_OF_LONG;
                dataBuffer = new byte[size];
                ArrayList<Long> list = (ArrayList<Long>) dataTable.GetColumnData().get(column);
                for (i = 0; i < elementCount; i++) {
                    if (list.get(i) == null) {
                        if (nullMask == null) {
                            int nullMaskSize = (elementCount + SIZE_OF_BYTE * 8 - 1) / (SIZE_OF_BYTE * 8);
                            nullMask = new byte[nullMaskSize];
                            for (int j = 0; j < nullMaskSize; j++) {
                                nullMask[j] = 0;
                            }
                        }
                        int byteIdx = i / (SIZE_OF_BYTE * 8);
                        int shiftIdx = i % (SIZE_OF_BYTE * 8);
                        nullMask[byteIdx] |= (byte) (1 << shiftIdx);
                        for (int j = 0; j < SIZE_OF_LONG; j++) {
                            dataBuffer[SIZE_OF_LONG * i + j] = 0;
                        }
                    } else {
                        long elem = list.get(i);
                        byte[] elemBytes = ByteBuffer.allocate(SIZE_OF_LONG).order(ByteOrder.LITTLE_ENDIAN)
                                .putLong(elem).array();

                        for (int j = 0; j < SIZE_OF_LONG; j++) {
                            dataBuffer[SIZE_OF_LONG * i + j] = elemBytes[j];
                        }
                    }
                }
            } else if (type == Float.class) {
                columnType = DataType.COLUMN_FLOAT;
                size *= SIZE_OF_FLOAT;
                typeSize = SIZE_OF_FLOAT;
                dataBuffer = new byte[size];
                ArrayList<Float> list = (ArrayList<Float>) dataTable.GetColumnData().get(column);
                for (i = 0; i < elementCount; i++) {
                    if (list.get(i) == null) {
                        if (nullMask == null) {
                            int nullMaskSize = (elementCount + SIZE_OF_BYTE * 8 - 1) / (SIZE_OF_BYTE * 8);
                            nullMask = new byte[nullMaskSize];
                            for (int j = 0; j < nullMaskSize; j++) {
                                nullMask[j] = 0;
                            }
                        }
                        int byteIdx = i / (SIZE_OF_BYTE * 8);
                        int shiftIdx = i % (SIZE_OF_BYTE * 8);
                        nullMask[byteIdx] |= (byte) (1 << shiftIdx);
                        for (int j = 0; j < SIZE_OF_FLOAT; j++) {
                            dataBuffer[SIZE_OF_FLOAT * i + j] = 0;
                        }
                    } else {
                        float elem = list.get(i);
                        byte[] elemBytes = ByteBuffer.allocate(SIZE_OF_FLOAT).order(ByteOrder.LITTLE_ENDIAN)
                                .putFloat(elem).array();

                        for (int j = 0; j < SIZE_OF_FLOAT; j++) {
                            dataBuffer[SIZE_OF_FLOAT * i + j] = elemBytes[j];
                        }
                    }
                }
            } else if (type == Double.class) {
                columnType = DataType.COLUMN_DOUBLE;
                size *= SIZE_OF_DOUBLE;
                typeSize = SIZE_OF_DOUBLE;
                dataBuffer = new byte[size];
                ArrayList<Double> list = (ArrayList<Double>) dataTable.GetColumnData().get(column);
                for (i = 0; i < elementCount; i++) {
                    if (list.get(i) == null) {
                        if (nullMask == null) {
                            int nullMaskSize = (elementCount + SIZE_OF_BYTE * 8 - 1) / (SIZE_OF_BYTE * 8);
                            nullMask = new byte[nullMaskSize];
                            for (int j = 0; j < nullMaskSize; j++) {
                                nullMask[j] = 0;
                            }
                        }
                        int byteIdx = i / (SIZE_OF_BYTE * 8);
                        int shiftIdx = i % (SIZE_OF_BYTE * 8);
                        nullMask[byteIdx] |= (byte) (1 << shiftIdx);
                        for (int j = 0; j < SIZE_OF_DOUBLE; j++) {
                            dataBuffer[SIZE_OF_DOUBLE * i + j] = 0;
                        }
                    } else {
                        double elem = list.get(i);
                        byte[] elemBytes = ByteBuffer.allocate(SIZE_OF_DOUBLE).order(ByteOrder.LITTLE_ENDIAN)
                                .putDouble(elem).array();

                        for (int j = 0; j < SIZE_OF_DOUBLE; j++) {
                            dataBuffer[SIZE_OF_DOUBLE * i + j] = elemBytes[j];
                        }
                    }
                }
            } else if (type == QPoint.class) {
                columnType = DataType.COLUMN_POINT;
                size = 0;
                typeSize = SIZE_OF_DOUBLE;
                ArrayList<QPoint> pointList = (ArrayList<QPoint>) dataTable.GetColumnData().get(column);
                QPoint defaultElement = new QPoint(0, 0);
                for (QPoint elem : pointList) {
                    if (elem != null) {
                        size += SIZE_OF_INT + elem.geoPoint.getSerializedSize();
                    } else {
                        size += SIZE_OF_INT + defaultElement.geoPoint.getSerializedSize();
                    }
                }
                dataBuffer = new byte[size];
                i = 0;
                for (int j = 0; j < pointList.size(); j++) {
                    QPoint elem = pointList.get(j);
                    if (elem == null) {
                        elem = defaultElement;
                        if (nullMask == null) {
                            int nullMaskSize = (elementCount + SIZE_OF_BYTE * 8 - 1) / (SIZE_OF_BYTE * 8);
                            nullMask = new byte[nullMaskSize];
                            for (int k = 0; k < nullMaskSize; k++) {
                                nullMask[k] = 0;
                            }
                        }
                        int byteIdx = j / (SIZE_OF_BYTE * 8);
                        int shiftIdx = j % (SIZE_OF_BYTE * 8);
                        nullMask[byteIdx] |= (byte) (1 << shiftIdx);
                    }
                    int len = elem.geoPoint.getSerializedSize();

                    byte[] lenBytes = ByteBuffer.allocate(SIZE_OF_INT).order(ByteOrder.LITTLE_ENDIAN).putInt(len)
                            .array();
                    for (int k = 0; k < SIZE_OF_INT; k++) {
                        dataBuffer[i + k] = lenBytes[k];
                    }

                    i += 4;
                    System.arraycopy(elem.geoPoint.toByteArray(), 0, dataBuffer, i, len);
                    i += len;
                }
            } else if (type == QComplexPolygon.class) {
                columnType = DataType.COLUMN_POLYGON;
                size = 0;
                typeSize = SIZE_OF_DOUBLE;
                ArrayList<QComplexPolygon> polygonList = (ArrayList<QComplexPolygon>) dataTable.GetColumnData()
                        .get(column);
                QComplexPolygon defaultElement = new QComplexPolygon();
                for (QComplexPolygon elem : polygonList) {
                    if (elem != null) {
                        size += SIZE_OF_INT + elem.complexPolygon.getSerializedSize();
                    } else {
                        size += SIZE_OF_INT + defaultElement.complexPolygon.getSerializedSize();
                    }
                }
                dataBuffer = new byte[size];
                i = 0;
                for (int j = 0; j < polygonList.size(); j++) {
                    QComplexPolygon elem = polygonList.get(j);
                    if (elem == null) {
                        elem = defaultElement;
                        if (nullMask == null) {
                            int nullMaskSize = (elementCount + SIZE_OF_BYTE * 8 - 1) / (SIZE_OF_BYTE * 8);
                            nullMask = new byte[nullMaskSize];
                            for (int k = 0; k < nullMaskSize; k++) {
                                nullMask[k] = 0;
                            }
                        }
                        int byteIdx = j / (SIZE_OF_BYTE * 8);
                        int shiftIdx = j % (SIZE_OF_BYTE * 8);
                        nullMask[byteIdx] |= (byte) (1 << shiftIdx);
                    }
                    int len = elem.complexPolygon.getSerializedSize();

                    byte[] lenBytes = ByteBuffer.allocate(SIZE_OF_INT).order(ByteOrder.LITTLE_ENDIAN).putInt(len)
                            .array();
                    for (int k = 0; k < SIZE_OF_INT; k++) {
                        dataBuffer[i + k] = lenBytes[k];
                    }

                    i += 4;
                    System.arraycopy(elem.complexPolygon.toByteArray(), 0, dataBuffer, i, len);
                    i += len;
                }
            } else if (type == String.class) {
                columnType = DataType.COLUMN_STRING;
                size = 0;
                typeSize = SIZE_OF_DOUBLE;
                ArrayList<String> stringList = (ArrayList<String>) dataTable.GetColumnData().get(column);
                String defaultElement = "";
                for (String elem : stringList) {
                    if (elem != null) {
                        size += SIZE_OF_INT + elem.length();
                    } else {
                        size += SIZE_OF_INT + defaultElement.length();
                    }
                }
                dataBuffer = new byte[size];
                i = 0;
                for (int j = 0; j < stringList.size(); j++) {
                    String elem = stringList.get(j);
                    if (elem == null) {
                        elem = defaultElement;
                        if (nullMask == null) {
                            int nullMaskSize = (elementCount + SIZE_OF_BYTE * 8 - 1) / (SIZE_OF_BYTE * 8);
                            nullMask = new byte[nullMaskSize];
                            for (int k = 0; k < nullMaskSize; k++) {
                                nullMask[k] = 0;
                            }
                        }
                        int byteIdx = j / (SIZE_OF_BYTE * 8);
                        int shiftIdx = j % (SIZE_OF_BYTE * 8);
                        nullMask[byteIdx] |= (byte) (1 << shiftIdx);
                    }
                    int len = elem.length();

                    byte[] lenBytes = ByteBuffer.allocate(SIZE_OF_INT).order(ByteOrder.LITTLE_ENDIAN).putInt(len)
                            .array();
                    for (int k = 0; k < SIZE_OF_INT; k++) {
                        dataBuffer[i + k] = lenBytes[k];
                    }

                    i += 4;
                    try {
                        System.arraycopy(elem.getBytes("UTF8"), 0, dataBuffer, i, len);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    i += len;
                }
            }

            int fragmentSize = 0;
            int lastNullBuffOffset = -1;
            for (i = 0; i < size; i += fragmentSize) {
                int elemCount = 0;
                if (columnType == DataType.COLUMN_STRING || columnType == DataType.COLUMN_POINT
                        || columnType == DataType.COLUMN_POLYGON) {
                    fragmentSize = 0;
                    while (fragmentSize < BULK_IMPORT_FRAGMENT_SIZE && i + fragmentSize < size) {
                        fragmentSize += 4;
                        int strSize = 0;

                        ByteBuffer strSizeBuffer = ByteBuffer.allocate(SIZE_OF_INT).order(ByteOrder.LITTLE_ENDIAN);
                        for (int k = 0; k < SIZE_OF_INT; k++) {
                            strSizeBuffer.put(k, dataBuffer[i + fragmentSize - 4 + k]);
                        }
                        if (fragmentSize + strSize > BULK_IMPORT_FRAGMENT_SIZE) {
                            fragmentSize -= 4;
                            break;
                        }
                        strSize = strSizeBuffer.getInt();

                        elemCount++;
                        fragmentSize += strSize;
                    }
                } else {
                    fragmentSize = size - i < BULK_IMPORT_FRAGMENT_SIZE ? size - i : BULK_IMPORT_FRAGMENT_SIZE;
                    fragmentSize = (fragmentSize / typeSize) * typeSize;
                    elemCount = fragmentSize / typeSize;
                }
                byte[] smallBuffer = new byte[fragmentSize];
                System.arraycopy(dataBuffer, i, smallBuffer, 0, fragmentSize);
                int nullBuffSize = ((elemCount) + SIZE_OF_BYTE * 8 - 1) / (SIZE_OF_BYTE * 8);
                BulkImportMessage bulkImportMessage = BulkImportMessage.newBuilder().setTableName(tableName)
                        .setElemCount(elemCount).setColumnName(column).setColumnType(columnType)
                        .setNullMaskLen(nullMask != null ? nullBuffSize : 0).setDataLength(fragmentSize).build();

                try {
                    NetworkMessage.WriteToNetwork(bulkImportMessage, mSocket.getOutputStream());
                    NetworkMessage.WriteRaw(mSocket.getOutputStream(), smallBuffer, fragmentSize);

                    if (bulkImportMessage.getNullMaskLen() != 0) {
                        int startOffset = i / (SIZE_OF_BYTE * 8);
                        if (startOffset == lastNullBuffOffset) {
                            startOffset++;
                            nullBuffSize--;
                        }
                        byte[] smallNullBuffer = new byte[nullBuffSize];
                        System.arraycopy(nullMask, startOffset, smallNullBuffer, 0, nullBuffSize);
                        NetworkMessage.WriteRaw(mSocket.getOutputStream(), smallNullBuffer, nullBuffSize);
                    }

                    Any serverPackedMessage = NetworkMessage.ReadFromNetwork(mSocket.getInputStream());
                    try {
                        InfoMessage serverMessage = serverPackedMessage.unpack(InfoMessage.class);
                        if (serverMessage.getCode() != InfoMessage.StatusCode.OK) {
                            throw new QueryException(serverMessage.getMessage());
                        }
                    } catch (InvalidProtocolBufferException ex) {
                        throw new IOException("Invalid response received from server");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void CloseWithoutNotify() {
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Close() throws IOException {
        InfoMessage msg = InfoMessage.newBuilder().setCode(InfoMessage.StatusCode.CONN_END).setMessage("").build();

        mSocket.setSoTimeout(1000);
        NetworkMessage.WriteToNetwork(msg, mSocket.getOutputStream());                
        
        CloseWithoutNotify();
    }        
}

