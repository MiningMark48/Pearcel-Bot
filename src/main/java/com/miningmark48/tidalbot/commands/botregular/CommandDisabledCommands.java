package com.miningmark48.tidalbot.commands.botregular;

import com.google.gson.JsonArray;
import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.util.FormatUtil;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandDisabledCommands implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        JsonArray commands = ServerConfigHandler.getBlacklistedCommands(event);

        StringBuilder builder = new StringBuilder();
        builder.append(FormatUtil.formatText(FormatUtil.FormatType.BOLD, "Currently Disabled Commands: ")).append(" \n");

        StringBuilder commandsBuilder = new StringBuilder();

        commands.forEach(q -> commandsBuilder.append("`").append(q.getAsString()).append("` "));

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
