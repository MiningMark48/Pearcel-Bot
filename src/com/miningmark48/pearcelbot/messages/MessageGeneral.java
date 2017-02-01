package com.miningmark48.pearcelbot.messages;

import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class MessageGeneral {

    public static void init(MessageReceivedEvent event){

        String message = event.getMessage().getContent().toLowerCase();

        MessageBuilder builder = new MessageBuilder();

        String msg = JSONMessageGeneral.JSONReadGeneral(event.getMessage());

        if (msg != ""){
            builder.append(JSONMessageGeneral.JSONReadGeneral(event.getMessage()));
            event.getTextChannel().sendMessage(builder.build()).queue();
        }

    }

}
