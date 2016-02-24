//package fullwipe.ciriitc.whereismybus;
//
//import java.util.StringTokenizer;
//
//import android.content.ContentValues;
//
//public class Db_PutResult {
//	public final static String TABLE_NAME 	= "CONTATTI";
//	public final static String ID			= "_id";
//	public final static String LINEA 		= "LINEA";
//	public final static String FERMATA 		= "FERMATA";
//	public final static String DENOMINAZIONE= "DENOMINAZIONE";
//	public final static String UBICAZIONE	= "UBICAZIONE";
//	public final static String COMUNE	= "COMUNE";
//	public final static String COORDX	= "COORDX";
//	public final static String COORDY	= "COORDY";
//	public final static String LAT	= "LAT";
//	public final static String LONG	= "LONG";
//	public final static String ZONA	= "ZONA";
//
//	public static ContentValues getContentValues(String value) {
//		ContentValues result = new ContentValues();
//		StringTokenizer stringTokenizer = new StringTokenizer(value, ";");
//		result.put(Db_PutResult.ID, stringTokenizer.nextToken());
//		result.put(Db_PutResult.LINEA, stringTokenizer.nextToken());
//		result.put(Db_PutResult.FERMATA, stringTokenizer.nextToken());
//		result.put(Db_PutResult.DENOMINAZIONE, stringTokenizer.nextToken());
//		result.put(Db_PutResult.UBICAZIONE, stringTokenizer.nextToken());
//		result.put(Db_PutResult.COMUNE, stringTokenizer.nextToken());
//		result.put(Db_PutResult.COORDX, stringTokenizer.nextToken());
//		result.put(Db_PutResult.COORDY, stringTokenizer.nextToken());
//		result.put(Db_PutResult.LAT, stringTokenizer.nextToken());
//		result.put(Db_PutResult.LONG, stringTokenizer.nextToken());
//		result.put(Db_PutResult.ZONA, stringTokenizer.nextToken());
//		return result;
//	}
//}
