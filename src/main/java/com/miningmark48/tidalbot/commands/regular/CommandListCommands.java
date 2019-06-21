package com.miningmark48.tidalbot.commands.regular;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.JSON.JSONParseFile;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class CommandListCommands implements ICommand {

    public static String fileName = "custom_commands.json";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        File file = new File(fileName);

        if(file.exists()) {

            JsonObject jsonObj = JSONParseFile.JSONParse(fileName);
            JsonObject jsonObjServ = jsonObj.getAsJsonObject(event.getGuild().getId());
            Set<Map.Entry<String, JsonElement>> entries = jsonObjServ.entrySet();
            MessageBuilder builder = new MessageBuilder();
            builder.append("\n**Commands for " + jsonObjServ.get("_comment_serverName_").getAsString() + ": ** ```");

            for (Map.Entry<String, JsonElement> entry: entries){
                if(!entry.getKey().equalsIgnoreCase("_comment_serverName_")) {
                    builder.append("\n" + Reference.botCommandCustomKey + entry.getKey() + ": " + entry.getValue().getAsString() + "\n");
                }
            }

            builder.append("\n```");

            event.getTextChannel().sendMessage(event.getAuthor().getName() + ", Sending you a list of custom commands for *" + jsonObjServ.get("_comment_serverName_").getAsString() + "* now.").queue();
            event.getAuthor().openPrivateChannel().queue(chan -> chan.sendMessage(builder.build()).queue());

        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "listcommands";
    }

    @Override
    public String getDesc() {
        return "List all custom commands for the server.";
    }

    @Override
    public String getUsage() {
        return "listcommands";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }
}
