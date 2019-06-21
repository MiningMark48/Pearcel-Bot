package com.miningmark48.tidalbot.base;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface ICommandPrivate {

    void actionPrivate(String[] args, MessageReceivedEvent event);

}
