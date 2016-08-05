package me.ghotimayo.eventqueue.script;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.ghotimayo.eventqueue.storage.StoreEvent;

public class Help {

	public static HashMap<String, String> cmdlist = new LinkedHashMap<>();
	public static HashMap<String, String> cmddesc = new LinkedHashMap<>();
	public static HashMap<String, String> cmdarg = new LinkedHashMap<>();

	public static void help(Player p , int i){
		p.sendMessage("");
		p.sendMessage(ChatColor.AQUA+"EventQueue "+ChatColor.BLUE+"v1.3 "+ChatColor.AQUA + "by "+ChatColor.RED+"Ghoti_Mayo");
		p.sendMessage(ChatColor.GREEN+"---------------- EventQueue Commands ----------------");

		cmdlist.put("","evq.use");
		cmdlist.put("leave","evq.use");
		cmdlist.put("start","evq.start");
		cmdlist.put("kick","evq.start");
		cmdlist.put("join","evq.join");
		cmdlist.put("close","evq.start");
		cmdlist.put("nick","evq.nick");
		cmdlist.put("msg","evq.msg");
		cmdlist.put("lock","evq.enablelock");
		cmdlist.put("unlock","evq.enablelock");
		cmdlist.put("enablenick","evq.enablenick");
		cmdlist.put("disablenick","evq.enablenick");
		cmdlist.put("enablewarp","evq.enablewarp");
		cmdlist.put("disablewarp","evq.enablewarp");
		cmdlist.put("enablechat","evq.enablechat");
		cmdlist.put("disablechat","evq.enablechat");
		cmdlist.put("tp","evq.tp");
		cmdlist.put("list","evq.list");
		cmdlist.put("check","evq.list");
		cmdlist.put("find","evq.list");
		cmdlist.put("remove","evq.mod");
		cmdlist.put("hush","evq.mod");
		cmdlist.put("unhush","evq.mod");

		cmddesc.put("","EventQueue base command.");
		cmddesc.put("leave","Leave another person's event.");
		cmddesc.put("start","Start a new event.");
		cmddesc.put("kick","Kick a player from your event.");
		cmddesc.put("join","Joins a player's active event.");
		cmddesc.put("close","Closes your current event.");
		cmddesc.put("nick","Sets a nickname for event chat.");
		cmddesc.put("msg","Sends a message to everyone in the event.");
		cmddesc.put("lock","Prevents other players from joining your event.");
		cmddesc.put("unlock","Allows other players to join your event.");
		cmddesc.put("enablenick","Allows people to create nicknames in event chat.");
		cmddesc.put("disablenick","Prevents people from creating nicknames in event chat.");
		cmddesc.put("enablewarp","Allows people to tp to the event.");
		cmddesc.put("disablewarp","Prevents people from tping to the event.");
		cmddesc.put("enablechat","Enables event chat.");
		cmddesc.put("disablechat","Disables event chat.");
		cmddesc.put("tp","Teleport to a player's event.");
		cmddesc.put("list","Lists active events.");
		cmddesc.put("check","Retrieves information on a specific player's event.");
		cmddesc.put("find","Retrieves events that contain a specific keyword in their description.");
		cmddesc.put("remove","Closes a player's active event.");
		cmddesc.put("hush","Prevents a player from starting an event until unhushed or plugin is reloaded.");
		cmddesc.put("unhush","Reallows player to start events.");

		cmdarg.put("start", "(description)");
		cmdarg.put("kick", "(player name)");
		cmdarg.put("join", "(player name)");
		cmdarg.put("nick", "(nickname)");
		cmdarg.put("msg", "(msg)");
		cmdarg.put("tp", "(player name)");
		cmdarg.put("list", "(page number)");
		cmdarg.put("check", "(player name)");
		cmdarg.put("find", "(keyword) (page number)");
		cmdarg.put("remove", "(player name)");
		cmdarg.put("hush", "(player name)");
		cmdarg.put("unhush", "(player name)");

		sendHelpList(p,i);
	}

	public static void sendHelpList(Player p, int page){
		int eventnum = 0;
		for(String item : cmdlist.keySet()){
			if(p.hasPermission(cmdlist.get(item))){
				eventnum = eventnum + 1;
			}
		}
		double dp = (float)eventnum / 10.0;
		int pagenum = (int)Math.ceil(dp);
		if(page <= pagenum && page > 0){
			StoreEvent.pageInstance.put(p,0);
			int pageten = (page * 10)- 10;
			for(String entry : cmdlist.keySet()){
				if(StoreEvent.pageInstance.get(p) == pageten || StoreEvent.pageInstance.get(p) == pageten + 1 || StoreEvent.pageInstance.get(p) == pageten + 2 || StoreEvent.pageInstance.get(p) == pageten + 3 || StoreEvent.pageInstance.get(p) == pageten + 4 || StoreEvent.pageInstance.get(p) == pageten + 5 || StoreEvent.pageInstance.get(p) == pageten + 6 || StoreEvent.pageInstance.get(p) == pageten + 7 || StoreEvent.pageInstance.get(p) == pageten + 8 || StoreEvent.pageInstance.get(p) == pageten + 9){
					if(p.hasPermission(cmdlist.get(entry))){
						if(entry.equals("")){
							p.sendMessage(ChatColor.GOLD + "/event" + ChatColor.GRAY + " - " + cmddesc.get(entry));
						}else{
							if(cmdarg.containsKey(entry)){
								p.sendMessage(ChatColor.GOLD + "/event " + entry + " " + cmdarg.get(entry) + ChatColor.GRAY + " - " + cmddesc.get(entry));
							}else{
								p.sendMessage(ChatColor.GOLD + "/event " + entry + ChatColor.GRAY + " - " + cmddesc.get(entry));
							}
						}
					}
				}
				StoreEvent.pageInstance.put(p,(StoreEvent.pageInstance.get(p)+1));
			}
			p.sendMessage(ChatColor.GREEN + "Page " + Integer.toString(page) + " of " + Integer.toString(pagenum));
			StoreEvent.pageInstance.remove(p);
		}
	}
}
