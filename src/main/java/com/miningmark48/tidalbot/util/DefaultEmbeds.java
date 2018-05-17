package com.miningmark48.tidalbot.util;

import com.miningmark48.tidalbot.reference.Reference;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;

import java.awt.*;

public class DefaultEmbeds {

    private static EmbedBuilder message = new EmbedBuilder();

    public static void sendMessage(TextChannel channel, String message, EmbedType type) {
        sendMessage(channel, message, "", type);
    }

    public static void sendMessage(TextChannel channel, String message, String description, EmbedType type) {
        EmbedBuilder builder = getEmbed();
        builder.setTitle(message);
        builder.setDescription(description);

        switch (type) {
            default:
            case NONE:
                break;
            case DANGER:
                builder.setThumbnail("http://tw.miningmark48.xyz/img/icons/danger.png");
                break;
            case INFO:
                builder.setThumbnail("http://tw.miningmark48.xyz/img/icons/info.png");
                break;
            case MUSIC:
                builder.setThumbnail("http://tw.miningmark48.xyz/img/icons/music.png");
                break;
        }

        channel.sendMessage(builder.build()).queue();
    }

    private static EmbedBuilder getEmbed() {
        message.clear();
        message.setColor(Color.ORANGE);
        message.setFooter("\uD83C\uDFB5 " + Reference.botName + " \uD83C\uDFB5", null);
        return message;
    }

    public enum EmbedType {
        NONE,
        DANGER,
        INFO,
        MUSIC
    }

}
