package com.miningmark48.tidalbot.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.commands.botcommander.CommandToggleCommand;
import com.miningmark48.tidalbot.util.FormatUtil;
import com.miningmark48.tidalbot.util.JSON.JSONParseFile;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandDisabledCommands implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        JsonObject jsonObj = JSONParseFile.JSONParse(CommandToggleCommand.fileName);

        StringBuilder builder = new StringBuilder();
        builder.append(FormatUtil.formatText(FormatUtil.FormatType.BOLD, "Currently Disabled Commands: ")).append(" \n");

        StringBuilder commandsBuilder = new StringBuilder();


        if (jsonObj != null) {
            if (jsonObj.getAsJsonObject("servers") != null) {
                JsonObject servers = jsonObj.getAsJsonObject("servers");
                if (servers.getAsJsonObject(event.getGuild().getId()) != null){
                    JsonObject guild = servers.getAsJsonObject(event.getGuild().getId());
                    if (guild.getAsJsonArray("commands") != null) {
                        JsonArray commands = guild.getAsJsonArray("commands");
                        commands.forEach(q -> commandsBuilder.append("`").append(q.getAsString()).append("` "));
                    }
                }
            }
        }

        if (commandsBuilder.toString().isEmpty()) {
            event.getTextChannel().sendMessage("No commands currently disabled.").queue();
            return;
        }

        builder.append(commandsBuilder);

        event.getTextChannel().sendMessage(builder).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "disabledcommands";
    }

    @Override
    public String getDesc() {
        return "View all currently disabled commands in the server.";
    }

    @Override
    public String getUsage() {
        return "disabledcommands";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
