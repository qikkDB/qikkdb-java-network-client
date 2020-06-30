package QikkDB.Types;
import QikkDB.Types.ComplexPolygonOuterClass.GeoPoint;
import QikkDB.Types.PointOuterClass.Point;

public class QPoint {

    public Point geoPoint;

    public QPoint(float latitude, float longitude)
    {
        GeoPoint.Builder geoPointBuilder = GeoPoint.newBuilder();
        geoPointBuilder.setLatitude(latitude);
        geoPointBuilder.setLongitude(longitude);

        Point.Builder pointBuilder = Point.newBuilder();
        pointBuilder.setGeoPoint(geoPointBuilder.build());

        geoPoint = pointBuilder.build();
    }

    public QPoint(String wktPoint)
    {
        String strPoint = wktPoint;
        strPoint = strPoint.replace("POINT(", "").replace("POINT (", ""); // remove prefix
        strPoint = strPoint.replace(")", ""); //remove suffix

        String[] pointCoordinates = strPoint.split(" ");

        if (pointCoordinates.length > 2)
        {
            throw new Error("Well known text polygon is in wrong format - wrong number of coordinates. [" + wktPoint + "]");
        }

        float latitude = Float.parseFloat(pointCoordinates[0]);
        float longitude = Float.parseFloat(pointCoordinates[1]);

        GeoPoint.Builder geoPointBuilder = GeoPoint.newBuilder();
        geoPointBuilder.setLatitude(latitude);
        geoPointBuilder.setLongitude(longitude);

        Point.Builder pointBuilder = Point.newBuilder();
        pointBuilder.setGeoPoint(geoPointBuilder.build());

        geoPoint = pointBuilder.build();
    }
}