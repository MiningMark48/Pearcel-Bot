package com.miningmark48.tidalbot.commands.botcommander.configs;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandToggleNPMessages implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        ServerConfigHandler.toggleNPMessages(event);
        event.getTextChannel().sendMessage("Now playing messages turned **" + (ServerConfigHandler.areNPMessagesDisabled(event.getGuild().getId()) ? "off" : "on") + "**.").queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "togglenpmessages";
    }

    @Override
    public String getDesc() {
        return "Toggles " + Reference.botName + "'s now playing messages when a track plays.";
    }

    @Override
    public String getUsage() {
        return "togglenpmessages";
    }

    @Override
    public CommandType getType() {
        return CommandType.BC;
    }

    @Override
    public boolean isRestricted() {
        return true;
    }
}
