package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.ICommand;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandSourceCode implements ICommand{

    public static final String desc = "Get the link for Pearcel Bot source code.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "sourcecode";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("**Source code for Pearcel Bot:** _https://github.com/MiningMark48/Pearcel-Bot_").queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
