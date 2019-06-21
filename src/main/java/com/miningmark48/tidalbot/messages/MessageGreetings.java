package com.miningmark48.tidalbot.messages;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MessageGreetings{

    public static void init(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase();
        MessageBuilder builder = new MessageBuilder();

        if (message.contains(event.getJDA().getSelfUser().getName().toLowerCase()) && event.getAuthor().getName() != event.getJDA().getSelfUser().getName()) {

            if (JSONMessageGreetings.JSONReadGreetWithin(event.getMessage())){
                builder.append(JSONMessageGreetings.JSONReadGreet() + " " + event.getAuthor().getName() + ", ");
                builder.append(JSONMessageGreetings.JSONReadGreetFollowup());

                event.getTextChannel().sendMessage(builder.build()).queue();
            }

        }
    }
}
