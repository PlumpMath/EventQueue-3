package me.ghotimayo.eventqueue.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.ghotimayo.eventqueue.script.GetEventList;
import me.ghotimayo.eventqueue.script.Help;
import me.ghotimayo.eventqueue.script.CheckEvent;
import me.ghotimayo.eventqueue.script.EventClear;
import me.ghotimayo.eventqueue.script.Filter;
import me.ghotimayo.eventqueue.script.FindEvent;
import me.ghotimayo.eventqueue.script.FullName;
import me.ghotimayo.eventqueue.storage.CommandNames;
import me.ghotimayo.eventqueue.storage.StoreEvent;

public class Event implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if (label.equalsIgnoreCase("event")) {
			Player p = (Player) sender;
			String playername = p.getName();
			String annmsg = StoreEvent.annmsgs.get("AnnMsg").replace("&p", playername);
			if(p.hasPermission("evq.use")){
				if (args.length >= 2) {
					if (args[0].equalsIgnoreCase("msg") && p.hasPermission("evq.msg")) {
						if(StoreEvent.inevent.containsKey(playername)){
							if(StoreEvent.chatallow.get(StoreEvent.inevent.get(playername)) == true){
								String chname = playername;
								if(StoreEvent.eventpname.containsKey(playername)){
									chname = StoreEvent.eventpname.get(playername);
								}
								String msg = "";
								for(int i = 0; i < args.length; i++){
									if(i != 0){
										String word = args[i];
										word = Filter.filter(word);
										String arg = word + " ";
										msg = msg + arg;}
								}
								for(Player ci : Bukkit.getOnlinePlayers()){
									if(StoreEvent.inevent.containsKey(ci.getName())){
										if(StoreEvent.inevent.get(ci.getName()).equals(StoreEvent.inevent.get(playername))){
											ci.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "EVENT" + ChatColor.DARK_GRAY +"-" + ChatColor.GREEN + "CHAT" + ChatColor.DARK_GRAY + "]" + ChatColor.BLUE + chname + ChatColor.DARK_GRAY + ": " + ChatColor.GOLD + msg );
										}
									}
								}
							}else{
								p.sendMessage(ChatColor.RED + "This event has chat disabled!");
							}
						}else{
							p.sendMessage(ChatColor.RED + "You are not in an event!");
						}
					}
					if (args[0].equalsIgnoreCase("start") && p.hasPermission("evq.start")) {
						if(!StoreEvent.hushlist.containsKey(playername)){
							String rp = "";
							for(int i = 0; i < args.length; i++){
								if(i != 0){
									String word = args[i];
									word = Filter.filter(word);
									String arg = word + " ";
									rp = rp + arg;}
							}
							if (StoreEvent.active.containsKey(playername) && !StoreEvent.inevent.containsKey(playername)){
								if (StoreEvent.active.get(playername)== false){
									StoreEvent.active.put(playername, true);
								}
								else{
									p.sendMessage(ChatColor.RED + "You are already in an active event!");
								}
							}
							else{
								StoreEvent.active.put(playername, true);
							}
							if (StoreEvent.active.get(playername) == true && StoreEvent.eventlist.containsKey(playername) == false){

								Location loc = p.getLocation();
								StoreEvent.eventlocations.put(playername, loc);
								StoreEvent.eventlist.put(playername,rp);
								StoreEvent.inevent.put(playername, playername);
								StoreEvent.eventlock.put(playername, StoreEvent.settings.get("EventLock"));
								StoreEvent.eventnameallow.put(playername, StoreEvent.settings.get("EventNick"));
								StoreEvent.chatallow.put(playername, StoreEvent.settings.get("EventChat"));;
								StoreEvent.warpallow.put(playername, StoreEvent.settings.get("EventWarp"));
								p.sendMessage(ChatColor.AQUA + "You have successfully created a new event!");

								if(StoreEvent.settings.get("AnnounceEvent") == true){

									for(Player item : Bukkit.getOnlinePlayers()){
										if(!item.getName().equals(playername)){
											item.sendMessage(ChatColor.GOLD + "[" + ChatColor.BLUE + ChatColor.MAGIC + "!!" + ChatColor.RESET + ChatColor.DARK_RED + "EVENT QUEUE" + ChatColor.BLUE + ChatColor.MAGIC + "!!" + ChatColor.GOLD +"] " + annmsg );
										}
									}
								}
							}
						}else{
							p.sendMessage(ChatColor.GRAY+"You have been hushed!");
							p.sendMessage(ChatColor.RED+"You will be unable to create new events until you are");
							p.sendMessage(ChatColor.RED+"unhushed or the plugin is reloaded!");
						}
					}
				}
				if (args.length == 2){
					if(args[0].equalsIgnoreCase("help")){
						String pagenum = args[1];
						if(pagenum.matches("[0-9]+")){
							int pageint = Integer.parseInt(pagenum);	
							Help.help(p, pageint);
						}	
					}
					if(args[0].equalsIgnoreCase("kick") && p.hasPermission("evq.start")){

						if(StoreEvent.inevent.containsKey(playername)){
							if(StoreEvent.active.containsKey(playername)){
								if(StoreEvent.inevent.containsKey(args[1]) && StoreEvent.inevent.get(args[1]).equals(playername)){
									for(Player ci : Bukkit.getOnlinePlayers()){
										if(StoreEvent.inevent.containsKey(ci.getName())){
											if(StoreEvent.inevent.get(ci.getName()).equals(StoreEvent.inevent.get(playername))){
												if(!ci.getName().equals(args[1])){
													ci.sendMessage(ChatColor.YELLOW + args[1] + ChatColor.RED + " was kicked from the event." );
												}else{
													StoreEvent.inevent.remove(args[1]);
													StoreEvent.eventpname.remove(args[1]);
													ci.sendMessage(ChatColor.RED + "You were kicked from the event!");
												}
											}
										}
									}
								}
							}else{
								p.sendMessage(ChatColor.RED + "This is not your event!");
							}
						}
					}
					if(args[0].equalsIgnoreCase("nick") && p.hasPermission("evq.nick")){
						if(StoreEvent.inevent.containsKey(playername)){
							if(StoreEvent.eventnameallow.get(StoreEvent.inevent.get(playername)) == true){
								String chnick = args[1];
								StoreEvent.eventpname.put(playername, chnick);
								p.sendMessage(ChatColor.AQUA + "Your nickname in this event is " + ChatColor.YELLOW + chnick + ChatColor.AQUA + "!");
							}else{
								p.sendMessage(ChatColor.RED + "The leader of this event has nicknames disabled.");
							}
						}else{
							p.sendMessage(ChatColor.RED + "You are not in an event!");
						}
					}
					if(args[0].equalsIgnoreCase("join") && p.hasPermission("evq.join")){
						String toplayer = args[1];
						toplayer = FullName.get(toplayer);
						if(StoreEvent.active.containsKey(toplayer)){
							if(!StoreEvent.inevent.containsKey(playername)){
								if(StoreEvent.eventlock.get(toplayer) == false){
									StoreEvent.inevent.put(playername, toplayer);
									p.sendMessage(ChatColor.GREEN + "You have successfully joined " + ChatColor.YELLOW + toplayer + "'s " + ChatColor.GREEN + "event!");


									for(Player ci : Bukkit.getOnlinePlayers()){
										if(StoreEvent.inevent.containsKey(ci.getName())){
											if(StoreEvent.inevent.get(ci.getName()).equals(StoreEvent.inevent.get(playername))){
												if(!ci.getName().equals(playername)){
													ci.sendMessage(ChatColor.YELLOW + playername + ChatColor.GREEN + " has joined the event." );
												}
											}
										}
									}
								}else{
									p.sendMessage(ChatColor.RED + "This event is locked.");
								}
							}
						}else{
							p.sendMessage(ChatColor.RED + "This event does not exist!");
						}
					}
					if(args[0].equalsIgnoreCase("tp") && p.hasPermission("evq.tp")){
						String toplayer = args[1];
						toplayer = FullName.get(toplayer);
						if(StoreEvent.eventlocations.containsKey(toplayer)){
							if(StoreEvent.warpallow.get(toplayer)== true){
								Location toloc = StoreEvent.eventlocations.get(toplayer);
								p.teleport(toloc);
							}else{
								p.sendMessage(ChatColor.RED + "This event has tp disabled!");
							}
						}
					}
					if(args[0].equalsIgnoreCase("remove") && p.hasPermission("evq.mod")){
						String toplayer = args[1];
						toplayer = FullName.get(toplayer);
						EventClear.removeEvent(toplayer);
						p.sendMessage(ChatColor.GOLD + "You have successfully deleted "+toplayer+"'s event!");
					}
					if(args[0].equalsIgnoreCase("hush") && p.hasPermission("evq.mod")){
						String toplayer = args[1];
						toplayer = FullName.get(toplayer);
						StoreEvent.hushlist.put(toplayer,toplayer);
						p.sendMessage(ChatColor.GREEN + "You have successfully hushed "+toplayer+"!");
					}
					if(args[0].equalsIgnoreCase("check") && p.hasPermission("evq.list")){
						String toplayer = args[1];
						toplayer = FullName.get(toplayer);
						CheckEvent.check(toplayer ,p);
					}
					if(args[0].equalsIgnoreCase("unhush") && p.hasPermission("evq.mod")){
						String toplayer = args[1];
						toplayer = FullName.get(toplayer);
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
				if(args.length >= 1 && args.length <= 3){	

					if(args[0].equalsIgnoreCase("find") && p.hasPermission("evq.list")){
						if(StoreEvent.eventlist.size() == 0){
							p.sendMessage(ChatColor.RED + "There are currently no open events!");
						}
						if(args.length == 1){
							p.sendMessage(ChatColor.YELLOW + "/event find (keyword) (page number)");	
						}
						if(args.length == 2){
							String pagenum = "1";
							if(pagenum.matches("[0-9]+")){
								int pageint = Integer.parseInt(pagenum);	
								FindEvent.sendFindList(p, args[1], pageint);
							}
						}
						if(args.length == 3){
							String pagenum = args[2];
							if(pagenum.matches("[0-9]+")){
								int pageint = Integer.parseInt(pagenum);	
								FindEvent.sendFindList(p, args[1], pageint);
							}	
						}
					}					
				}
				if (args.length == 1){
					if(args[0].equalsIgnoreCase("leave")){
						if(StoreEvent.inevent.containsKey(playername)){
							if(StoreEvent.inevent.get(playername) != playername){
								for(Player ci : Bukkit.getOnlinePlayers()){
									if(StoreEvent.inevent.containsKey(ci.getName())){
										if(StoreEvent.inevent.get(ci.getName()).equals(StoreEvent.inevent.get(playername))){
											if(!ci.getName().equals(playername)){
												ci.sendMessage(ChatColor.YELLOW + playername + ChatColor.GREEN + " has left the event." );
											}
										}
									}
								}
								StoreEvent.inevent.remove(playername);
								StoreEvent.eventpname.remove(playername);
								p.sendMessage(ChatColor.GREEN + "You have successfully left the event!");
							}
						}
					}
					if(args[0].equalsIgnoreCase("hush") && p.hasPermission("evq.mod")){
						p.sendMessage(ChatColor.YELLOW + "/event hush (player name)");
					}
					if(args[0].equalsIgnoreCase("unhush") && p.hasPermission("evq.mod")){
						p.sendMessage(ChatColor.YELLOW + "/event unhush (player name)");
					}
					if(args[0].equalsIgnoreCase("enablenick") && p.hasPermission("evq.enablenick")){
						if(StoreEvent.inevent.containsKey(playername)){
							if(StoreEvent.inevent.get(playername).equals(playername)){
								StoreEvent.eventnameallow.put(playername, true);
								p.sendMessage(ChatColor.GREEN + "Nicknames in event chat enabled!");
							}else{
								p.sendMessage(ChatColor.RED + "This is not your event!");
							}
						}else{
							p.sendMessage(ChatColor.RED + "You are not in an event!");

						}
					}
					if(args[0].equalsIgnoreCase("enablechat") && p.hasPermission("evq.enablechat")){
						if(StoreEvent.inevent.containsKey(playername)){
							if(StoreEvent.inevent.get(playername).equals(playername)){
								StoreEvent.chatallow.put(playername, true);
								p.sendMessage(ChatColor.GREEN + "Event chat enabled!");
							}else{
								p.sendMessage(ChatColor.RED + "This is not your event!");
							}
						}else{
							p.sendMessage(ChatColor.RED + "You are not in an event!");

						}
					}
					if(args[0].equalsIgnoreCase("lock") && p.hasPermission("evq.enablelock")){
						if(StoreEvent.inevent.containsKey(playername)){
							if(StoreEvent.inevent.get(playername).equals(playername)){
								StoreEvent.eventlock.put(playername, true);
								p.sendMessage(ChatColor.GREEN + "Event locked!");
							}else{
								p.sendMessage(ChatColor.RED + "This is not your event!");
							}
						}else{
							p.sendMessage(ChatColor.RED + "You are not in an event!");
						}
					}
					if(args[0].equalsIgnoreCase("unlock") && p.hasPermission("evq.enablelock")){
						if(StoreEvent.inevent.containsKey(playername)){
							if(StoreEvent.inevent.get(playername).equals(playername)){
								StoreEvent.eventlock.put(playername, false);
								p.sendMessage(ChatColor.GREEN + "Event unlocked!");
							}else{
								p.sendMessage(ChatColor.RED + "This is not your event!");
							}
						}else{
							p.sendMessage(ChatColor.RED + "You are not in an event!");
						}
					}
					if(args[0].equalsIgnoreCase("enablewarp") && p.hasPermission("evq.enablewarp")){
						if(StoreEvent.inevent.containsKey(playername)){
							if(StoreEvent.inevent.get(playername).equals(playername)){
								StoreEvent.warpallow.put(playername, true);
								p.sendMessage(ChatColor.GREEN + "Warp allowed!");
							}else{
								p.sendMessage(ChatColor.RED + "This is not your event!");
							}
						}else{
							p.sendMessage(ChatColor.RED + "You are not in an event!");
						}
					}
					if(args[0].equalsIgnoreCase("disablewarp") && p.hasPermission("evq.disablewarp")){
						if(StoreEvent.inevent.containsKey(playername)){
							if(StoreEvent.inevent.get(playername).equals(playername)){
								StoreEvent.warpallow.put(playername, false);
								p.sendMessage(ChatColor.GREEN + "Event unlocked!");
							}else{
								p.sendMessage(ChatColor.RED + "This is not your event!");
							}
						}else{
							p.sendMessage(ChatColor.RED + "You are not in an event!");
						}
					}
					if(args[0].equalsIgnoreCase("disablechat") && p.hasPermission("evq.enablechat")){
						if(StoreEvent.inevent.containsKey(playername)){
							if(StoreEvent.inevent.get(playername).equals(playername)){
								StoreEvent.chatallow.put(playername, false);
								p.sendMessage(ChatColor.GREEN + "Event chat disabled!");
							}else{
								p.sendMessage(ChatColor.RED + "This is not your event!");
							}
						}else{
							p.sendMessage(ChatColor.RED + "You are not in an event!");
						}
					}
					if(args[0].equalsIgnoreCase("disablenick") && p.hasPermission("evq.enablenick")){
						if(StoreEvent.inevent.containsKey(playername)){
							if(StoreEvent.inevent.get(playername).equals(playername)){
								StoreEvent.eventnameallow.put(playername, false);
								for(Player item : Bukkit.getOnlinePlayers()){
									if(StoreEvent.inevent.containsKey(item.getName())){
										if(StoreEvent.inevent.get(item.getName()).equals(playername)){
											StoreEvent.eventpname.put(item.getName(), item.getName());
										}
									}
								}
								p.sendMessage(ChatColor.GREEN + "Nicknames in event chat have been disabled!");
							}else{
								p.sendMessage(ChatColor.RED + "This is not your event!");
							}
						}else{
							p.sendMessage(ChatColor.RED + "You are not in an event!");
						}
					}
					if(args[0].equalsIgnoreCase("remove") && p.hasPermission("evq.mod")){
						p.sendMessage(ChatColor.YELLOW + "/event remove (player name)");
					}
					if (args[0].equalsIgnoreCase("start") && p.hasPermission("evq.start")) {
						if(!StoreEvent.hushlist.containsKey(playername)){
							String rp = ChatColor.GRAY+"No Description!";
							if (StoreEvent.active.containsKey(playername) && !StoreEvent.inevent.containsKey(playername)){
								if (StoreEvent.active.get(playername)== false){
									StoreEvent.active.put(playername, true);
								}
								else{
									p.sendMessage(ChatColor.RED + "You are already in an active event!");
								}
							}
							else{
								StoreEvent.active.put(playername, true);
							}
							if (StoreEvent.active.get(playername) == true && StoreEvent.eventlist.containsKey(playername) == false){
								Location loc = p.getLocation();
								StoreEvent.eventlocations.put(playername, loc);
								StoreEvent.eventlist.put(playername,rp);
								StoreEvent.inevent.put(playername, playername);
								StoreEvent.eventlock.put(playername, StoreEvent.settings.get("EventLock"));
								StoreEvent.eventnameallow.put(playername, StoreEvent.settings.get("EventNick"));
								StoreEvent.chatallow.put(playername, StoreEvent.settings.get("EventChat"));;
								StoreEvent.warpallow.put(playername, StoreEvent.settings.get("EventWarp"));
								p.sendMessage(ChatColor.AQUA + "You have successfully created a new event!");
								if(StoreEvent.settings.get("AnnounceEvent") == true){

									for(Player item : Bukkit.getOnlinePlayers()){
										if(!item.getName().equals(playername)){
											item.sendMessage(ChatColor.GOLD + "[" + ChatColor.BLUE + ChatColor.MAGIC + "!!" + ChatColor.RESET + ChatColor.DARK_RED + "EVENT QUEUE" + ChatColor.BLUE + ChatColor.MAGIC + "!!" + ChatColor.GOLD +"] " + annmsg );
										}
									}
								}
							}
						}else{
							p.sendMessage(ChatColor.GRAY+"You have been hushed!");
							p.sendMessage(ChatColor.RED+"You will be unable to create new events until you are");
							p.sendMessage(ChatColor.RED+"unhushed or the plugin is reloaded!");
						}
					}
					if(args[0].equalsIgnoreCase("help")){
						Help.help(p , 1);
					}
					if(args[0].equalsIgnoreCase("tp") && p.hasPermission("evq.tp")){
						p.sendMessage(ChatColor.YELLOW + "/event tp (player name)");
					}
					if(args[0].equalsIgnoreCase("close") && p.hasPermission("evq.start")){
						EventClear.removeEvent(playername);
						p.sendMessage(ChatColor.GOLD + "You have successfully closed your event!");
						for(Player item : Bukkit.getOnlinePlayers()){
							String itemname = item.getName();
							if(StoreEvent.inevent.get(itemname) == playername){
								StoreEvent.inevent.remove(itemname);
								if(itemname != playername){
									item.sendMessage(ChatColor.RED + "The event you were in has just been closed.");
								}
							}
						}
					}
				}
				if(args.length == 0){
					Help.help(p , 1);
				}
				if(args.length >= 1){
					boolean good = false;
					for(String cm : CommandNames.cmds){
						if(args[0].equalsIgnoreCase(cm)){
							good = true;
						}
					}
					if(good == false){
						p.sendMessage(ChatColor.RED + "That is not a valid EventQueue command.");
						p.sendMessage(ChatColor.RED + "Use /event help (page number) for a list of event commands.");
					}
					good = false;
					if(args[0].equalsIgnoreCase("start") && !p.hasPermission("evq.start")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("tp") && !p.hasPermission("evq.tp")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("list") && !p.hasPermission("evq.list")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("remove") && !p.hasPermission("evq.mod")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("hush") && !p.hasPermission("evq.mod")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("unhush") && !p.hasPermission("evq.mod")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("join") && !p.hasPermission("evq.join")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("msg") && !p.hasPermission("evq.msg")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("nick") && !p.hasPermission("evq.nick")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("kick") && !p.hasPermission("evq.start")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("lock") && !p.hasPermission("evq.enablelock")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("unlock") && !p.hasPermission("evq.enablelock")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("enablechat") && !p.hasPermission("evq.enablechat")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("disablechat") && !p.hasPermission("evq.enablechat")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("enablewarp") && !p.hasPermission("evq.enablewarp")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("disablewarp") && !p.hasPermission("evq.enablewarp")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("enablenick") && !p.hasPermission("evq.enablenick")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("disablenick") && !p.hasPermission("evq.enablenick")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("check") && !p.hasPermission("evq.list")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
					if(args[0].equalsIgnoreCase("find") && !p.hasPermission("evq.list")){
						p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
						p.sendMessage(ChatColor.RED + "EventQueue command.");
					}
				}
			}else{
				p.sendMessage(ChatColor.RED + "You do not have the permission required to use this");
				p.sendMessage(ChatColor.RED + "EventQueue command.");
			}
		}
		return true;	
	}
}