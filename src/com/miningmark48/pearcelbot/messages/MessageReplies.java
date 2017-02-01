package com.miningmark48.pearcelbot.messages;

import com.miningmark48.pearcelbot.util.ChatBuilder;
import com.miningmark48.pearcelbot.util.Logger;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Random;

public class MessageReplies {

    public static Random rand = new Random();

    public static void init(MessageReceivedEvent event){
        MessageBuilder builder = new MessageBuilder();

        if(event.getAuthor().getName() != event.getJDA().getSelfUser().getName()) {

            if (JSONMessageReplies.JSONReadRepliesWithin(event.getMessage())) {
                builder.append(JSONMessageReplies.JSONReadReplies(JSONMessageReplies.JSONReadRepliesType(event.getMessage())));
                event.getTextChannel().sendMessage(builder.build()).queue();
            }
        }

    }

}
