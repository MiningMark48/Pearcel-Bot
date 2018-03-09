package com.miningmark48.pearcelbot.commands.music.role_block;

import com.miningmark48.pearcelbot.commands.CommandType;
import com.miningmark48.pearcelbot.commands.ICommand;
import com.miningmark48.pearcelbot.commands.ICommandInfo;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.music.handler.AudioHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandRepeatOnce implements ICommand, ICommandInfo {

    public static final String desc = "Repeat currently playing track once. Run again to disable.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "repeatonce";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (!event.getMember().getRoles().toString().contains(Reference.botNoMusicRole)) {
            AudioHandler.repeat(event.getTextChannel(), true);
        } else {
            event.getTextChannel().sendMessage("Sorry " + event.getAuthor().getAsMention() + ", but you do not have permission to use that command. If you think this is a mistake, ask an admin why you have the `" + Reference.botNoMusicRole + "` role.").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "repeatonce";
    }

    @Override
    public String getDesc() {
        return "Repeat currently playing track once. Run again to disable.";
    }

    @Override
    public String getUsage() {
        return "repeatonce";
    }

    @Override
    public CommandType getType() {
        return CommandType.MUSIC;
    }
}
