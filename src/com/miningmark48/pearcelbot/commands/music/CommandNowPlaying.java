package com.miningmark48.pearcelbot.commands.music;

import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.util.features.music.handler.AudioHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandNowPlaying implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        AudioHandler.nowPlaying(event.getTextChannel());
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "nowplaying";
    }

    @Override
    public String getDesc() {
        return "Tells what track is currently playing.";
    }

    @Override
    public String getUsage() {
        return "nowplaying";
    }

    @Override
    public CommandType getType() {
        return CommandType.MUSIC;
    }
}
