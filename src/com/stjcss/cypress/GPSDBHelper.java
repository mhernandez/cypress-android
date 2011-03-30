package com.stjcss.cypress;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GPSDBHelper {

   private static final String DATABASE_NAME = "cypressGPS.db";
   private static final int DATABASE_VERSION = 1;
   private static final String TABLE_NAME = "GpsInfo";
   
   // Static Column Names
   private static final String LATITUDE = "Latitude";
   private static final String LONGITUDE = "Longitude";
   private static final String ALTITUDE = "Altitude";
   private static final String ACCURACY = "Accuracy";
   private static final String BEARING = "Bearing";
   private static final String SPEED = "Speed";
   private static final String TIME = "Time";

   private Context context;
   private SQLiteDatabase db;

   private SQLiteStatement insertStmt;
   private static final String gpsInsert = "insert into " 
	      + TABLE_NAME 
	      + "(LATITUDE,LONGITUDE,ALTITUDE,ACCURACY,BEARING,SPEED,TIME) values (?,?,?,?,?,?,?)";
   private static final String INSERT = "insert into " 
      + TABLE_NAME + "() values (?)";

   public GPSDBHelper(Context context) {
      this.context = context;
      OpenHelper openHelper = new OpenHelper(this.context);
      this.db = openHelper.getWritableDatabase();
      this.insertStmt = this.db.compileStatement(gpsInsert);
   }

   public long insertAll(String latitude, String longitude, String altitude,
		   String accuracy, String bearing, String speed, String time ){
      this.insertStmt.bindString(1, latitude);
      this.insertStmt.bindString(2, longitude);
      this.insertStmt.bindString(3, altitude);
      this.insertStmt.bindString(4, accuracy);
      this.insertStmt.bindString(5, bearing);
      this.insertStmt.bindString(6, speed);
      this.insertStmt.bindString(7, time);
      
      return this.insertStmt.executeInsert();
   }

   public void deleteAll() {
      this.db.delete(TABLE_NAME, null, null);
   }

   public List<String> selectAll() {
      List<String> list = new ArrayList<String>();
      Cursor cursor = this.db.query(TABLE_NAME, new String[] { "latitude, " +
      		"longitude,"+
      		"altitude," +
      		"accuracy," +
      		"bearing," +
      		"speed," +
      		"time" }, 
        null, null, null, null, "name desc");
      if (cursor.moveToFirst()) {
         do {
            list.add(cursor.getString(0)); 
         } while (cursor.moveToNext());
      }
      if (cursor != null && !cursor.isClosed()) {
         cursor.close();
      }
      return list;
   }

   private static class OpenHelper extends SQLiteOpenHelper {

      OpenHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
      }

      @Override
      public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY, " +
         		"latitude TEXT, " +
         		"longitude TEXT, "+
         		"altitude TEXT, " +
         		"accuracy TEXT, " +
         		"bearing TEXT, " +
         		"speed TEXT, " +
         		"time TEXT " +
         		")");
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         Log.w("Example", "Upgrading database, this will drop tables and recreate.");
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
         onCreate(db);
      }
   }
}