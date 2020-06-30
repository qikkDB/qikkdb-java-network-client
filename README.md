# Java connector to QikkDB database

**QikkDBClient.java:** class containing functions necessary to establish connection and get the data

**ExampleApp.java:** sample usage of QikkDBClient

## Usage

### Creating connection
```
QikkDBClient client = new QikkDBClient("192.168.1.230", 12345);
try {
	client.Connect();
} catch (IOException e) {
	System.out.println("Error connecting to server " + e.toString());            
}
```

### Switching database
```
try {
	client.UseDatabase("default_db");            
} catch (IOException | QueryException e) {
	System.out.println("Error changing database " + e.toString());
}
```

### Querying
Query is executed using `Query()` method. Getting result is done by calling `GetNextQueryResult()` method which returns bulk of rows (1000 rows). 
All rows could be retrieved by iterating until `(null, null)` pair is returned by `GetNextQueryResult()`.
```
Executing query and priting the result
try {
	client.Query("SELECT * FROM T_default1 LIMIT 10;");
    Pair<ColumnarDataTable, Dictionary<String, Float>> result = client.GetNextQueryResult();
    ColumnarDataTable resultData = result.getFirst();
} catch (IOException | QueryException e) {
	System.out.println("Error while execting query " + e.toString());
}
```

### Bulk import
```
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

try {
    client.BulkImport(dataTable);
} catch (QueryException e) {            
    System.out.println("Error while executing bulk import " + e.toString());
}        
```

### Disconnecting
```
try {
    client.Close();
} catch (IOException e) {
    System.out.println("Error disconnecting from the server " + e.toString());            
}
```

## Generating Protobuf classes
Generating classes are from `proto/` folder with command
```
protoc --proto_path=. --java_out=../src/main/java/ Message/InfoMessage.proto Message/QueryMessage.proto Message/QueryResponseMessage.proto Message/SetDatabaseMessage.proto Message/BulkImportMessage.proto Message/CSVImportMessage.proto Types/ComplexPolygon.proto Types/Point.proto
```
