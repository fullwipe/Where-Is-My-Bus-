//package fullwipe.ciriitc.whereismybus;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//
//
//public class Db_constructor extends SQLiteOpenHelper {
//
//	public final static String DB_NAME = "linee_sequenze_fermate.db";
//	private final static  int DATABASE_VERSION = 5;
//
//	public Db_constructor(Context context) {
//		super(context, DB_NAME, null, DATABASE_VERSION);
//	}
//
//	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL("ALTER TABLE ADD COLUMN " + Db_PutResult.TABLE_NAME);
//		db.execSQL("CREATE TABLE " + Db_PutResult.TABLE_NAME + " ("
//                + Db_PutResult.ID + " INTEGER PRIMARY KEY,"
//                + Db_PutResult.LINEA + " TEXT,"
//                + Db_PutResult.FERMATA + " TEXT,"
//                + Db_PutResult.DENOMINAZIONE + " TEXT,"
//                + Db_PutResult.UBICAZIONE + " TEXT,"
//                + Db_PutResult.COMUNE + " TEXT,"
//                + Db_PutResult.COORDX + " TEXT,"
//                + Db_PutResult.COORDY + " TEXT,"
//                + Db_PutResult.LAT + " TEXT,"
//                + Db_PutResult.LONG + " TEXT,"
//                + Db_PutResult.ZONA + " TEXT"
//                + ");");
//		load(db, new InputStreamReader(this.getClass().getResourceAsStream("midl_lsf.csv")));
//	}
//
//	@Override
//	public void onCreate(SQLiteDatabase db) {
//		db.execSQL("CREATE TABLE " + Db_PutResult.TABLE_NAME + " ("
//                + Db_PutResult.ID + " INTEGER PRIMARY KEY,"
//                + Db_PutResult.LINEA + " TEXT,"
//                + Db_PutResult.FERMATA + " TEXT,"
//                + Db_PutResult.DENOMINAZIONE + " TEXT,"
//                + Db_PutResult.UBICAZIONE + " TEXT,"
//                + Db_PutResult.COMUNE + " TEXT,"
//                + Db_PutResult.COORDX + " TEXT,"
//                + Db_PutResult.COORDY + " TEXT,"
//                + Db_PutResult.LAT + " TEXT,"
//                + Db_PutResult.LONG + " TEXT,"
//                + Db_PutResult.ZONA + " TEXT"
//                + ");");
//		load(db, new InputStreamReader(this.getClass().getResourceAsStream("midl_lsf.csv")));
//	}
//
//	private void load(SQLiteDatabase db2, InputStreamReader in) {
//		BufferedReader reader = new BufferedReader(in);
//		try {
//			String line = null;
//			while ( (line = reader.readLine()) != null ) {
//				db2.insert(Db_PutResult.TABLE_NAME, null, Db_PutResult.getContentValues(line));
//			}
//			reader.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (in != null) {
//				try {
//					in.close();
//				} catch (IOException e) {
//				}
//			}
//		}
//	}
//
//}
