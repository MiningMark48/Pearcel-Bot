package com.miningmark48.tidalbot.commands.commander;

import com.miningmark48.tidalbot.base.CommandType;
import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class CommandPrune implements ICommand {

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
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.SPECIFIC;
    }

    @Override
    public Permission[] getPermissions() {
        return new Permission[]{Permission.MESSAGE_MANAGE};
    }
}
