package me.ghotimayo.eventqueue.script;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
			StoreEvent.eventlock.remove(playername);
			StoreEvent.eventnameallow.remove(playername);
			StoreEvent.chatallow.remove(playername);
		}
		for(Player item : Bukkit.getOnlinePlayers()){
			String itemname = item.getName();
			if(StoreEvent.inevent.containsKey(itemname)){	
				if(StoreEvent.inevent.get(itemname).equals(playername)){
					StoreEvent.inevent.remove(itemname);
					if(itemname != (playername)){
						StoreEvent.eventpname.remove(itemname);
						item.sendMessage(ChatColor.RED + "The event you were in has just been closed.");
					}
				}
			}
		}
	}
}

