package QikkDB.NetworkClient.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.google.protobuf.Any;
import com.google.protobuf.Message;

public class NetworkMessage {
    public static void WriteToNetwork(Message message, OutputStream outputStream) throws IOException
    {
        Any packedMsg = Any.pack(message);
        int size = packedMsg.getSerializedSize();
     
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        byteBuffer.putInt(size);
        byte[] header = byteBuffer.array();
                
        outputStream.write(header);
        packedMsg.writeTo(outputStream);
        outputStream.flush();
    }

    public static Any ReadFromNetwork(InputStream inputStream) throws IOException 
    {
        byte[] buffer = new byte[4];
        int totalRead = 0;
        while (totalRead != 4) 
        {
            int readNow = inputStream.read(buffer, totalRead, 4 - totalRead);
            if (readNow == 0) {
                throw new IOException("Conncection was closed by the other side");
            }
            totalRead += readNow;
        }
        totalRead = 0;

        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        int dataLen = byteBuffer.getInt();
        
        byte[] data = new byte[dataLen];
        while (totalRead != dataLen) {
            int readNow = inputStream.read(data, totalRead, dataLen - totalRead);
            if (readNow == 0) {
                throw new IOException("Conncection was closed by the other side");
            }
            totalRead += readNow;
        }
        
        return Any.parseFrom(data);
    }

    public static void WriteRaw(OutputStream outputStream, byte[] dataBuffer, int elementCount) throws IOException
        {
            outputStream.write(dataBuffer, 0, elementCount);
        }
}