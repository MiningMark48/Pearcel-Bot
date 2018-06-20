package com.miningmark48.tidalbot.commands.botcommander.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.util.features.serverconfig.JsonConfigHandler;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandViewConfig implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        StringBuilder builder = new StringBuilder();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonFinal = gson.toJson(JsonConfigHandler.getJson(event));
        builder.append("**Current Server Config:**\n```Json\n");
        builder.append(jsonFinal);
        builder.append("\n```");
        event.getMessage().delete().queue();
        event.getTextChannel().sendMessage(builder.toString()).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "viewconfig";
    }

    @Override
    public String getDesc() {
        return "Will show the current configuration file of the server.";
    }

    @Override
    public String getUsage() {
        return "viewconfig";
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
