package com.miningmark48.tidalbot.util;

import com.miningmark48.tidalbot.reference.Reference;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

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
                builder.setColor(Color.RED);
                break;
            case INFO:
                builder.setThumbnail("http://tw.miningmark48.xyz/img/icons/info.png");
                builder.setColor(Color.YELLOW);
                break;
            case MUSIC:
                builder.setThumbnail("http://tw.miningmark48.xyz/img/icons/music.png");
                builder.setColor(Color.BLUE);
                break;
            case SUCCESS:
                builder.setThumbnail("http://tw.miningmark48.xyz/img/icons/success.png");
                builder.setColor(Color.GREEN);
                break;
        }

        channel.sendMessage(builder.build()).queue();
    }

    private static EmbedBuilder getEmbed() {
        message.clear();
        message.setColor(Color.ORANGE);
        message.setFooter("\uD83E\uDD16 " + Reference.botName + " \uD83E\uDD16", null);
        return message;
    }

    public enum EmbedType {
        NONE,
        DANGER,
        INFO,
        MUSIC,
        SUCCESS
    }

}
