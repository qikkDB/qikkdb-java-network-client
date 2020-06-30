package QikkDB.NetworkClient;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import com.google.api.client.util.DateTime;

import QikkDB.Types.QComplexPolygon;
import QikkDB.Types.QPoint;

public class ColumnarDataTable
{
    private ArrayList<String> columnNames;
    private Dictionary<String, ArrayList> columnData;
    private Dictionary<String, Class> columnTypes;
    private int size;
    private String tableName;
    private ArrayList<String> orderedColumnNames;    //order of columns defined by SELECT

    public ColumnarDataTable(String name)
    {
        this.columnNames = new ArrayList<String>();
        this.columnData = new Hashtable<String, ArrayList>();
        this.columnTypes = new Hashtable<String, Class>();
        this.orderedColumnNames = new ArrayList<String>();
        this.size = 0;
        this.tableName = name;
    }

    public ColumnarDataTable()
    {
        this.columnNames = new ArrayList<String>();
        this.columnData = new Hashtable<String, ArrayList>();
        this.columnTypes = new Hashtable<String, Class>();
        this.orderedColumnNames = new ArrayList<String>();
        this.size = 0;
    }

    public ColumnarDataTable(final ArrayList<String> columnNames, final Hashtable<String, ArrayList> columnData, final Hashtable<String, Class> columnTypes, final ArrayList<String> orderedColumnNames)
    {
        this.columnNames = columnNames;
        this.columnData = columnData;
        this.columnTypes = columnTypes;
        this.orderedColumnNames = orderedColumnNames;
        this.size = columnData.size() > 0 ? columnData.get(columnNames.get(0)).size() : 0;
    }


    public void AddColumn(String columnName, Class clazz)
    {
            columnNames.add(columnName);

            if (clazz == Integer.class)
                columnData.put(columnName, new ArrayList<Integer>());
            else if (clazz == Long.class)
                columnData.put(columnName, new ArrayList<Long>());
            else if (clazz == Float.class)
                columnData.put(columnName, new ArrayList<Float>());
            else if (clazz == Double.class)
                columnData.put(columnName, new ArrayList<Double>());
            else if (clazz == DateTime.class)
                columnData.put(columnName, new ArrayList<Long>());
            else if (clazz == Boolean.class)
                columnData.put(columnName, new ArrayList<Boolean>());
            else if (clazz == QPoint.class)
                columnData.put(columnName, new ArrayList<QPoint>());
            else if (clazz == QComplexPolygon.class)
                columnData.put(columnName, new ArrayList<QComplexPolygon>());
            else if (clazz == String.class)
                columnData.put(columnName, new ArrayList<String>());

            if (clazz == Integer.class)
                columnTypes.put(columnName, Integer.class);
            else if (clazz == Long.class)
                columnTypes.put(columnName, Long.class);
            else if (clazz == Float.class)
                columnTypes.put(columnName, Float.class);
            else if (clazz == Double.class)
                columnTypes.put(columnName, Double.class);
            else if (clazz == DateTime.class)
                columnTypes.put(columnName, Long.class);
            else if (clazz == Boolean.class)
                columnTypes.put(columnName, Boolean.class);
            else if (clazz == QPoint.class)
                columnTypes.put(columnName, QPoint.class);
            else if (clazz == QComplexPolygon.class)
                columnTypes.put(columnName, QComplexPolygon.class);
            else if (clazz == String.class)
                columnTypes.put(columnName, String.class);
    }

    public void AddRow(Object[] values)
    {
        for (int i = 0; i < values.length; i++)
        {
            columnData.get(columnNames.get(i)).add(values[i]);
        }
        this.size = this.size + 1;
    }

    public ArrayList<String> GetColumnNames()
    {
        return this.columnNames;
    }

    public Dictionary<String, Class> GetColumnTypes()
    {
        return this.columnTypes;
    }

    public Dictionary<String, ArrayList> GetColumnData()
    {
        return this.columnData;
    }

    public ArrayList<String> GetOrderedColumnNames()
    {
        return this.orderedColumnNames;
    }

    public int GetSize()
    {
        return this.size;
    }

    public String GetTableName()
    {
        return this.tableName;
    }

    public void SetTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public void Clear()
    {
        for (String columnName : this.columnNames) {
            this.columnData.get(columnName).clear();
            this.size = 0;
        }
    }
}