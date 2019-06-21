package com.miningmark48.tidalbot.commands.commander.configs;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandToggleAR implements ICommand {

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
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.MANAGER;
    }
}
