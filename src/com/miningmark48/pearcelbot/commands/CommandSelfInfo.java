package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.util.MessageHelper;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandSelfInfo implements ICommand, ICommandPrivate, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        doCmd(event, false);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public void actionPrivate(String[] args, MessageReceivedEvent event) {
        doCmd(event, true);
    }

    private static void doCmd(MessageReceivedEvent event, boolean isPrivate) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.decode("#7289da"));
        embedBuilder.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl());
        embedBuilder.setThumbnail(event.getAuthor().getAvatarUrl());

        embedBuilder.addField("Name", event.getAuthor().getName(), true);
        embedBuilder.addField("ID", event.getAuthor().getId(), true);
        embedBuilder.addField("Created On", event.getAuthor().getCreationTime().toString().substring(0, 10), true);
        embedBuilder.addField("Is Bot?", event.getAuthor().isBot() ? "Yes" : "No", true);

        MessageHelper.sendMessage(event, embedBuilder.build(), isPrivate);
    }

    @Override
    public String getName() {
        return "selfinfo";
    }

    @Override
    public String getDesc() {
        return "Get information about yourself on Discord.";
    }

    @Override
    public String getUsage() {
        return "selfinfo";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
