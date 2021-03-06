package com.miningmark48.tidalbot.commands.botregular;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.reference.Reference;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandBotStats implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.decode("#A2F000"));
        embedBuilder.setAuthor(event.getJDA().getSelfUser().getName() + " Statistics", "http://www.miningmark48.xyz/tidalbot", event.getJDA().getSelfUser().getAvatarUrl());
        embedBuilder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());

        embedBuilder.addField("Connected Servers", String.valueOf(event.getJDA().getGuilds().size()), true);
        embedBuilder.addField("Text Channels", String.valueOf(event.getJDA().getTextChannels().size()), true);
        embedBuilder.addField("Voice Channels", String.valueOf(event.getJDA().getVoiceChannels().size()), true);
        embedBuilder.addField("Private Channels", String.valueOf(event.getJDA().getPrivateChannels().size()), true);
        embedBuilder.addField("Visible Users", String.valueOf(event.getJDA().getUsers().size()), true);

        embedBuilder.setFooter("Tidal Bot: A Discord bot by MiningMark48.", event.getAuthor().getAvatarUrl());
        event.getTextChannel().sendMessage(embedBuilder.build()).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

    @Override
    public String getName() {
        return "botstats";
    }

    @Override
    public String getDesc() {
        return "Returns bot stats.";
    }

    @Override
    public String getUsage() {
        return "botstats";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
