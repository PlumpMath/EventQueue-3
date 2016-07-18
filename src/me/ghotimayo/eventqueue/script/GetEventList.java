package me.ghotimayo.eventqueue.script;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import me.ghotimayo.eventqueue.storage.StoreEvent;

public class GetEventList {

	public static void sendEventList(Player p, int page){
		int eventnum = StoreEvent.eventlist.size();
		double dp = (float)eventnum / 10.0;
		int pagenum = (int)Math.ceil(dp);
		if(page <= pagenum && page > 0){
			StoreEvent.pageInstance.put(p,0);
			int pageten = (page * 10)- 10;
			p.sendMessage(ChatColor.GREEN + "Vieving page " + Integer.toString(page) + " of " + Integer.toString(pagenum));
			p.sendMessage(ChatColor.AQUA + "Type /event tp (player) to visit their event!");
			for(String entry : StoreEvent.eventlist.keySet()){
				if(StoreEvent.pageInstance.get(p) == pageten || StoreEvent.pageInstance.get(p) == pageten + 1 || StoreEvent.pageInstance.get(p) == pageten + 2 || StoreEvent.pageInstance.get(p) == pageten + 3 || StoreEvent.pageInstance.get(p) == pageten + 4 || StoreEvent.pageInstance.get(p) == pageten + 5 || StoreEvent.pageInstance.get(p) == pageten + 6 || StoreEvent.pageInstance.get(p) == pageten + 7 || StoreEvent.pageInstance.get(p) == pageten + 8 || StoreEvent.pageInstance.get(p) == pageten + 9){
					p.sendMessage(ChatColor.RED+"Event Leader: " + ChatColor.GOLD + entry + ChatColor.RED + " Description: " + ChatColor.YELLOW + StoreEvent.eventlist.get(entry));    	
				}
				StoreEvent.pageInstance.put(p,(StoreEvent.pageInstance.get(p)+1));
			}
			p.sendMessage(ChatColor.GREEN + "Page " + Integer.toString(page) + " of " + Integer.toString(pagenum));
			StoreEvent.pageInstance.remove(p);
		}
	}
}
