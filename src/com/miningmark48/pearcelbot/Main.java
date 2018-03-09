package com.miningmark48.pearcelbot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandPrivate;
import com.miningmark48.pearcelbot.commands.base.InitializeCommands;
import com.miningmark48.pearcelbot.commands.music.soundboard.AudioHandlerSoundboard;
import com.miningmark48.pearcelbot.customcommands.GetCommand;
import com.miningmark48.pearcelbot.messages.InitializeMessages;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.richpresence.PresenceClock;
import com.miningmark48.pearcelbot.util.Clock;
import com.miningmark48.pearcelbot.util.CommandParser;
import com.miningmark48.pearcelbot.util.JSON.JSONParseFile;
import com.miningmark48.pearcelbot.util.Logger;
import com.miningmark48.pearcelbot.util.music.handler.AudioHandler;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;
import java.util.HashMap;

public class Main {

    public static JDA jda;
    static final CommandParser parser = new CommandParser();

    public static HashMap<String, ICommand> commands = new HashMap<>();

    private static String configFile = "config.json";

    public static void main(String[] args) {

        if (!getConfigs()) {
            return;
        }

        try {
            jda = new JDABuilder(AccountType.BOT).addEventListener(new BotListener()).setToken(Reference.botToken).buildBlocking();
            jda.setAutoReconnect(true);
            jda.getPresence().setGame(Game.of(Game.GameType.DEFAULT, String.format("Do %scmds", Reference.botCommandKey)));
            PresenceClock.runClockGame(jda);
            Clock.runClockUptime();

            Logger.log(Logger.LogType.STATUS, "Bot started!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        AudioSourceManagers.registerRemoteSources(AudioHandler.playerManager);
        AudioSourceManagers.registerLocalSource(AudioHandler.playerManager);
        AudioSourceManagers.registerRemoteSources(AudioHandlerSoundboard.playerManager);
        AudioSourceManagers.registerLocalSource(AudioHandlerSoundboard.playerManager);

        InitializeCommands.init();

    }

    static void handleCommand(CommandParser.CommandContainer cmd){
        if (commands.containsKey(cmd.invoke)) {
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);

            if (safe){
                if (cmd.event.getChannelType() == ChannelType.PRIVATE) {
                    if (commands.get(cmd.invoke) instanceof ICommandPrivate) {
                        ((ICommandPrivate) commands.get(cmd.invoke)).actionPrivate(cmd.args, cmd.event);
                    }
                } else {
                    commands.get(cmd.invoke).action(cmd.args, cmd.event);
                }
                commands.get(cmd.invoke).executed(safe, cmd.event);
            } else {
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }

        }
    }

    static void handleMessage(MessageReceivedEvent event){
        InitializeMessages.init(event);
    }

    static void handleCustom(MessageReceivedEvent event){
        GetCommand.init(event);
    }

    private static boolean getConfigs() {
        Logger.log(Logger.LogType.STATUS, "Getting configs...");

        try {
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

                    jw.name("roles");
                    jw.beginObject();
                        jw.name("commander").value("PBC");
                        jw.name("autoresponse").value("PBAR");
                        jw.name("nomusic").value("PB-NoMusic");
                    jw.endObject();

                    jw.name("other");
                    jw.beginObject();
                        jw.name("do chat log").value(true);
                    jw.endObject();

                jw.endObject();

                writer.close();

                Logger.log(Logger.LogType.STATUS, "Config file was created and must be filled in, stopping bot.");
                return false;
            } else {

                try {
                    JsonObject jsonObject = JSONParseFile.JSONParse(configFile);
                    if (jsonObject != null) {
                        JsonObject jsonObjectBot = jsonObject.getAsJsonObject("bot");
                        JsonObject jsonObjectRoles = jsonObject.getAsJsonObject("roles");
                        JsonObject jsonObjectOther = jsonObject.getAsJsonObject("other");

                        Reference.botName = jsonObjectBot.get("botname").getAsString();
                        Reference.botToken = jsonObjectBot.get("token").getAsString();
                        Reference.botCommandKey = jsonObjectBot.get("key").getAsString();
                        Reference.botClientID = jsonObjectBot.get("client id").getAsString();

                        Reference.botCommanderRole = jsonObjectRoles.get("commander").getAsString();
                        Reference.botAutoResponseRole = jsonObjectRoles.get("autoresponse").getAsString();
                        Reference.botNoMusicRole = jsonObjectRoles.get("nomusic").getAsString();

                        Reference.doChatLog = jsonObjectOther.get("do chat log").getAsBoolean();

                    } else {
                        throw new NullPointerException();
                    }

                } catch (NullPointerException e) {
                    Logger.log(Logger.LogType.FATAL, "Configs were unable to be loaded, stopping bot.");
                    e.printStackTrace();
                    return false;
                }

                Logger.log(Logger.LogType.STATUS, "Configs were loaded, continuing.");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
