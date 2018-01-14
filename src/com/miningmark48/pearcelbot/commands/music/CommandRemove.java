package com.miningmark48.pearcelbot.commands.music;

import com.miningmark48.pearcelbot.commands.ICommand;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.music.handler.AudioHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandRemove implements ICommand {

    public static final String desc = "Remove a track in the queue by keyword.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "remove <keyword - in queue>";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        AudioHandler.remove(event.getTextChannel(), args);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
