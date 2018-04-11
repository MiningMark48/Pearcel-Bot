package com.miningmark48.pearcelbot.commands.music.role_block;

import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.features.music.handler.AudioHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandRepeat implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (!event.getMember().getRoles().toString().contains(Reference.botNoMusicRole)) {
            AudioHandler.repeat(event.getTextChannel(), false);
        } else {
            event.getTextChannel().sendMessage("Sorry " + event.getAuthor().getAsMention() + ", but you do not have permission to use that command. If you think this is a mistake, ask an admin why you have the `" + Reference.botNoMusicRole + "` role.").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "repeat";
    }

    @Override
    public String getDesc() {
        return "Repeats a currently playing track. Run again to disable.";
    }

    @Override
    public String getUsage() {
        return "repeat";
    }

    @Override
    public CommandType getType() {
        return CommandType.MUSIC;
    }
}