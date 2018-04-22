package com.miningmark48.tidalbot.util;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.RestAction;
import net.dv8tion.jda.core.requests.restaction.MessageAction;

public class MessageUtil {

    public static MessageAction sendMessage(MessageReceivedEvent event, String message, boolean isPrivate) {
        return (isPrivate ? event.getPrivateChannel().sendMessage(message) : event.getTextChannel().sendMessage(message));
    }

    public static MessageAction sendMessage(MessageReceivedEvent event, MessageEmbed message, boolean isPrivate) {
        return (isPrivate ? event.getPrivateChannel().sendMessage(message) : event.getTextChannel().sendMessage(message));
    }

    public static MessageAction sendMessage(MessageReceivedEvent event, Message message, boolean isPrivate) {
        return (isPrivate ? event.getPrivateChannel().sendMessage(message) : event.getTextChannel().sendMessage(message));
    }

    public static MessageAction sendMessage(MessageReceivedEvent event, String message) {
        return sendMessage(event, message, false);
    }

    public static MessageAction sendMessage(MessageReceivedEvent event, MessageEmbed message) {
        return sendMessage(event, message, false);
    }

    public static MessageAction sendMessage(MessageReceivedEvent event, Message message) {
        return sendMessage(event, message, false);
    }

    public static RestAction<Void> sendTyping(MessageReceivedEvent event) {
        return sendTyping(event, false);
    }

    public static RestAction<Void> sendTyping(MessageReceivedEvent event, boolean isPrivate) {
        return (isPrivate ? event.getPrivateChannel().sendTyping() : event.getTextChannel().sendTyping());
    }

}
