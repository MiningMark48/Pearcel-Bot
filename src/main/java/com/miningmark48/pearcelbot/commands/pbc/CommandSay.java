package com.miningmark48.pearcelbot.commands.pbc;

import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandSay implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String messageFinal = event.getMessage().getContentRaw().substring(5);

        if (event.getMember().getRoles().contains(Reference.botCommanderRole) || event.getGuild().getOwner() == event.getMember() || event.getAuthor().getId().equals(Reference.botOwner)){
            event.getMessage().delete().queue();
            event.getTextChannel().sendMessage(messageFinal).queue();
        } else {
            event.getTextChannel().sendMessage(event.getAuthor().getName() + ", You do not have permission to run that command.").queue();
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
        return "say";
    }

    @Override
    public String getDesc() {
        return "Make the bot say something.";
    }

    @Override
    public String getUsage() {
        return "say <arg:string>";
    }

    @Override
    public CommandType getType() {
        return CommandType.PBC;
    }
}
