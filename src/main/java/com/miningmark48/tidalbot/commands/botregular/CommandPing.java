package com.miningmark48.tidalbot.commands.botregular;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.commands.base.ICommandPrivate;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.time.temporal.ChronoUnit;

public class CommandPing implements ICommand, ICommandPrivate, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("\uD83C\uDFD3 **Pong! **...").queue(m -> m.editMessage("\uD83C\uDFD3 **Pong! **" + Math.abs(event.getMessage().getCreationTime().until(m.getCreationTime(), ChronoUnit.MILLIS)) + "ms \uD83D\uDCF6").queue());
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

    @Override
    public void actionPrivate(String[] args, MessageReceivedEvent event) {
        event.getPrivateChannel().sendMessage("\uD83C\uDFD3 **Pong! **...").queue(m -> m.editMessage("\uD83C\uDFD3 **Pong! **" + event.getMessage().getCreationTime().until(m.getCreationTime(), ChronoUnit.MILLIS) + "ms \uD83D\uDCF6").queue());
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
    public CommandType getType() {
        return CommandType.NORMAL;
    }

}
