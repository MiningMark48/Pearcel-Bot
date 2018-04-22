package com.miningmark48.tidalbot.messages;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.miningmark48.tidalbot.util.JSON.JSONParseFile;
import net.dv8tion.jda.core.entities.Message;

import java.util.Random;

public class JSONMessageReplies {

    public static final String file = "messages//replies.json";

    public static String JSONReadReplies(int type){

        JsonObject js;

        js = JSONParseFile.JSONParse(file);

        JsonArray replies;

        switch (type){
            default:
            case 0:
                replies = js.get("goodReply").getAsJsonArray();
                break;
            case 1:
                replies = js.get("sosoReply").getAsJsonArray();
                break;
            case 2:
                replies = js.get("badReply").getAsJsonArray();
                break;
            case 3:
                replies = js.get("doingReply").getAsJsonArray();
        }

        Random rand = new Random();
        int num = rand.nextInt(replies.size() - 1);
        String msg = replies.get(num).getAsString();

        return msg;

    }

    public static boolean JSONReadRepliesWithin(Message message){

        JsonObject js;
        boolean flag = false;

        js = JSONParseFile.JSONParse(file);

        JsonArray repliesGood = js.get("good").getAsJsonArray();
        JsonArray repliesSoso = js.get("soso").getAsJsonArray();
        JsonArray repliesBad = js.get("bad").getAsJsonArray();
        JsonArray repliesDoing = js.get("doing").getAsJsonArray();

        for (int i = 0; i <= repliesGood.size() - 1 && !flag; i++){
            if (message.getContentRaw().toLowerCase().contains(repliesGood.get(i).getAsString().toLowerCase())){
                flag = true;
            }
        }
        for (int i = 0; i <= repliesSoso.size() - 1 && !flag; i++){
            if (message.getContentRaw().toLowerCase().contains(repliesSoso.get(i).getAsString().toLowerCase())){
                flag = true;
            }
        }
        for (int i = 0; i <= repliesBad.size() - 1 && !flag; i++){
            if (message.getContentRaw().toLowerCase().contains(repliesBad.get(i).getAsString().toLowerCase())){
                flag = true;
            }
        }
        for (int i = 0; i <= repliesDoing.size() - 1 && !flag; i++){
            if (message.getContentRaw().toLowerCase().contains(repliesDoing.get(i).getAsString().toLowerCase())){
                flag = true;
            }
        }

        return flag;

    }


    public static int JSONReadRepliesType(Message message){

        JsonObject js;
        int type = 0;
        boolean flag = false;

        js = JSONParseFile.JSONParse(file);

        JsonArray repliesGood = js.get("good").getAsJsonArray();
        JsonArray repliesSoso = js.get("soso").getAsJsonArray();
        JsonArray repliesBad = js.get("bad").getAsJsonArray();
        JsonArray repliesDoing = js.get("doing").getAsJsonArray();

        for (int i = 0; i <= repliesGood.size() - 1 && !flag; i++){
            if (message.getContentRaw().toLowerCase().contains(repliesGood.get(i).getAsString().toLowerCase())){
                flag = true;
                type = 0;
            }
        }
        for (int i = 0; i <= repliesSoso.size() - 1 && !flag; i++){
            if (message.getContentRaw().toLowerCase().contains(repliesSoso.get(i).getAsString().toLowerCase())){
                flag = true;
                type = 1;
            }
        }
        for (int i = 0; i <= repliesBad.size() - 1 && !flag; i++){
            if (message.getContentRaw().toLowerCase().contains(repliesBad.get(i).getAsString().toLowerCase())){
                flag = true;
                type = 2;
            }
        }
        for (int i = 0; i <= repliesDoing.size() - 1 && !flag; i++){
            if (message.getContentRaw().toLowerCase().contains(repliesDoing.get(i).getAsString().toLowerCase())){
                flag = true;
                type = 3;
            }
        }

        return type;

    }

}
