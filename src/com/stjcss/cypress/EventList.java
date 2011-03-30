package com.stjcss.cypress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventList extends Activity {
	
	/* Declarations */
	
	Menu mainMenu;
	EventDBHelper eventdb;
	ListView listView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.eventdb = new EventDBHelper(this);
        //this.db = new DataHelper(this);
        setContentView(R.layout.main);
        
      //  final EventDBHelper edbh = new EventDBHelper(this);
        EventListManager elm = new EventListManager(this);
        
        listView = (ListView)findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
                onListItemClick(v,pos,id);
            }
            protected void onListItemClick(View v, int pos, long id) {
                //Log.i(TAG, "onListItemClick id=" + id);
            	TextView tv = (TextView)v;
            	
            	/**Set our INTENT */
            	Intent intent = new Intent(getBaseContext(),Main.class);
            	/*Grab the event ID from the database */
            	Event event = eventdb.selectByName(tv.getText().toString());
            	/*Add a bundle of values to the intent */
            	intent.putExtra("event_id",event.id);
            	//Our intent is to open the tab for details
            	/*
            	 * @Todo: Change 1 to a defined static variable
            	 */
            	intent.putExtra("tab_id", 1);
            	
            	/*
                Toast.makeText(getBaseContext(), 
                        "You have selected item : " + tv.getText().toString() , Toast.LENGTH_SHORT).show();
                */
                /**
                 * This creates a new MainTab Activity
                 * Utilizing the SharedPreferences, we can
                 * now reopen the Main activity which is a Tab Layout
                 * and select the Tab we need, as well as populate it 
                 * with neccessary information
                 * 
                 * Be sure to close this activity with the finish() method.
                 */
                startActivity(intent);
                /** Closing activity with finish() method */
                finish();
 
            }
        });
        listView = elm.getList(listView);
        

        
    }
    
    
    

    
    /** Region: Options Menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	/** Call parent to attach an parent system level menus */
    	super.onCreateOptionsMenu(menu);
    	/** We Populate the menu items */
    	this.mainMenu = menu;
    	/* Place menu items in a group */
    	addMenuItems(menu);
    	
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	
    	if(item.getItemId() == 1){
    		//call show events intent
    	}
    	if(item.getItemId() == 2){
    		//call manage events intent
    	}
    	if(item.getItemId() == 3){
    		//call options intent
    	}
    	return true;
    	
    	//return super.onOptionsItemSelected(item);
    }
    public void addMenuItems(Menu menu){
    	int base = Menu.FIRST;
    	menu.add(base,base+1,base,"Show Events");
    	menu.add(base,base+2,base+1,"Manage Events");
    	menu.add(base,base+3,base+2,"Options");
    }
}
