package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.Command;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.sql.Ref;

public class CommandServerInvite implements Command{

    public static final String desc = "Get a link so you can invite " + Reference.botName + " to your server.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "serverinvite";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#A2F000"));
        builder.setAuthor(Reference.botName, "http://www.miningmark48.xyz/pearcelbot", event.getJDA().getSelfUser().getAvatarUrl());
        builder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription(String.format("Click the link to add %s to your server. \n\n%s", Reference.botName, Reference.joinLink));

        event.getTextChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
