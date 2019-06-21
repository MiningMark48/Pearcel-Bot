package com.miningmark48.tidalbot.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.miningmark48.tidalbot.util.UtilFormat.FormatType;
import com.miningmark48.tidalbot.util.JSON.JSONParse;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.Random;

public class UtilYoutubeSearch {

    public static Object searchYoutube(String query, MessageReceivedEvent event, SearchType type) {
        String newQuery = UtilFormat.removeNonAlphanumeric(query).replace(" ", "%20");
        String[] queryArray = {"", newQuery};
        return searchYoutube(queryArray, event, type);
    }

    public static Object searchYoutube(String[] query, MessageReceivedEvent event, SearchType type){
        int resultNum = 0;
        Random random = new Random();
        JsonObject js;
        TextChannel channel = event.getTextChannel();
        String url = "https://www.youtube.com/watch?v=";
        StringBuilder builder = new StringBuilder();
        String queryString;

        Arrays.stream(query).forEach(q -> builder.append(q).append("%20"));

        if (type.equals(SearchType.REMIX)){
            builder.append("%20remix");
        }

        queryString = builder.toString();

        if (query.length <= 0){
            channel.sendMessage("No search query was provided!").queue();
            return null;
        } else {
            try {
                js = JSONParse.JSONParse(String.format("https://www.googleapis.com/youtube/v3/search?&key=AIzaSyBnt38rBPV1WAZGx6imcMvp0GuuQU15YKE&part=snippet&type=video&q=%s", queryString));
                JsonElement js2 = js.get("items");
                if (type.equals(SearchType.QUERY)) return js2;
                if (type.equals(SearchType.RANDOM)){
                    JsonObject jsonObjectPageInfo = js.getAsJsonObject("pageInfo");
                    resultNum = random.nextInt(jsonObjectPageInfo.get("resultsPerPage").getAsInt());
                }
                JsonObject jsonObject = js2.getAsJsonArray().get(resultNum).getAsJsonObject();
                JsonObject jsonObjectID = jsonObject.get("id").getAsJsonObject();
//                JsonObject jsonObjectSnippet = jsonObject.get("snippet").getAsJsonObject();

                channel.sendMessage("\uD83D\uDD0D" + UtilFormat.formatText(FormatType.ITALIC,"Searching...")).queue();

                return url + jsonObjectID.get("videoId").getAsString();

            } catch (NullPointerException | IndexOutOfBoundsException e){
                channel.sendMessage(UtilFormat.formatText(FormatType.BOLD, "Error: ") + "Could not retrieve data!").queue();
                UtilLogger.log(UtilLogger.LogType.WARN, "Query: -- " + queryString + " --");
                e.printStackTrace();
            }
        }

        return null;

    }

    public enum SearchType{
        NORMAL,
        RANDOM,
        REMIX,
        QUERY
    }

}
