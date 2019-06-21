package com.miningmark48.tidalbot.commands.regular;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.base.ICommandPrivate;
import com.miningmark48.tidalbot.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandSelfInfo implements ICommand, ICommandPrivate {

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
        embedBuilder.addField("Created On", event.getAuthor().getTimeCreated().toString().substring(0, 10), true);
        embedBuilder.addField("Is Bot?", event.getAuthor().isBot() ? "Yes" : "No", true);

        MessageUtil.sendMessage(event, embedBuilder.build(), isPrivate).queue();
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
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }
}
