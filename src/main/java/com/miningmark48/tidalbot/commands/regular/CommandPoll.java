package com.miningmark48.tidalbot.commands.regular;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.util.DefaultEmbeds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandPoll implements ICommand {

    //Emoji: A, B, C, D, E
    private String[] mcEmoji = {"\uD83C\uDDE6", "\uD83C\uDDE7", "\uD83C\uDDE8", "\uD83C\uDDE9", "\uD83C\uDDEA"};

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getMessage().delete().queue();
        if (args.length == 0) {
            DefaultEmbeds.sendMessage(event.getTextChannel(), "Missing Arguments!", "Usage: " + getUsage() + "\n\n" + getExample(), DefaultEmbeds.EmbedType.INFO);
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(args).forEach(q -> stringBuilder.append(q).append(" "));
        String original = stringBuilder.toString();
        ArrayList<String> split = new ArrayList<>(Arrays.asList(original.split("::")));

        boolean isMC = (split.size() > 1);
        String question = split.remove(0);

        //Remove duplicate answers
        Set<String> set = new LinkedHashSet<>(split);
        split.clear();
        split.addAll(set);

        EmbedBuilder embedBuilder = getEmbed(event);
        embedBuilder.setTitle(question);

        StringBuilder answers = new StringBuilder();
        AtomicInteger mcSize = new AtomicInteger();
        split.forEach(ans -> {
            if (split.indexOf(ans) < 5) {
                answers.append(mcEmoji[split.indexOf(ans)]);
                answers.append(ans);
                answers.append("\n");
                mcSize.getAndIncrement();
            }
        });

        embedBuilder.setDescription(answers);

        event.getTextChannel().sendMessage(embedBuilder.build()).queue(msg -> {
            if (isMC) {
                for (int i = 0; i <= mcSize.get() - 1; i++) {
                    msg.addReaction(mcEmoji[i]).queue();
                }
            } else {
                msg.addReaction("\uD83D\uDC4D").queue(); //Thumbs up
                msg.addReaction("\uD83D\uDC4E").queue(); //Thumbs down
            }
        });

    }

    private static EmbedBuilder getEmbed(MessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.ORANGE);
        embedBuilder.setFooter("Created by " + event.getAuthor().getName(), null);

        return embedBuilder;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "poll";
    }

    @Override
    public String getDesc() {
        return "Ask a poll, can be yes/no or multiple choice to 5";
    }

    @Override
    public String getUsage() {
        return "poll <question:string> [:: <answer:string>...to 5]";
    }

    private String getExample() {
        return "`poll Do you like Discord?` (Yes/No) \n `poll Which is better? :: Skype :: Teamspeak :: Discord` (Multiple Choice)";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }
}
