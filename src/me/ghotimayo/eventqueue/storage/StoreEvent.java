package me.ghotimayo.eventqueue.storage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class StoreEvent {
	public static LinkedHashMap<String, String> eventlist = new LinkedHashMap<>();
	public static HashMap<String, String> hushlist = new HashMap<>();
	public static HashMap<String, Location> eventlocations = new HashMap<>();
	public static HashMap<String, Boolean> active = new HashMap<>();
	public static HashMap<Player, Integer> pageInstance = new HashMap<>();
	public static HashMap<String, String> inevent = new HashMap<>();
	public static HashMap<String, Boolean> eventnameallow = new HashMap<>();
	public static HashMap<String, String> eventpname = new HashMap<>();
	public static HashMap<String, String> annmsgs = new HashMap<>();
	public static HashMap<String, Boolean> eventlock = new HashMap<>();
	public static HashMap<String, Boolean> chatallow = new HashMap<>();
	public static HashMap<String, Boolean> settings = new HashMap<>();
	public static HashMap<String, Boolean> warpallow = new HashMap<>();
}