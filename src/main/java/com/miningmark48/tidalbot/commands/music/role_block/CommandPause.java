package com.miningmark48.pearcelbot.commands.music.role_block;

import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.features.music.handler.AudioHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandPause implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (event.getMember() == null) { //Webhook support
            AudioHandler.pause(event.getTextChannel());
            return;
        }
        if (!event.getMember().getRoles().toString().contains(Reference.botNoMusicRole)) {
            AudioHandler.pause(event.getTextChannel());
        } else {
            event.getTextChannel().sendMessage("Sorry " + event.getAuthor().getAsMention() + ", but you do not have permission to use that command. If you think this is a mistake, ask an admin why you have the `" + Reference.botNoMusicRole + "` role.").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "pause";
    }

    @Override
    public String getDesc() {
        return "Pauses the current track.";
    }

    @Override
    public String getUsage() {
        return "pause";
    }

    @Override
    public CommandType getType() {
        return CommandType.MUSIC;
    }
}
