package com.miningmark48.tidalbot.commands.music;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.util.features.music.handler.AudioHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandNowPlaying implements ICommand {

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
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }

    @Override
    public boolean isMusic() {
        return true;
    }
}
