package me.ghotimayo.eventqueue.script;


import me.ghotimayo.eventqueue.storage.StoreEvent;

public class EventClear {

	public static void removeEvent(String playername){		
		if(StoreEvent.eventlist.containsKey(playername)){			
			StoreEvent.eventlist.remove(playername);			
		}		
		if(StoreEvent.eventlocations.containsKey(playername)){			
			StoreEvent.eventlocations.remove(playername);			
		}		
		if(StoreEvent.active.containsKey(playername)){
			StoreEvent.active.remove(playername);
		}
	}
}
