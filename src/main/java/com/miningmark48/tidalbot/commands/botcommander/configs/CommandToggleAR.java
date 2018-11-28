package com.miningmark48.tidalbot.commands.botcommander.configs;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandToggleAR implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        ServerConfigHandler.toggleAR(event);
        event.getTextChannel().sendMessage("Auto response turned **" + (ServerConfigHandler.isAREnabled(event) ? "on" : "off") + "**.").queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "togglear";
    }

    @Override
    public String getDesc() {
        return "Toggles " + Reference.botName + "'s Auto Response.";
    }

    @Override
    public String getUsage() {
        return "togglear";
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
