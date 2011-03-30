package com.stjcss.cypress;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;


public class Main extends TabActivity {
	
	public TabHost tabHost;
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.tablayout);
        Bundle extras = getIntent().getExtras(); 
        int tab_select_id = 10;
        int event_id = 1;
        if(extras !=null)
        {

        	tab_select_id = extras.getInt("tab_id");
        	event_id = extras.getInt("event_id");
        }
	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, EventList.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    	    spec = tabHost.newTabSpec("Events").setIndicator("Events").setContent(intent);
	    tabHost.addTab(spec);
	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, DetailedEvents.class);
	    spec = tabHost.newTabSpec("detailed").setIndicator("Detailed Event Info")
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, GPSData.class);
	    spec = tabHost.newTabSpec("GPSData").setIndicator("GPS Data").setContent(intent);
	    tabHost.addTab(spec);
	      // We need an Editor object to make preference changes.
	      // All objects are from android.context.Context
        // Restore preferences
	      SharedPreferences settings = getSharedPreferences("cypres_states",MODE_WORLD_WRITEABLE);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putInt("SelectedEvent", event_id);
	      editor.commit();
	    //spec = tabHost.newTabSpec();
	    
	    if(!(tab_select_id == 10))
	    	tabHost.setCurrentTab(tab_select_id);
	    else
	    	tabHost.setCurrentTab(0);
	}
	
	
	
}
