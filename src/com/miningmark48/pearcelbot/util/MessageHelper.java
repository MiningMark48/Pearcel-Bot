package com.miningmark48.pearcelbot.util;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class MessageHelper {

    public static void sendMessage(MessageReceivedEvent event, String message, boolean isPrivate) {
        if (!isPrivate) {
            event.getTextChannel().sendMessage(message).queue();
        } else {
            event.getPrivateChannel().sendMessage(message).queue();
        }
    }

    public static void sendMessage(MessageReceivedEvent event, MessageEmbed message, boolean isPrivate) {
        if (!isPrivate) {
            event.getTextChannel().sendMessage(message).queue();
        } else {
            event.getPrivateChannel().sendMessage(message).queue();
        }
    }

    public static void sendMessage(MessageReceivedEvent event, Message message, boolean isPrivate) {
        if (!isPrivate) {
            event.getTextChannel().sendMessage(message).queue();
        } else {
            event.getPrivateChannel().sendMessage(message).queue();
        }
    }

}
