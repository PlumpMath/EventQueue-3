package me.ghotimayo.eventqueue.script;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Help {

	public static void help(Player p){
		p.sendMessage("");
		p.sendMessage(ChatColor.AQUA+"EventQueue by "+ChatColor.RED+"Ghoti_Mayo");
		p.sendMessage(ChatColor.GREEN+"---------------- EventQueue Commands ----------------");
		if(p.hasPermission("evq.use")){
			p.sendMessage(ChatColor.GOLD+"/event" + ChatColor.GRAY + " - EventQueue base command.");
		}
		if(p.hasPermission("evq.start")){
			p.sendMessage(ChatColor.GOLD+"/event start (description)" + ChatColor.GRAY + " - Start a new event.");
		}
		if(p.hasPermission("evq.start")){
			p.sendMessage(ChatColor.GOLD+"/event close" + ChatColor.GRAY + " - Closes your current event.");
		}
		if(p.hasPermission("evq.tp")){
			p.sendMessage(ChatColor.GOLD+"/event tp (player name)" + ChatColor.GRAY + " - Teleport to a player's event.");
		}
		if(p.hasPermission("evq.list")){
			p.sendMessage(ChatColor.GOLD+"/event list (page number)" + ChatColor.GRAY + " - Lists active events.");
		}
		if(p.hasPermission("evq.mod")){
			p.sendMessage(ChatColor.GOLD+"/event remove (player name)" + ChatColor.GRAY + " - Closes a player's active event.");
			p.sendMessage(ChatColor.GOLD+"/event hush (player name)" + ChatColor.GRAY + " - Prevents a player from starting an event until unhushed or plugin is reloaded.");
			p.sendMessage(ChatColor.GOLD+"/event unhush (player name)" + ChatColor.GRAY + " - Reallows player to start events.");
		}
	}
}
