package com.miningmark48.tidalbot.commands.base;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface ICommandPrivate {

    void actionPrivate(String[] args, MessageReceivedEvent event);

}
