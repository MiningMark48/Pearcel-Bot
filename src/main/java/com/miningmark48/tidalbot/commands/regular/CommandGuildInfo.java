package com.miningmark48.tidalbot.commands.regular;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandGuildInfo implements ICommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.decode("#7289da"));
        embedBuilder.setAuthor(event.getGuild().getName(), null, event.getGuild().getIconUrl());
        embedBuilder.setThumbnail(event.getGuild().getIconUrl());

        embedBuilder.addField("Name", event.getGuild().getName(), true);
        embedBuilder.addField("ID", event.getGuild().getId(), true);
        embedBuilder.addField("Owner", event.getGuild().getOwner().getEffectiveName(), true);
        embedBuilder.addField("Created On", event.getGuild().getTimeCreated().toString().substring(0, 10), true);
        embedBuilder.addField("Users Joined", Integer.valueOf(event.getGuild().getMembers().size()).toString(), true);
        embedBuilder.addField("Emote Amount", Integer.valueOf(event.getGuild().getEmotes().size()).toString(), true);
        embedBuilder.addField("Verified?", event.getGuild().checkVerification() ? "Yes" : "No", true);
        embedBuilder.addField("Region", event.getGuild().getRegion().getName(), true);
        embedBuilder.addField("AFK Timeout Time", event.getGuild().getAfkTimeout().getSeconds() != 3600 ? event.getGuild().getAfkTimeout().getSeconds() / 60 + " minutes" : "1 hour", true);
        embedBuilder.addField("AFK Channel", event.getGuild().getAfkChannel().getName(), true);

        event.getTextChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "guildinfo";
    }

    @Override
    public String getDesc() {
        return "Get information about a guild you're in on Discord.";
    }

    @Override
    public String getUsage() {
        return "guildinfo";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }

}
