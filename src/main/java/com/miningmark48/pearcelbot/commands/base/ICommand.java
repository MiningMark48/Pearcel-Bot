package com.miningmark48.pearcelbot.commands.base;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;

public interface ICommand {

    boolean called(String[] args, MessageReceivedEvent event);
    void action(String[] args, MessageReceivedEvent event);
    void executed(boolean success, MessageReceivedEvent event);
    default boolean isRestricted() { return false; }

}
