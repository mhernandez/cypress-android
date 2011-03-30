package com.stjcss.cypress;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class EventDBHelper {

   private static final String DATABASE_NAME = "CypressProjectDevelopment.db";
   private static final int DATABASE_VERSION = 1;
   private static final String TABLE_NAME = "Events";

   private Context context;
   private SQLiteDatabase db;

   private SQLiteStatement insertStmt;
   private static final String INSERT = "insert into " 
      + TABLE_NAME + "(name, location, description, time, eventID) values (?,?,?,?,?)";

   public EventDBHelper(Context context) {
      this.context = context;
      OpenHelper openHelper = new OpenHelper(this.context);
      this.db = openHelper.getWritableDatabase();
      this.insertStmt = this.db.compileStatement(INSERT);
   }

   public long insert(String name, String location, String description,
		   String time, Integer eventID){
      this.insertStmt.bindString(1, name);
      this.insertStmt.bindString(2, location);
      this.insertStmt.bindString(3, description);
      this.insertStmt.bindString(4, time);
      this.insertStmt.bindString(5, eventID.toString());
      
      return this.insertStmt.executeInsert();
   }

   public void deleteAll() {
      this.db.delete(TABLE_NAME, null, null);
   }

   public List<Event> selectAll() {
	      List<Event> eventlist = new ArrayList<Event>();
	      Cursor cursor = this.db.query(TABLE_NAME, new String[] { "id, " + 
	    		"name, " +
	      		"location,"+
	      		"description," +
	      		"time," +
	      		"eventID"}, 
	        null, null, null, null, null);
	      if (cursor.moveToFirst()) {
	         do {
	        	 Event event = new Event();
	        	 
	        	 event.id = cursor.getInt(0);
	        	 int column = 1;
	        	 event.name = cursor.getString(column);
	        	 event.location = cursor.getString(column+1);
	        	 event.description = cursor.getString(column+2);
	        	 event.time = cursor.getString(column+3);
	        	 event.eventID = cursor.getString(column+4);
	            eventlist.add(event); 
	         } while (cursor.moveToNext());
	      }
	      if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
	      }
	      return eventlist;
	   }
   public Event selectByID(Integer id){
	      Cursor cursor = this.db.query(TABLE_NAME, new String[] { "id, " + 
		    		"name, " +
		      		"location,"+
		      		"description," +
		      		"time," +
		      		"eventID"}, 
		        "id =" + id, null, null, null, null);
	      if (cursor.moveToFirst()) {
	        	 Event event = new Event();
	        	 int column = 0;
	        		 
	        
	        	 event.id = cursor.getInt(column);
	        	
	        	 event.name = cursor.getString(column+1);
	        	 event.location = cursor.getString(column+2);
	        	 event.description = cursor.getString(column+3);
	        	 event.time = cursor.getString(column+4);
	        	 event.eventID = cursor.getString(column+5);
	        	 
	        	 cursor.close();
	        	 return event;
		      }
	      return new Event();
   }
   public Event selectByName(String name ){
	      Cursor cursor = this.db.query(TABLE_NAME, new String[] { "id, " + 
		    		"name, " +
		      		"location,"+
		      		"description," +
		      		"time," +
		      		"eventID"}, 
		        null, null, null, null, null);
		      if (cursor.moveToFirst()) {
		         do {
		        	 Event event = new Event();
		        	 int column = 0;
		        		 
		        
		        	 event.id = cursor.getInt(column);
		        	 event.name = cursor.getString(column+1);
		        	 event.location = cursor.getString(column+2);
		        	 event.description = cursor.getString(column+3);
		        	 event.time = cursor.getString(column+4);
		        	 event.eventID = cursor.getString(column+5);
		        	 
		        	 if(event.name.equals(name))
		        	 {
		        		 cursor.close();
		        		 return event;
		        	 }
		         } while (cursor.moveToNext());
		      }
		      return new Event();
   }
   private static class OpenHelper extends SQLiteOpenHelper {

      OpenHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
      }

      @Override
      public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE " + TABLE_NAME + 
          "(id INTEGER PRIMARY KEY," +
          " name TEXT," +
          " location TEXT," +
          " description TEXT," +
          " time TEXT," +
          "	eventID TEXT NOT NULL )");
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         Log.w("Example", "Upgrading database, this will drop tables and recreate.");
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
         onCreate(db);
      }
   }
}