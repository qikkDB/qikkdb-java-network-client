package QikkDB.NetworkClient;

import java.io.IOException;
import java.util.Dictionary;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import QikkDB.Types.QComplexPolygon;
import QikkDB.Types.QPoint;

/**
 * Example java application demonstrating usage of QikkDB 
 *
 */
public class ExampleApp {
    public static void main(String[] args) {
        
        // Connecting to QikkDB
        QikkDBClient client = new QikkDBClient("127.0.0.1", 12345);
        try {
            client.Connect();
        } catch (IOException e) {
            System.out.println("Error connecting to the server " + e.toString());            
        }

        // Using database: test
        try {
            client.UseDatabase("default_db");            
        } catch (IOException | QueryException e) {
            System.out.println("Error changing database " + e.toString());
        }

        // Executing query and priting the result
        try {
            client.Query("SELECT * FROM T_default1 LIMIT 10;");
            Pair<ColumnarDataTable, Dictionary<String, Float>> result = client.GetNextQueryResult();
            ColumnarDataTable resultData = result.getFirst();
            Gson gson = new GsonBuilder().create();
            String resultDataJson = gson.toJson(resultData.GetColumnData());
            System.out.println("Data: " + resultDataJson);
        } catch (IOException | QueryException e) {
            System.out.println("Error while execting query " + e.toString());
        }

        // Importing data of all supported types
        ColumnarDataTable dataTable = new ColumnarDataTable("jimport");        
        dataTable.AddColumn("col_bool", Boolean.class);
        dataTable.AddColumn("col_int", Integer.class);
        dataTable.AddColumn("col_long", Long.class);
        dataTable.AddColumn("col_float", Float.class);
        dataTable.AddColumn("col_double", Double.class);
        dataTable.AddColumn("col_string", String.class);
        dataTable.AddColumn("col_point", QPoint.class);
        dataTable.AddColumn("col_polygon", QComplexPolygon.class);
        dataTable.AddRow(new Object[] { true, 1, 11l, 1.1f, 11.1, "hello1", new QPoint(10.4f, 11.5f), new QComplexPolygon("POLYGON((2 2, 4 2, 4 4, 2 2),(5 15.5, 10.5 10.5, 20.5 10.5, 25 15.5, 20.5 20, 10.5 20, 5 15.5))")  });
        dataTable.AddRow(new Object[] { false, 2, 12l, 2.1f, 12.1, "hello2", new QPoint(10.4f, 12.5f), new QComplexPolygon("POLYGON((2 2, 4 2, 4 4, 2 2),(5 15.5, 10.5 10.5, 20.5 10.5, 25 15.5, 20.5 20, 10.5 20, 5 15.5))")  });
        dataTable.AddRow(new Object[] { true, 3, 13l, 3.1f, 13.1, "hello3", new QPoint(10.4f, 13.5f), new QComplexPolygon("POLYGON((2 2, 4 2, 4 4, 2 2),(5 15.5, 10.5 10.5, 20.5 10.5, 25 15.5, 20.5 20, 10.5 20, 5 15.5))")  });
        dataTable.AddRow(new Object[] { false, 4, 14l, 4.1f, 14.1, "hello4", new QPoint(10.4f, 14.5f), new QComplexPolygon("POLYGON((2 2, 4 2, 4 4, 2 2),(5 15.5, 10.5 10.5, 20.5 10.5, 25 15.5, 20.5 20, 10.5 20, 5 15.5))")  });
        dataTable.AddRow(new Object[] { null, null, null, null, null, null, new QPoint(10.4f, 15.5f), new QComplexPolygon("POLYGON((2 2, 4 2, 4 4, 2 2),(5 15.5, 10.5 10.5, 20.5 10.5, 25 15.5, 20.5 20, 10.5 20, 5 15.5))")  });
        dataTable.AddRow(new Object[] { true, 5, 15l, 5.1f, 15.1, "hello6", new QPoint(10.4f, 16.5f), null });        
        
        try {
            client.BulkImport(dataTable);
        } catch (QueryException e) {            
            System.out.println("Error while executing bulk import " + e.toString());
        }

        // Disconnecting from QikkDB
        try {
            client.Close();
        } catch (IOException e) {
            System.out.println("Error disconnecting from the server " + e.toString());            
        }
    }
}
