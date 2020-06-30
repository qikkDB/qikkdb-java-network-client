package QikkDB.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import QikkDB.Types.ComplexPolygonOuterClass.ComplexPolygon;
import QikkDB.Types.ComplexPolygonOuterClass.GeoPoint;
import QikkDB.Types.ComplexPolygonOuterClass.Polygon;

public class QComplexPolygon {
    
    public final int MAX_POLYGONS_NUMBER = 8;

    public ComplexPolygon complexPolygon;

    public QComplexPolygon()
    {
        ComplexPolygon.Builder complexPolygonBuilder = ComplexPolygon.newBuilder();        
        complexPolygon = complexPolygonBuilder.build();
    }

    public QComplexPolygon(String wktPolygon)
    {
        List<Polygon> polygons = new ArrayList<Polygon>();
        wktPolygon = wktPolygon.replace(", ", ",");
        String[] strPolygons = wktPolygon.split(Pattern.quote("),("));

        String regex = "((-?[0-9]+(\\.[0-9]+)?) (-?[0-9]+(\\.[0-9]+)?)(, ?)?)+";

        strPolygons[0] = strPolygons[0].replace("POLYGON((", "").replace("POLYGON ((", ""); //remove prefix
        strPolygons[strPolygons.length - 1] = strPolygons[strPolygons.length - 1].replace("))", ""); //remove suffix

        if (strPolygons.length > MAX_POLYGONS_NUMBER)
        {
            throw new Error(
                "Well known text polygon is in wrong format - encountered more polygons than " +
                "is max number of polygons in complex polygon. [" + wktPolygon + "]");
        }

        for (int i = 0; i < strPolygons.length; i++)
        {
            if (strPolygons[i].contains(")") || strPolygons[i].contains("("))
            {
                throw new Error(
                    "Well known text polygon is in wrong format - wrong number of '(' or ')'.");
            }

            if (!strPolygons[i].matches(regex))
            {
                throw new Error("Well known text polygon is in wrong format - there is a wrong " +
                    "character that is not a number, comma nor space. [" + wktPolygon + "]");
            }

            String[] points;

            points = strPolygons[i].split(",");

            List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();

            for (int j = 0; j < points.length; j++)
            {
                String[] coordinates = points[j].split(" ");

                if (coordinates.length > 2)
                {
                    throw new Error(
                        "Well known text polygon is in wrong format - wrong number of coordinates, there is more of them per geo point. [" +
                        wktPolygon + "]");
                }

                try 
                {
                    float latitude = Float.parseFloat(coordinates[0]);
                    float longitude = Float.parseFloat(coordinates[1]);    

                    GeoPoint.Builder geoPointBuilder = GeoPoint.newBuilder();
                    geoPointBuilder.setLatitude(latitude);
                    geoPointBuilder.setLongitude(longitude);
                    geoPoints.add(geoPointBuilder.build());
                }
                catch (Exception ex)
                {
                    throw new Error("Well known text polygon is in wrong format - encountered problem " +
                        "with geo point coordinates. [" + wktPolygon + "]");
                }
            }

            Polygon.Builder polygonBuilder = Polygon.newBuilder();
            polygonBuilder.addAllGeoPoints(geoPoints);
            polygons.add(polygonBuilder.build());
        }
        
        ComplexPolygon.Builder complexPolygonBuilder = ComplexPolygon.newBuilder();
        complexPolygonBuilder.addAllPolygons(polygons);
        complexPolygon = complexPolygonBuilder.build();
    }
}