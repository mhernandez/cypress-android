package com.stjcss.cypress;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DetailedEvents extends Activity {
	
	/* Declarations */
	
	Menu mainMenu;
	EventDBHelper eventdb;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Bundled
        int id = 1;
        this.eventdb = new EventDBHelper(this);
        //this.db = new DataHelper(this);
        setContentView(R.layout.detailed);
        //String s_id = getResources().getString(R.string.SelectedEvent);
        //id = Integer.parseInt(s_id);
        
        // Restore preferences
        
	    SharedPreferences settings = getSharedPreferences("cypres_states",MODE_WORLD_WRITEABLE);
        id = settings.getInt("SelectedEvent", 2);
        Event event = this.eventdb.selectByID(id);
        //get event
        final TextView name = (TextView)findViewById(R.id.deName);
        final TextView location = (TextView)findViewById(R.id.deLocation);
        final TextView description = (TextView)findViewById(R.id.deDescription);
        final TextView time = (TextView)findViewById(R.id.deTime);
        final TextView eventID = (TextView)findViewById(R.id.deEventID);
        Toast.makeText(getBaseContext(), event.name, Toast.LENGTH_LONG).show();

        //CharSequence text = event.name;
        name.append(event.name);
        location.append(event.location);
        description.append(event.description);
        time.append(event.time);
        eventID.append(event.eventID);

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