package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.Command;
import com.miningmark48.pearcelbot.messages.JSONMessageGreetings;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.Logger;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.jws.soap.SOAPBinding;

public class CommandPing implements Command{

    public static final String desc = "Simple Ping, Pong.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "ping";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("Pong!").queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }
}
