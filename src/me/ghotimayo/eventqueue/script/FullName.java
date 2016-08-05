package me.ghotimayo.eventqueue.script;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class FullName {
	public static String get(String name){
		for(OfflinePlayer item : Bukkit.getOfflinePlayers()){
			if(item.getName().equalsIgnoreCase(name)){
				name = item.getName();
			}
		}
		return name;
	}
}
