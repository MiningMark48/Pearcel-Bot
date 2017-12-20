package com.miningmark48.pearcelbot;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface ICommand {

    String desc = null;
    String usage = null;
    String info = desc + " " + usage;

    boolean called(String[] args, MessageReceivedEvent event);
    void action(String[] args, MessageReceivedEvent event);
    void executed(boolean success, MessageReceivedEvent event);

}
