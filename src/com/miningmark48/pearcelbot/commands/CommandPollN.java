package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.Command;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.RestAction;

import java.time.OffsetDateTime;
import java.util.List;

public class CommandPollN implements Command{

    public static final String desc = "Ask a 2 option poll with a neither option.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "polln <arg>";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length != 0){
            event.getMessage().addReaction("\u2B1B").queue();
            event.getMessage().addReaction("\u2B1C").queue();
            event.getMessage().addReaction("\u274C").queue();

            event.getTextChannel().sendMessage(String.format("**Click one of the reactions above to vote on the question:** \n     _%s_", event.getMessage().getContent().replace(Reference.botCommandKey + "polln ", ""))).queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
