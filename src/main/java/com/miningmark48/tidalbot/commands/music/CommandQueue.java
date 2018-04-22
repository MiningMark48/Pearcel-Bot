package com.miningmark48.tidalbot.commands.music;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.util.features.music.handler.AudioHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandQueue implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
//        AudioHandler.nowPlaying(event.getTextChannel());
        AudioHandler.getQueue(event.getTextChannel());
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public String getDesc() {
        return "Shows current queue.";
    }

    @Override
    public String getUsage() {
        return "queue";
    }

    @Override
    public CommandType getType() {
        return CommandType.MUSIC;
    }
}
