package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandBotStats implements ICommand{

    public static final String desc = "Returns bot stats.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "botstats";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.decode("#A2F000"));
        embedBuilder.setAuthor(event.getJDA().getSelfUser().getName() + " Statistics", "http://www.miningmark48.xyz/pearcelbot", event.getJDA().getSelfUser().getAvatarUrl());
        embedBuilder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());

        embedBuilder.addField("Connected Servers", String.valueOf(event.getJDA().getGuilds().size()), true);
        embedBuilder.addField("Text Channels", String.valueOf(event.getJDA().getTextChannels().size()), true);
        embedBuilder.addField("Voice Channels", String.valueOf(event.getJDA().getVoiceChannels().size()), true);
        embedBuilder.addField("Private Channels", String.valueOf(event.getJDA().getPrivateChannels().size()), true);
        embedBuilder.addField("Visible Users", String.valueOf(event.getJDA().getUsers().size()), true);

        embedBuilder.setFooter("Pearcel Bot: A Discord bot by MiningMark48.", event.getAuthor().getAvatarUrl());
        event.getTextChannel().sendMessage(embedBuilder.build()).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

}
