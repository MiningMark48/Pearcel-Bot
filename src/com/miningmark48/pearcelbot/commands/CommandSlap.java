package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.ICommand;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandSlap implements ICommand {

    public static final String desc = "Slap someone.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "slap <arg>";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("*slaps " + args[0] + " with a fish.* \uD83D\uDC1F").queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }
}
