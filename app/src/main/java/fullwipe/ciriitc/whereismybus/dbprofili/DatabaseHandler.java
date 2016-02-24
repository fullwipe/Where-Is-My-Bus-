package fullwipe.ciriitc.whereismybus.dbprofili;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

 //Database Version
 private static final int DATABASE_VERSION = 1;
 //Database Name
 private static final String DATABASE_NAME = "Profili";
 //Table Name
 private static final String TABLE_PROFILI = "table_profili";
 //Column Name
 private static final String KEY_ID = "id";
 private static final String KEY_NOME = "nome";
 private static final String KEY_INDIRIZZO = "indirizzo";
 private static final String KEY_TEMPOMAX = "tempomax";
 private static final String KEY_FERMATA = "kfermata";
 private static final String KEY_LINEA = "klinea";
 private static final String KEY_X = "kX";
 private static final String KEY_Y = "kY";
 private static final String TERMINAL = "terminal";

 public DatabaseHandler(Context context) {
  super(context, DATABASE_NAME, null, DATABASE_VERSION);
 }

 //Create Table
 @Override
 public void onCreate(SQLiteDatabase db) {
  String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PROFILI + "("
    + KEY_ID + " INTEGER PRIMARY KEY," 
    + KEY_NOME + " TEXT,"
    + KEY_INDIRIZZO + " TEXT,"
    + KEY_TEMPOMAX + " TEXT,"
    + KEY_FERMATA + " TEXT,"
    + KEY_LINEA + " TEXT,"
    + KEY_X + " TEXT,"
    + KEY_Y + " TEXT,"
    + TERMINAL + " TEXT" + ")";
  db.execSQL(CREATE_CONTACTS_TABLE);
 }

 @Override
 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILI);
  onCreate(db);
 }

 //Insert Value
 public void adddata(Context context,String keynome,String keyindirizzo,String keytempomax,String keyfermata,String keylinea,String keyX,String keyY,String terminal) {
  SQLiteDatabase db = this.getWritableDatabase();
  ContentValues values = new ContentValues();
  values.put(KEY_NOME, keynome);
  values.put(KEY_INDIRIZZO, keyindirizzo);
  values.put(KEY_TEMPOMAX, keytempomax);
  values.put(KEY_FERMATA, keyfermata);
  values.put(KEY_LINEA, keylinea);
  values.put(KEY_X, keyX);
  values.put(KEY_Y, keyY);
  values.put(TERMINAL, terminal);
  db.insert(TABLE_PROFILI, null, values);
  db.close();
 }

 //Get Row Count
 public int getCount() {
  String countQuery = "SELECT  * FROM " + TABLE_PROFILI;
  int count = 0;
  SQLiteDatabase db = this.getReadableDatabase();
  Cursor cursor = db.rawQuery(countQuery, null);
  if(cursor != null && !cursor.isClosed()){
   count = cursor.getCount();
   cursor.close();
  } 
  return count;
 }

 //Delete Query
 public void removeProf(int id) {
  String countQuery = "DELETE FROM " + TABLE_PROFILI + " where " + KEY_ID + "= " + id ;
  SQLiteDatabase db = this.getReadableDatabase();
  db.execSQL(countQuery);
 }

 //Get FavList
 public List<ProfiliList> getProfList(){
  String selectQuery = "SELECT  * FROM " + TABLE_PROFILI;
  SQLiteDatabase db = this.getWritableDatabase();
  Cursor cursor = db.rawQuery(selectQuery, null);
  List<ProfiliList> ProfList = new ArrayList<ProfiliList>();
  if (cursor.moveToFirst()) {
   do {
    ProfiliList list = new ProfiliList();
    list.setId(Integer.parseInt(cursor.getString(0)));
    list.setNome(cursor.getString(1));
    list.setIndirizzo(cursor.getString(2));
    list.setTempomax(cursor.getString(3));
    list.setFermatasc(cursor.getString(4));
    list.setLineasc(cursor.getString(5));
    list.setCoorX(cursor.getString(6));
    list.setCoorY(cursor.getString(7));
    list.setTerminal(cursor.getString(8));
    ProfList.add(list);
   } while (cursor.moveToNext());
  }
  return ProfList;
 }

}