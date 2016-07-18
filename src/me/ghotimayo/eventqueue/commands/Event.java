package me.ghotimayo.eventqueue.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.ghotimayo.eventqueue.script.GetEventList;
import me.ghotimayo.eventqueue.script.Help;
import me.ghotimayo.eventqueue.script.EventClear;
import me.ghotimayo.eventqueue.storage.StoreEvent;

public class Event implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if (label.equalsIgnoreCase("event")) {
			Player p = (Player) sender;
			String playername = p.getName();
			if(p.hasPermission("evq.use")){
				if (args.length >= 2) {
					if (args[0].equalsIgnoreCase("start") && p.hasPermission("evq.start")) {
						if(!StoreEvent.hushlist.containsKey(playername)){
							String rp = "";
							for(int i = 0; i < args.length; i++){
								if(i != 0){
									String arg = args[i] + " ";
									rp = rp + arg;}
							}
							if (StoreEvent.active.containsKey(playername)){
								if (StoreEvent.active.get(playername)== false){
									StoreEvent.active.put(playername, true);
								}
								else{
									p.sendMessage(ChatColor.RED + "You already have an active event!");
								}
							}
							else{
								StoreEvent.active.put(playername, true);
							}
							if (StoreEvent.active.get(playername) == true && StoreEvent.eventlist.containsKey(playername) == false){

								Location loc = p.getLocation();
								StoreEvent.eventlocations.put(playername, loc);
								StoreEvent.eventlist.put(playername,rp);
								p.sendMessage(ChatColor.AQUA + "You have successfully created a new event!");
							}
						}else{
							p.sendMessage(ChatColor.GRAY+"You have been hushed!");
							p.sendMessage(ChatColor.RED+"You will be unable to create new events until you are");
							p.sendMessage(ChatColor.RED+"unhushed or the plugin is reloaded!");
						}
					}
				}
				if (args.length == 2){
					if(args[0].equalsIgnoreCase("tp") && p.hasPermission("evq.tp")){
						String toplayer = args[1];
						if(StoreEvent.eventlocations.containsKey(toplayer)){
							Location toloc = StoreEvent.eventlocations.get(toplayer);
							p.teleport(toloc);
						}
					}
					if(args[0].equalsIgnoreCase("remove") && p.hasPermission("evq.mod")){
						String toplayer = args[1];
						EventClear.removeEvent(toplayer);
						p.sendMessage(ChatColor.GOLD + "You have successfully deleted "+toplayer+"'s event!");
					}
					if(args[0].equalsIgnoreCase("hush") && p.hasPermission("evq.mod")){
						String toplayer = args[1];
						StoreEvent.hushlist.put(toplayer,toplayer);
						p.sendMessage(ChatColor.GREEN + "You have successfully hushed "+toplayer+"!");
					}
					if(args[0].equalsIgnoreCase("unhush") && p.hasPermission("evq.mod")){
						String toplayer = args[1];
						StoreEvent.hushlist.remove(toplayer);
						p.sendMessage(ChatColor.GREEN + "You have successfully unhushed "+toplayer+"!");
					}
				}
				if (args.length >= 1 && args.length <= 2){	
					if(args[0].equalsIgnoreCase("list") && p.hasPermission("evq.list")){
						if(StoreEvent.eventlist.size() == 0){
							p.sendMessage(ChatColor.RED + "There are currently no open events!");
						}
						if(args.length == 1){
							String pagenum = "1";
							if(pagenum.matches("[0-9]+")){
								int pageint = Integer.parseInt(pagenum);	
								GetEventList.sendEventList(p, pageint);
							}
						}
						if(args.length == 2){
							String pagenum = args[1];
							if(pagenum.matches("[0-9]+")){
								int pageint = Integer.parseInt(pagenum);	
								GetEventList.sendEventList(p, pageint);
							}	
						}
					}
				}
				if (args.length == 1){
					if(args[0].equalsIgnoreCase("hush") && p.hasPermission("evq.mod")){
						p.sendMessage(ChatColor.YELLOW + "/event hush (player name)");
					}
					if(args[0].equalsIgnoreCase("unhush") && p.hasPermission("evq.mod")){
						p.sendMessage(ChatColor.YELLOW + "/event unhush (player name)");
					}
					if(args[0].equalsIgnoreCase("remove") && p.hasPermission("evq.mod")){
						p.sendMessage(ChatColor.YELLOW + "/event remove (player name)");
					}
					if (args[0].equalsIgnoreCase("start") && p.hasPermission("evq.start")) {
						if(!StoreEvent.hushlist.containsKey(playername)){
							String rp = ChatColor.GRAY+"No Description!";
							if (StoreEvent.active.containsKey(playername)){
								if (StoreEvent.active.get(playername)== false){
									StoreEvent.active.put(playername, true);
								}
								else{
									p.sendMessage(ChatColor.RED + "You already have an active event!");
								}
							}
							else{
								StoreEvent.active.put(playername, true);
							}
							if (StoreEvent.active.get(playername) == true && StoreEvent.eventlist.containsKey(playername) == false){
								Location loc = p.getLocation();
								StoreEvent.eventlocations.put(playername, loc);
								StoreEvent.eventlist.put(playername,rp);
								p.sendMessage(ChatColor.AQUA + "You have successfully created a new event!");
							}
						}else{
							p.sendMessage(ChatColor.GRAY+"You have been hushed!");
							p.sendMessage(ChatColor.RED+"You will be unable to create new events until you are");
							p.sendMessage(ChatColor.RED+"unhushed or the plugin is reloaded!");
						}
					}
					if(args[0].equalsIgnoreCase("help")){
						Help.help(p);
					}
					if(args[0].equalsIgnoreCase("tp") && p.hasPermission("evq.tp")){
						p.sendMessage(ChatColor.YELLOW + "/event tp (player name)");
					}
					if(args[0].equalsIgnoreCase("close") && p.hasPermission("evq.start")){
						EventClear.removeEvent(playername);
						p.sendMessage(ChatColor.GOLD + "You have successfully closed your event!");
					}
				}	
				if(args.length == 0){
					Help.help(p);
				}
			}	
		}
		return true;	
	}
}