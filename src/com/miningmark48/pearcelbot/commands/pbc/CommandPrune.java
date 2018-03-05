package com.miningmark48.pearcelbot.commands.pbc;

import com.miningmark48.pearcelbot.commands.ICommand;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandPrune implements ICommand {

    public static final String desc = "Prune a chunk of messages.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "prune <arg:int>";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (event.getMember().getRoles().toString().contains(Reference.botCommanderRole) || event.getGuild().getOwner() == event.getMember() || event.getAuthor().getId().equals(Reference.botOwner)) {
            if (args.length == 0) {
                event.getTextChannel().sendMessage("Missing args!").queue();
            } else {
                try {
                    int pruneAmt = Integer.valueOf(args[0]);
                    TextChannel channel = event.getTextChannel();
                    channel.getHistory().retrievePast(pruneAmt + 1).queue(
                            messages -> channel.deleteMessages(messages).queue()
                    );
                } catch (NumberFormatException e) {
                    event.getTextChannel().sendMessage("Error! That's not a number.").queue();
                }
            }
        } else {
            event.getTextChannel().sendMessage(event.getAuthor().getName() + ", You do not have permission to run that command.").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

}
