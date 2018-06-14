package com.miningmark48.tidalbot.commands;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandARBlacklist implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        ServerConfigHandler.toggleARBlacklistUser(event, event.getAuthor().getId());
        event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + ", **" + (ServerConfigHandler.isUserARBlacklisted(event, event.getAuthor().getId()) ? "added" : "removed") + "** you " + (ServerConfigHandler.isUserARBlacklisted(event, event.getAuthor().getId()) ? "to" : "from") + " the Auto Response blacklist.").queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "arblacklist";
    }

    @Override
    public String getDesc() {
        return "Opt-in to be blacklisted from " + Reference.botName + "'s AutoResponse system";
    }

    @Override
    public String getUsage() {
        return "arblacklist";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
