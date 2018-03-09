package com.miningmark48.pearcelbot.commands.music.role_block;

import com.miningmark48.pearcelbot.commands.CommandType;
import com.miningmark48.pearcelbot.commands.ICommand;
import com.miningmark48.pearcelbot.commands.ICommandInfo;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.music.handler.AudioHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandSkip implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (event.getMember() == null) { //Webhook support
            AudioHandler.skipTrack(event.getTextChannel());
            return;
        }
        if (!event.getMember().getRoles().toString().contains(Reference.botNoMusicRole)) {
            AudioHandler.skipTrack(event.getTextChannel());
        } else {
            event.getTextChannel().sendMessage("Sorry " + event.getAuthor().getAsMention() + ", but you do not have permission to use that command. If you think this is a mistake, ask an admin why you have the `" + Reference.botNoMusicRole + "` role.").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getDesc() {
        return "Skip current track.";
    }

    @Override
    public String getUsage() {
        return "skip";
    }

    @Override
    public CommandType getType() {
        return CommandType.MUSIC;
    }
}
