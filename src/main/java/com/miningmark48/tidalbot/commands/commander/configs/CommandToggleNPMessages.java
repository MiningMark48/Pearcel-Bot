package com.miningmark48.tidalbot.commands.commander.configs;

import com.miningmark48.tidalbot.base.CommandType;
import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandToggleNPMessages implements ICommand {

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
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.MANAGER;
    }
}