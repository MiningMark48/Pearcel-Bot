package com.miningmark48.tidalbot.messages;

import com.miningmark48.tidalbot.reference.Reference;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

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
