package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.util.FormatUtil;
import com.miningmark48.pearcelbot.util.MessageUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;

public class CommandEmojiList implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        ArrayList<Emote> normalEmotes = new ArrayList<>();
        ArrayList<Emote> animatedEmotes = new ArrayList<>();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(FormatUtil.formatText(FormatUtil.FormatType.UNDERLINE, event.getGuild().getName() + "'s Emojis"));
        builder.setColor(Color.PINK);
        builder.setFooter(event.getGuild().getName() + "'s Emojis", event.getGuild().getIconUrl());

        event.getGuild().getEmotes().forEach(q -> {
            if (q.isAnimated()) {
                animatedEmotes.add(q);
            } else {
                normalEmotes.add(q);
            }
        });

        normalEmotes.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        animatedEmotes.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));

        builder.addField(FormatUtil.formatText(FormatUtil.FormatType.UNDERLINE, "Normal"), "", false);
        normalEmotes.forEach(q -> builder.addField(":" + q.getName() + ":", q.getAsMention(), true));

        builder.addBlankField(false);

        builder.addField(FormatUtil.formatText(FormatUtil.FormatType.UNDERLINE, "Animated"), "", false);
        animatedEmotes.forEach(q -> builder.addField(":" + q.getName() + ":", q.getAsMention(), true));

        MessageUtil.sendMessage(event, builder.build()).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "emojilist";
    }

    @Override
    public String getDesc() {
        return "Lists all emojis for the guild.";
    }

    @Override
    public String getUsage() {
        return "emojilist";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
