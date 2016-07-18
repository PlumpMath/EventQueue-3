package me.ghotimayo.eventqueue;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.ghotimayo.eventqueue.event.PlayerDisconnect;
import me.ghotimayo.eventqueue.commands.Event;

public class EventQueue extends JavaPlugin{

	public void onEnable(){
		registerEvents();
		getCommand("Event").setExecutor(new Event());
	}

	public void onDisable(){
		System.out.println("See you next time!");
	}

	public void registerEvents(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerDisconnect(), this);
	}
}