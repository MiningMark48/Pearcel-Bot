package com.miningmark48.pearcelbot.util;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;

public class ChatBuilder {

    public static MessageBuilder builder = new MessageBuilder();

    public static void sendMessage( MessageChannel channel){
        channel.sendMessage(builder.build());
        builder = new MessageBuilder();
    }

}
