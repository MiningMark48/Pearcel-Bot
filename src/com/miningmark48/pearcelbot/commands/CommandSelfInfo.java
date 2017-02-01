package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.Command;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandSelfInfo implements Command{

    public static final String desc = "Get information about yourself on Discord.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "selfinfo";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.decode("#7289da"));
        embedBuilder.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl());
        embedBuilder.setThumbnail(event.getAuthor().getAvatarUrl());

        embedBuilder.addField("Name", event.getAuthor().getName(), true);
        embedBuilder.addField("ID", event.getAuthor().getId(), true);
        embedBuilder.addField("Created On", event.getAuthor().getCreationTime().toString().substring(0, 10), true);
        embedBuilder.addField("Is Bot?", event.getAuthor().isBot() ? "Yes" : "No", true);

        event.getTextChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
