package com.miningmark48.tidalbot.commands.botcommander;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import com.miningmark48.tidalbot.Main;
import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.commands.base.InitializeCommands;
import com.miningmark48.tidalbot.util.JSON.JSONParseFile;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;

public class CommandToggleCommand implements ICommand, ICommandInfo {

    public static String fileName = "command_blacklist.json";
    private BufferedWriter bufferedWriter = null;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (args.length <= 0) {
            event.getTextChannel().sendMessage("Missing args!").queue();
            return;
        }

        if (!Main.commands.containsKey(args[0]) || args[0].equalsIgnoreCase(this.getName())) {
            event.getTextChannel().sendMessage("That is not a valid command!").queue();
            return;
        }

        try {

            File file = new File(fileName);

            if (!file.exists()) {

                Writer writer = new OutputStreamWriter(new FileOutputStream(fileName) , "UTF-8");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonWriter jw = gson.newJsonWriter(writer);

                jw.beginObject();

                jw.name("servers");
                jw.beginObject();

                jw.name(event.getGuild().getId());
                jw.beginObject();

                jw.name("commands");
                jw.beginArray();
                jw.value(args[0]);
                jw.endArray();

                jw.endObject();

                jw.endObject();

                jw.endObject();

                writer.close();
                successAdd(event, args[0]);
            } else {
                JsonObject jsonObj = JSONParseFile.JSONParse(fileName);

                Writer writer = new OutputStreamWriter(new FileOutputStream(fileName) , "UTF-8");
                bufferedWriter = new BufferedWriter(writer);

                assert jsonObj != null;
                JsonObject servs = jsonObj.getAsJsonObject("servers");
                if (servs.getAsJsonObject(event.getGuild().getId()) == null) {
                    JsonObject tempObj = new JsonObject();
                    tempObj.add("commands", new JsonArray());
                    servs.add(event.getGuild().getId(), tempObj);
                }

                JsonObject newObj = servs.getAsJsonObject(event.getGuild().getId());

                JsonArray newJson = newObj.getAsJsonArray("commands");

                if (!newJson.contains(new JsonPrimitive(args[0]))) {
                    newJson.add(args[0]);
                    successAdd(event, args[0]);
                } else {
                    newJson.remove(new JsonPrimitive(args[0]));
                    successRemove(event, args[0]);
                }

                bufferedWriter.write(jsonObj.toString());

            }

        } catch (IOException e) {
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

    private static void successAdd(MessageReceivedEvent event, String command) {
        event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + " Successfully **added** the command *(" + command + ")* to the command blacklist.").queue();
    }

    private static void successRemove(MessageReceivedEvent event, String command) {
        event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + " Successfully **removed** the command *(" + command + ")* to the command blacklist.").queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "togglecommand";
    }

    @Override
    public String getDesc() {
        return "Toggle the ability to use a command.";
    }

    @Override
    public String getUsage() {
        return "togglecommand <arg:string>";
    }

    @Override
    public CommandType getType() {
        return CommandType.PBC;
    }

    @Override
    public boolean isRestricted() {
        return true;
    }
}
