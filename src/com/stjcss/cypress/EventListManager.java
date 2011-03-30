package com.stjcss.cypress;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventListManager {

	Context caller;
	public EventListManager(Context caller){
		this.caller = caller;
	}
	public ListView getList(ListView lv){
        List<Event> eventList = this.getInstanceOfEventDBHelper().selectAll();
        List<String> lines = new ArrayList<String>();

        for(Event event : eventList){
        	String eventstring;
        	eventstring = event.name;
        	lines.add(eventstring);
        }
        String lv_args[] = (String[]) lines.toArray(new String[0]);
        lv.setAdapter(new ArrayAdapter<String>(caller,android.R.layout.simple_list_item_1 ,lv_args ));
        
        return lv;
	}
	private EventDBHelper getInstanceOfEventDBHelper(){
		return new EventDBHelper(this.caller);
	}
}
