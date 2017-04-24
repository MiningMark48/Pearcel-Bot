package com.miningmark48.pearcelbot.messages;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.miningmark48.pearcelbot.util.JSON.JSONParseFile;
import net.dv8tion.jda.core.entities.Message;

import java.util.Random;

public class JSONMessageGreetings {

    public static final String file = "messages//greetings.json";

    public static String JSONReadGreet(){

        JsonObject js;

        js = JSONParseFile.JSONParse(file);

        JsonArray greetings = js.get("greetings").getAsJsonArray();

        Random rand = new Random();
        int num = rand.nextInt(greetings.size() - 1);
        String msg = greetings.get(num).getAsString();

        return msg;

    }

    public static boolean JSONReadGreetWithin(Message message){

        JsonObject js;
        boolean flag = false;

        js = JSONParseFile.JSONParse(file);

        JsonArray greetings = js.get("greetings").getAsJsonArray();

        for (int i = 0; i <= greetings.size() - 1; i++){
            if (!flag && message.getContent().toLowerCase().contains(greetings.get(i).getAsString().toLowerCase())){
                flag = true;
            }
        }

        return flag;

    }

    public static String JSONReadGreetFollowup(){

        JsonObject js;

        js = JSONParseFile.JSONParse(file);

        JsonArray greetings = js.get("greetFollowup").getAsJsonArray();

        Random rand = new Random();
        int num = rand.nextInt(greetings.size() - 1);
        String msg = greetings.get(num).getAsString();

        return msg;

    }

}
