package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class CommandListBots implements ICommand {

    public static final String desc = "Lists all the bots in the current guild.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "listbots";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("**Bots Connected to Guild, **" + event.getGuild().getName() + ": \n```");

        List<Member> members = event.getGuild().getMembers();

        members.stream().filter(member -> member.getUser().isBot()).forEach(member -> {
            builder.append(member.getEffectiveName() + "\n");
        });

        builder.append("```");

        event.getTextChannel().sendMessage(builder.build()).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
