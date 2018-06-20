package com.miningmark48.tidalbot.commands.botcommander.configs;

import com.google.gson.JsonArray;
import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class CommandListCommanders implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        JsonArray botCommanders = ServerConfigHandler.getBotCommanders(event);
        ArrayList<String> commanders = new ArrayList<>();

        if (!botCommanders.isJsonNull()) {
            botCommanders.forEach(q -> commanders.add(q.getAsString()));
            StringBuilder builder = new StringBuilder();
            commanders.forEach(q -> builder.append(event.getGuild().getMembers().stream().filter(q2 -> q2.getUser().getId().equalsIgnoreCase(q)).findFirst().get().getUser().getName() + "\n"));

            event.getTextChannel().sendMessage("Bot Commanders: \n```" + builder.toString() + "```").queue();
        } else {
            event.getTextChannel().sendMessage("Server has no bot commanders!").queue();
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

    @Override
    public boolean isRestricted() {
        return true;
    }

    @Override
    public String getName() {
        return "listcommanders";
    }

    @Override
    public String getDesc() {
        return "Shows all bot commanders.";
    }

    @Override
    public String getUsage() {
        return "listcommanders";
    }

    @Override
    public CommandType getType() {
        return CommandType.PBC;
    }

}
