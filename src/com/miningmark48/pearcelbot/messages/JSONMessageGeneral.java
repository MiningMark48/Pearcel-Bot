package com.miningmark48.pearcelbot.messages;

import com.google.gson.JsonObject;
import com.miningmark48.pearcelbot.util.JSONParseFile;
import net.dv8tion.jda.core.entities.Message;

public class JSONMessageGeneral {

    public static final String file = "messages//general.json";

    public static String JSONReadGeneral(Message message){

        JsonObject js;
        String msg = "";
        String content;
        content = message.getContent().toLowerCase();

        js = JSONParseFile.JSONParse(file);

        if(js.has(content)) {
            msg = js.get(content).getAsString();
        }

        return msg;

    }


}
