package com.miningmark48.tidalbot.commands.regular;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.reference.Reference;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandServerInvite implements ICommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#A2F000"));
        builder.setAuthor(Reference.botName, "http://www.miningmark48.xyz/tidalbot", event.getJDA().getSelfUser().getAvatarUrl());
        builder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription(String.format("Click the link to add %s to your server. \n\n%s", Reference.botName, Reference.joinLink));

        event.getTextChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "serverinvite";
    }

    @Override
    public String getDesc() {
        return "Get a link so you can invite " + Reference.botName + " to your server.";
    }

    @Override
    public String getUsage() {
        return "serverinvite";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }
}
