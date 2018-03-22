package com.miningmark48.pearcelbot.commands.pbc;

import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandAnnounce implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String messageFinal = event.getMessage().getContentRaw().substring(10);
        event.getTextChannel().sendMessage("**-----Announcement-----**\n" + messageFinal + "\n**----------------------------**").queue();
        event.getMessage().delete().queue();
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
        return "announce";
    }

    @Override
    public String getDesc() {
        return "Announces a message to the channel.";
    }

    @Override
    public String getUsage() {
        return "announce <arg:string>";
    }

    @Override
    public CommandType getType() {
        return CommandType.PBC;
    }
}
