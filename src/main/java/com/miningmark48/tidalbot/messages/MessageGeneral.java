package com.miningmark48.tidalbot.messages;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MessageGeneral {

    public static void init(MessageReceivedEvent event){

        String message = event.getMessage().getContentRaw().toLowerCase();

        MessageBuilder builder = new MessageBuilder();

        String msg = JSONMessageGeneral.JSONReadGeneral(event.getMessage());

        if (msg != ""){
            builder.append(JSONMessageGeneral.JSONReadGeneral(event.getMessage()));
            event.getTextChannel().sendMessage(builder.build()).queue();
        }

    }

}
