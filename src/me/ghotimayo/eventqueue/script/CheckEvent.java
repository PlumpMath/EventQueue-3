package me.ghotimayo.eventqueue.script;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.ghotimayo.eventqueue.storage.StoreEvent;

public class CheckEvent {
	public static void check(String playername, Player p){
		Player p2 = GetPlayer.getEP(playername);
		playername = p2.getName();
		if(StoreEvent.eventlist.containsKey(playername)){
			p.sendMessage(ChatColor.DARK_AQUA + "Type /event tp (player) to visit their event!");
			p.sendMessage(ChatColor.GRAY + "-------------------------------------");
			int count = 0;
			for(String item : StoreEvent.inevent.keySet()){
				if(StoreEvent.inevent.get(item).equalsIgnoreCase(playername)){
					count = count + 1;
				}
			}
			if(count == 1){
				p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Event Leader" + ChatColor.GRAY + ": " + ChatColor.RESET + ChatColor.AQUA + playername + ChatColor.GRAY + " -" + ChatColor.GREEN + " 1 Member" );
				p.sendMessage(ChatColor.RED + " Description" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "" + ChatColor.ITALIC + StoreEvent.eventlist.get(playername));
			}else{
				p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Event Leader" + ChatColor.GRAY + ": " + ChatColor.RESET + ChatColor.AQUA + playername + ChatColor.GRAY + " -" + ChatColor.GREEN + " " + count + " Members" );
				p.sendMessage(ChatColor.RED + " Description" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "" + ChatColor.ITALIC + "" + StoreEvent.eventlist.get(playername));
			}
			p.sendMessage(ChatColor.GRAY + "-------------------------------------");
		}else{
			p.sendMessage(ChatColor.RED + "That player does not currently have an active event.");
		}
	}
}
