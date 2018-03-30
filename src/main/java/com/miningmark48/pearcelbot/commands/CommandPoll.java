package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandPoll implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length != 0){
            event.getMessage().addReaction("\u2705").queue();
            event.getMessage().addReaction("\u274C").queue();

            event.getTextChannel().sendMessage(String.format("**Click one of the reactions above to vote on the question:** \n     _%s_", event.getMessage().getContentRaw().replace(Reference.botCommandKey + "poll ", ""))).queue();
        }
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
        return "Ask a 2 option poll.";
    }

    @Override
    public String getUsage() {
        return "poll <arg:string>";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
