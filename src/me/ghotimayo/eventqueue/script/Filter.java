package me.ghotimayo.eventqueue.script;

import java.util.regex.Pattern;

import me.ghotimayo.eventqueue.storage.StoreEvent;

public class Filter {

	static Pattern punc = Pattern.compile("[,.;!?(){}\\[\\]<>%]");

	public static String filter(String word){
		if(StoreEvent.settings.get("Filter") == true){
			String word2 = punc.matcher(word).replaceAll("");
			String[] list = StoreEvent.annmsgs.get("NaughtyWords").split(",");
			for(String item : list){
				if(word2.equalsIgnoreCase(item)){
					if (word2.length() > 3) {
						if(word2.substring(word2.length() - 3).equalsIgnoreCase("ing")){
							word = "bleeping";
							if(StoreEvent.settings.get("FrankMode") == true){
								word = "Franking";
							}
						}else{
							word = "bleep";
							if(StoreEvent.settings.get("FrankMode") == true){
								word = "Frank";
							}
						}
					}
				}
			}
			word2 = null;
		}
		return word;
	}
}
