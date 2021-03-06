package com.miningmark48.tidalbot.commands.botregular;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class CommandListBots implements ICommand, ICommandInfo {

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

    @Override
    public String getName() {
        return "listbots";
    }

    @Override
    public String getDesc() {
        return "Lists all the bots in the current guild.";
    }

    @Override
    public String getUsage() {
        return "listbots";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
