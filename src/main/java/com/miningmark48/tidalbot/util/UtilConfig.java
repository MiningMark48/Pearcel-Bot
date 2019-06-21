package com.miningmark48.tidalbot.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.JSON.JSONParseFile;

import java.io.*;

@SuppressWarnings("Duplicates")
public class UtilConfig {

    public static boolean getConfigs() {
        UtilLogger.log(UtilLogger.LogType.STATUS, "Fetching configuration settings...");

        try {
            String configFile = "config.json";
            File file = new File(configFile);

            if (!file.exists()) {
                Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonWriter jw = gson.newJsonWriter(writer);

                jw.beginObject();

                jw.name("bot");
                jw.beginObject();
                jw.name("botname").value("Bot Name");
                jw.name("token").value("bot.token");
                jw.name("key").value("bot.key");
                jw.name("client id").value("bot.id");
                jw.endObject();

                jw.name("other");
                jw.beginObject();
                jw.name("do chat log").value(true);
                jw.endObject();

                jw.endObject();

                writer.close();

                UtilLogger.log(UtilLogger.LogType.STATUS, "Configuration file was created and must be filled in, stopping bot.");
                return false;
            } else {

                try {
                    JsonObject jsonObject = JSONParseFile.JSONParse(configFile);
                    if (jsonObject != null) {
                        JsonObject jsonObjectBot = jsonObject.getAsJsonObject("bot");
                        JsonObject jsonObjectOther = jsonObject.getAsJsonObject("other");

                        Reference.botName = jsonObjectBot.get("botname").getAsString();
                        Reference.botToken = jsonObjectBot.get("token").getAsString();
                        Reference.botCommandKey = jsonObjectBot.get("key").getAsString();
                        Reference.botCommandCustomKey = Reference.botCommandKey + Reference.botCommandKey;
                        Reference.botClientID = jsonObjectBot.get("client id").getAsString();

                        Reference.doChatLog = jsonObjectOther.get("do chat log").getAsBoolean();

                        Reference.updateJoinLink();

                    } else {
                        throw new NullPointerException();
                    }

                } catch (NullPointerException e) {
                    UtilLogger.log(UtilLogger.LogType.FATAL, "Configuration settings were unable to be loaded, stopping bot.");
                    e.printStackTrace();
                    return false;
                }

                UtilLogger.log(UtilLogger.LogType.STATUS, "Configuration settings were loaded, continuing.");
                return true;
            }
        } catch (IOException e) {
            UtilLogger.log(UtilLogger.LogType.FATAL, "An error has occurred.");
            e.printStackTrace();
            return false;
        }
    }

}
