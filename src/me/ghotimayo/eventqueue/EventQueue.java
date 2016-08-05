package me.ghotimayo.eventqueue;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.ghotimayo.eventqueue.event.PlayerDisconnect;
import me.ghotimayo.eventqueue.storage.StoreEvent;
import me.ghotimayo.eventqueue.commands.Event;

public class EventQueue extends JavaPlugin{

	public void onEnable(){
		registerEvents();
		getCommand("Event").setExecutor(new Event());
		registerConfig();
		messageSet();
		getSettings();
	}

	public void onDisable(){
		System.out.println("See you next time!");
	}

	public void registerEvents(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerDisconnect(), this);
	}
	private void registerConfig() {
		this.getConfig().options().copyDefaults(true);
		config.options().copyHeader(true);
		saveConfig();
	}

	FileConfiguration config = this.getConfig();

	public void messageSet(){
		if (config.get("AnnounceEvent")==null){
			config.createSection("AnnounceEvent");
			config.set("AnnounceEvent", true);
		}
		if (config.get("DefaultAllowEventNick")==null){
			config.createSection("DefaultAllowEventNick");
			config.set("DefaultAllowEventNick", false);
		}
		if (config.get("DefaultAllowEventWarp")==null){
			config.createSection("DefaultAllowEventWarp");
			config.set("DefaultAllowEventWarp", true);
		}
		if (config.get("DefaultAllowEventChat")==null){
			config.createSection("DefaultAllowEventChat");
			config.set("DefaultAllowEventChat", true);
		}
		if (config.get("DefaultAllowEventLock")==null){
			config.createSection("DefaultAllowEventLock");
			config.set("DefaultAllowEventLock", false);
		}
		if (config.get("AnnounceMessage")==null){
			config.createSection("AnnounceMessage");
			config.set("AnnounceMessage", ChatColor.RED + "&p" + ChatColor.AQUA + " just started a new event!");
		}
		if (config.get("NaughtyWords")==null){
			config.createSection("NaughtyWords");
			config.set("NaughtyWords", "word1,word2,word3");
		}
		if (config.get("Filter")==null){
			config.createSection("Filter");
			config.set("Filter", false);
		}
		if (config.get("FrankMode")==null){
			config.createSection("FrankMode");
			config.set("FrankMode", false);
		}
		this.saveConfig();
	}

	public void getSettings(){
		StoreEvent.settings.put("AnnounceEvent", config.getBoolean("AnnounceEvent"));
		StoreEvent.settings.put("EventNick", config.getBoolean("DefaultAllowEventNick"));
		StoreEvent.settings.put("EventWarp", config.getBoolean("DefaultAllowEventWarp"));
		StoreEvent.settings.put("EventChat", config.getBoolean("DefaultAllowEventChat"));
		StoreEvent.settings.put("EventLock", config.getBoolean("DefaultAllowEventLock"));
		StoreEvent.settings.put("Filter", config.getBoolean("Filter"));
		StoreEvent.settings.put("FrankMode", config.getBoolean("FrankMode"));
		StoreEvent.annmsgs.put("AnnMsg",  config.getString("AnnounceMessage"));
		StoreEvent.annmsgs.put("NaughtyWords", config.getString("NaughtyWords"));
	}
}