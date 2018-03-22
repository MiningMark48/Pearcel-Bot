package com.miningmark48.pearcelbot.util;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.restaction.MessageAction;

public class MessageUtil {

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

    public static MessageAction sendMessageNoQueue(MessageReceivedEvent event, String message, boolean isPrivate) {
        if (!isPrivate) {
            return event.getTextChannel().sendMessage(message);
        } else {
            return event.getPrivateChannel().sendMessage(message);
        }
    }

    public static MessageAction sendMessageNoQueue(MessageReceivedEvent event, MessageEmbed message, boolean isPrivate) {
        if (!isPrivate) {
            return event.getTextChannel().sendMessage(message);
        } else {
            return event.getPrivateChannel().sendMessage(message);
        }
    }

    public static MessageAction sendMessageNoQueue(MessageReceivedEvent event, Message message, boolean isPrivate) {
        if (!isPrivate) {
            return event.getTextChannel().sendMessage(message);
        } else {
            return event.getPrivateChannel().sendMessage(message);
        }
    }

}
