package com.miningmark48.tidalbot.util.features.serverconfig;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import com.miningmark48.tidalbot.util.JSON.JSONParseFile;
import com.miningmark48.tidalbot.util.LoggerUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;

public class ServerConfigHandler {

    public static String fileName = "server_configs.json";
    private static BufferedWriter bufferedWriter = null;

    public static void setupConfig() {
        try {
            File file = new File(fileName);

            if (!file.exists()) {

                Writer writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonWriter jw = gson.newJsonWriter(writer);

                jw.beginObject();

                jw.name("servers");
                jw.beginObject();

                jw.name("<guild_id>");
                jw.beginObject();

                jw.name("bot_commanders");
                jw.beginArray();
                jw.value("<client_id>");
                jw.endArray();

                jw.name("music_blacklist");
                jw.beginArray();
                jw.value("<client_id>");
                jw.endArray();

                jw.endObject();

                jw.endObject();

                jw.endObject();

                writer.close();

                LoggerUtil.log(LoggerUtil.LogType.INFO, "Server config file created.");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addBotCommander(MessageReceivedEvent event, String value) {
        addToOArray(event, "bot_commanders", value);
    }

    public static boolean isBotCommander(MessageReceivedEvent event, String value) {
        return isAddedToArray(event, "bot_commanders", value);
    }

    public static JsonArray getBotCommanders(MessageReceivedEvent event) {
        return getArray(event, "bot_commanders");
    }

    @SuppressWarnings("Duplicates")
    private static void addToOArray(MessageReceivedEvent event, String arrayName, String value) {
        String guildID = event.getGuild().getId();

        File file = new File(fileName);

        if (!file.exists()) {
            setupConfig();
        }

        try {
            JsonObject jsonObj = JSONParseFile.JSONParse(fileName);

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
            bufferedWriter = new BufferedWriter(writer);

            assert jsonObj != null;
            JsonObject servs = jsonObj.getAsJsonObject("servers");
            if (servs.getAsJsonObject(guildID) == null) {
                JsonObject tempObj = new JsonObject();
                tempObj.add(arrayName, new JsonArray());
                servs.add(guildID, tempObj);
            }

            JsonObject newObj = servs.getAsJsonObject(guildID);

            JsonArray newJson = newObj.getAsJsonArray(arrayName);

            if (!newJson.contains(new JsonPrimitive(value))) {
                newJson.add(value);
                successAdd(event, value, arrayName);
            } else {
                newJson.remove(new JsonPrimitive(value));
                successRemove(event, value, arrayName);
            }

            bufferedWriter.write(jsonObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null){
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @SuppressWarnings("Duplicates")
    private static JsonArray getArray(MessageReceivedEvent event, String arrayName) {
        String guildID = event.getGuild().getId();

        File file = new File(fileName);

        if (!file.exists()) {
            setupConfig();
            return null;
        }

        try {
            JsonObject jsonObj = JSONParseFile.JSONParse(fileName);
            assert jsonObj != null;
            JsonObject servs = jsonObj.getAsJsonObject("servers");
            if (servs.getAsJsonObject(guildID) == null) {
                return null;
            }
            JsonObject newObj = servs.getAsJsonObject(guildID);
            JsonArray newJson = newObj.getAsJsonArray(arrayName);
            return newJson;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static boolean isAddedToArray(MessageReceivedEvent event, String arrayName, String value) {
        String guildID = event.getGuild().getId();

        File file = new File(fileName);

        if (!file.exists()) {
            setupConfig();
            return false;
        }

        try {
            JsonObject jsonObj = JSONParseFile.JSONParse(fileName);
            assert jsonObj != null;
            JsonObject servs = jsonObj.getAsJsonObject("servers");
            if (servs.getAsJsonObject(guildID) == null) {
                return false;
            }
            JsonObject newObj = servs.getAsJsonObject(guildID);
            JsonArray newJson = newObj.getAsJsonArray(arrayName);
            if (newJson.contains(new JsonPrimitive(value))) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    private static void successAdd(MessageReceivedEvent event, String value, String array) {
        event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + " Successfully **added** *" + value + "* to " + array).queue();
    }

    private static void successRemove(MessageReceivedEvent event, String value, String array) {
        event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + " Successfully **removed** *" + value + "* from " + array).queue();
    }

}
