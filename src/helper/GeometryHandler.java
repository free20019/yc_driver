package helper;
//package helper;
//
//import com.vividsolutions.jts.geom.Coordinate;
//import com.vividsolutions.jts.geom.CoordinateSequence;
//import com.vividsolutions.jts.geom.Geometry;
//import com.vividsolutions.jts.geom.GeometryFactory;
//import com.vividsolutions.jts.geom.LinearRing;
//import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
//
//public class GeometryHandler  {
//
//	public static Geometry getGeometryObject(String xys)
//	{
//	     Geometry geometry=null;
//	     if(xys!=null && !xys.isEmpty())
//	     {
//	         String[] array=xys.split(";");
////	         System.out.println(array.length);
//	         if(array.length==1)
//	         {
//	        	 GeometryFactory gFactory = new GeometryFactory();
//	        	 String[] coordArray=array[0].split(",");
//	        	 geometry=gFactory.createPoint(new Coordinate(Double.parseDouble(coordArray[0]),Double.parseDouble(coordArray[1]),0));
//	         }
//	         else if(array.length>1)
//	         {
//	        	 GeometryFactory gFactory = new GeometryFactory();
//	        	 Coordinate[] coords=new Coordinate[array.length];
//	        	 for(int i=0;i<array.length;i++)
//	        	 {
//	        		 String[] coordArray=array[i].split(",");
//	        		 coords[i]=new Coordinate(Double.parseDouble(coordArray[0]),Double.parseDouble(coordArray[1]),0);
//	        	 }
//	        	 if(coords[0].equals2D(coords[array.length-1]))
//	        	 {
//	        		 CoordinateSequence  cs = new CoordinateArraySequence(coords);
//	        		 LinearRing shell = new LinearRing(cs,gFactory);
//		        	 geometry=gFactory.createPolygon( shell,null);//createPolygon(coords);
//	        	 }
//	        	 else
//	        	 {
//	        		 geometry=gFactory.createLineString(coords);
//	        	 }
//	         }
//	     }
//	     return geometry;
//	}
//
//	public static void main(String[] args)
//	{
//		Geometry geometry=GeometryHandler.getGeometryObject("120.156227,30.27495;120.157048,30.272865;120.154532,30.272647;120.154317,30.274486;120.156227,30.27495");
//		Geometry g2=GeometryHandler.getGeometryObject("120.154427,30.274131");
//		Geometry g3=GeometryHandler.getGeometryObject("120.156713,30.273567");
//		Geometry g4=GeometryHandler.getGeometryObject("120.156513,30.273567");
//		Geometry g5=GeometryHandler.getGeometryObject("120.155513,30.273567");
//		System.out.println(geometry.contains(g2));
//		System.out.println(geometry.contains(g3));
//		System.out.println(geometry.contains(g4));
//		System.out.println(geometry.contains(g5));
//		
//	}
//}
