package me.ghotimayo.eventqueue.script;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GetPlayer {
	public static Player getEP(String playername){
		Player p = null;
		for(Player item : Bukkit.getOnlinePlayers()){
			if(playername.equalsIgnoreCase(item.getName())){
				p = item;
			}
		}
		return p;
	}
}
