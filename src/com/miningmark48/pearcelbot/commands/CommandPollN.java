package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandPollN implements ICommand {

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
