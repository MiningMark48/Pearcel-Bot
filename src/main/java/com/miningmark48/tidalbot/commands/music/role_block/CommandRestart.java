package com.miningmark48.tidalbot.commands.music.role_block;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.features.music.handler.AudioHandler;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandRestart implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (!ServerConfigHandler.isMusicBlacklisted(event, event.getAuthor().getId())) {
            AudioHandler.restart(event.getTextChannel());
        } else {
            event.getTextChannel().sendMessage("Sorry " + event.getAuthor().getAsMention() + ", but you do not have permission to use that command. If you think this is a mistake, ask an admin why you have been banned from using music commands.").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "restart";
    }

    @Override
    public String getDesc() {
        return "Restarts a currently playing track.";
    }

    @Override
    public String getUsage() {
        return "restart";
    }

    @Override
    public CommandType getType() {
        return CommandType.MUSIC;
    }
}
