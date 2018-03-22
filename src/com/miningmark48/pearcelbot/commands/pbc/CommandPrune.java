package com.miningmark48.pearcelbot.commands.pbc;

import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class CommandPrune implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length == 0) {
            event.getTextChannel().sendMessage("Missing args!").queue();
        } else {
            try {
                int pruneAmt = Integer.valueOf(args[0]);
                TextChannel channel = event.getTextChannel();
                channel.getHistory().retrievePast(pruneAmt + 1).queue(
                        messages -> channel.deleteMessages(messages).queue()
                );
                channel.sendMessage("Deleted " + pruneAmt + " messages. ").queue(msg -> {
                    msg.delete().queueAfter(2500, TimeUnit.MILLISECONDS);
                });
            } catch (NumberFormatException e) {
                event.getTextChannel().sendMessage("Error! That's not a number.").queue();
            }
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

    @Override
    public boolean isRestricted() {
        return true;
    }

    @Override
    public String getName() {
        return "prune";
    }

    @Override
    public String getDesc() {
        return "Prune an amount of messages.";
    }

    @Override
    public String getUsage() {
        return "prune <arg:int>";
    }

    @Override
    public CommandType getType() {
        return CommandType.PBC;
    }
}
