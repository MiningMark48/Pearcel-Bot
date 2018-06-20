package com.miningmark48.tidalbot.util.features.serverconfig;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import com.miningmark48.tidalbot.util.LoggerUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;

public class ServerConfigHandler {

    //Setup Config
    public static void setupConfig() {
        try {
            File file = new File(JsonConfigHandler.fileName);

            if (!file.exists()) {

                Writer writer = new OutputStreamWriter(new FileOutputStream(JsonConfigHandler.fileName), "UTF-8");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonWriter jw = gson.newJsonWriter(writer);

                jw.beginObject();

                jw.name("servers");
                jw.beginObject();

                jw.endObject();

                jw.endObject();

                writer.close();

                LoggerUtil.log(LoggerUtil.LogType.INFO, "Server config file created.");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Bot Commander
    public static void toggleBotCommander(MessageReceivedEvent event, String value) {
        JsonConfigHandler.toggleOnArray(event, "bot_commanders", value);
    }

    public static boolean isBotCommander(MessageReceivedEvent event, String value) {
        return JsonConfigHandler.isInArray(event, "bot_commanders", value);
    }

    public static JsonArray getBotCommanders(MessageReceivedEvent event) {
        return JsonConfigHandler.getArray(event, "bot_commanders");
    }

    //Ar Blacklist
    public static void toggleARBlacklistUser(MessageReceivedEvent event, String value) {
        JsonConfigHandler.toggleOnArray(event, "ar_blacklist", value);
    }

    public static boolean isUserARBlacklisted(MessageReceivedEvent event, String value) {
        return JsonConfigHandler.isInArray(event, "ar_blacklist", value);
    }

    //AR
    public static void toggleAR(MessageReceivedEvent event) {
        JsonConfigHandler.setPropertyBoolean(event, "ar_enabled", !isAREnabled(event));
    }

    public static boolean isAREnabled(MessageReceivedEvent event) {
        return JsonConfigHandler.isPropertyBoolean(event, "ar_enabled");
    }

    //Music Blacklist
    public static void toggleMusicUserBlacklist(MessageReceivedEvent event, String value) {
        JsonConfigHandler.toggleOnArray(event, "music_blacklist", value);
    }

    public static boolean isMusicBlacklisted(MessageReceivedEvent event, String value) {
        return JsonConfigHandler.isInArray(event, "music_blacklist", value);
    }

    public static JsonArray getBannedMusicUsers(MessageReceivedEvent event) {
        return JsonConfigHandler.getArray(event, "music_blacklist");
    }

    //Command Blacklist
    public static void toggleCommandBlacklist(MessageReceivedEvent event, String value) {
        JsonConfigHandler.toggleOnArray(event, "commands_blacklist", value);
    }

    public static boolean isCommandBlacklisted(MessageReceivedEvent event, String value) {
        return JsonConfigHandler.isInArray(event, "commands_blacklist", value);
    }

    public static JsonArray getBlacklistedCommands(MessageReceivedEvent event) {
        return JsonConfigHandler.getArray(event, "commands_blacklist");
    }

    //Music - nowplaying
    public static void toggleNPMessages(MessageReceivedEvent event) {
        JsonConfigHandler.setPropertyBoolean(event, "np_messages_disabled", !areNPMessagesDisabled(event.getGuild().getId()));
    }

    public static boolean areNPMessagesDisabled(String id) {
        return JsonConfigHandler.isPropertyBoolean(id, "np_messages_disabled");
    }

}
