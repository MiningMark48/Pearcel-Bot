package com.miningmark48.tidalbot.commands.regular;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.base.ICommandPrivate;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.temporal.ChronoUnit;

public class CommandPing implements ICommand, ICommandPrivate {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("\uD83C\uDFD3 **Pong! **...").queue(m -> m.editMessage("\uD83C\uDFD3 **Pong! **" + Math.abs(event.getMessage().getTimeCreated().until(m.getTimeCreated(), ChronoUnit.MILLIS)) + "ms \uD83D\uDCF6").queue());
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

    @Override
    public void actionPrivate(String[] args, MessageReceivedEvent event) {
        event.getPrivateChannel().sendMessage("\uD83C\uDFD3 **Pong! **...").queue(m -> m.editMessage("\uD83C\uDFD3 **Pong! **" + event.getMessage().getTimeCreated().until(m.getTimeCreated(), ChronoUnit.MILLIS) + "ms \uD83D\uDCF6").queue());
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDesc() {
        return "Simple Ping, Pong.";
    }

    @Override
    public String getUsage() {
        return "ping";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }

}
