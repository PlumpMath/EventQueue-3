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
			p.sendMessage("");
			p.sendMessage(ChatColor.GREEN + "Vieving page " + Integer.toString(page) + " of " + Integer.toString(pagenum));
			p.sendMessage(ChatColor.DARK_AQUA + "Type /event tp (player) to visit their event!");
			p.sendMessage(ChatColor.GRAY + "-------------------------------------");
			for(String entry : StoreEvent.eventlist.keySet()){
				int count = 0;
				for(String item : StoreEvent.inevent.keySet()){
					if(StoreEvent.inevent.get(item).equals(entry)){
						count = count + 1;
					}

				}
				if(StoreEvent.pageInstance.get(p) == pageten || StoreEvent.pageInstance.get(p) == pageten + 1 || StoreEvent.pageInstance.get(p) == pageten + 2 || StoreEvent.pageInstance.get(p) == pageten + 3 || StoreEvent.pageInstance.get(p) == pageten + 4 || StoreEvent.pageInstance.get(p) == pageten + 5 || StoreEvent.pageInstance.get(p) == pageten + 6 || StoreEvent.pageInstance.get(p) == pageten + 7 || StoreEvent.pageInstance.get(p) == pageten + 8 || StoreEvent.pageInstance.get(p) == pageten + 9){
					if(count == 1){
						p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Event Leader" + ChatColor.GRAY + ": " + ChatColor.RESET + ChatColor.AQUA + entry + ChatColor.GRAY + " -" + ChatColor.GREEN + " 1 Member" );
						p.sendMessage(ChatColor.RED + " Description" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "" + ChatColor.ITALIC + StoreEvent.eventlist.get(entry));
					}else{
						p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Event Leader" + ChatColor.GRAY + ": " + ChatColor.RESET + ChatColor.AQUA + entry + ChatColor.GRAY + " -" + ChatColor.GREEN + " " + count + " Members" );
						p.sendMessage(ChatColor.RED + " Description" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "" + ChatColor.ITALIC + "" + StoreEvent.eventlist.get(entry));
					}
				}
				StoreEvent.pageInstance.put(p,(StoreEvent.pageInstance.get(p)+1));
			}
			p.sendMessage(ChatColor.GRAY + "-------------------------------------");
			p.sendMessage(ChatColor.GREEN + "Page " + Integer.toString(page) + " of " + Integer.toString(pagenum));
			StoreEvent.pageInstance.remove(p);
		}
	}
}
