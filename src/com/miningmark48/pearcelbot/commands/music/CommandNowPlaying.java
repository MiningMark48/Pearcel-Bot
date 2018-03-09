package com.miningmark48.pearcelbot.commands.music;

import com.miningmark48.pearcelbot.commands.CommandType;
import com.miningmark48.pearcelbot.commands.ICommand;
import com.miningmark48.pearcelbot.commands.ICommandInfo;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.music.handler.AudioHandler;
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
