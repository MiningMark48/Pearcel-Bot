package com.miningmark48.pearcelbot.util.features.chatlog;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.miningmark48.pearcelbot.util.JSON.JSONParseFile;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class JSONChatLog {

    public static final String file = "chatlog//server_blacklist.json";

    public static boolean isChatlogBlacklist(MessageReceivedEvent event){

        JsonObject js;
        js = JSONParseFile.JSONParse(file);

        JsonElement servBlacklistName = js.get("blacklistName");
        JsonElement servBlacklistId = js.get("blacklistId");

        if(servBlacklistName.toString().toLowerCase().contains(event.getGuild().getName().toString().toLowerCase()) || servBlacklistId.toString().toLowerCase().contains(event.getGuild().getId().toString())){
            return true;
        }

        return false;

    }

}
