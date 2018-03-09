package com.miningmark48.pearcelbot.commands.music;

import com.miningmark48.pearcelbot.commands.CommandType;
import com.miningmark48.pearcelbot.commands.ICommand;
import com.miningmark48.pearcelbot.commands.ICommandInfo;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.music.LyricLookup;
import com.miningmark48.pearcelbot.util.music.handler.AudioHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandLyrics implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        LyricLookup.postLyrics(event.getTextChannel(), args);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "lyrics";
    }

    @Override
    public String getDesc() {
        return "Search for lyrics for a song by artist and title.";
    }

    @Override
    public String getUsage() {
        return "lyrics <artist(string) :: title(string)>";
    }

    @Override
    public CommandType getType() {
        return CommandType.MUSIC;
    }
}
