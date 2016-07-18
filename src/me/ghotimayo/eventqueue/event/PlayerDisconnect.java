package me.ghotimayo.eventqueue.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import me.ghotimayo.eventqueue.storage.StoreEvent;

public class PlayerDisconnect implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		Player p = event.getPlayer();
		String playername = p.getName();
		if(StoreEvent.active.containsKey(playername)){
			if(StoreEvent.active.get(playername) == true){
				StoreEvent.eventlist.remove(playername);
				StoreEvent.eventlocations.remove(playername);
				StoreEvent.active.remove(playername);
			}
		}
	}
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event){
		Player p = event.getPlayer();
		String playername = p.getName();
		if(StoreEvent.active.containsKey(playername)){
			if(StoreEvent.active.get(playername) == true){
				StoreEvent.eventlist.remove(playername);
				StoreEvent.eventlocations.remove(playername);
				StoreEvent.active.remove(playername);
			}
		}
	}
}