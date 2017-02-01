package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.Command;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.Logger;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandSay implements Command{

    public static final String desc = "Make the bot say something.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "say <arg>";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String messageFinal = event.getMessage().getContent().substring(5);

        if (event.getMember().getRoles().contains(Reference.botCommanderRole) || event.getGuild().getOwner() == event.getMember() || event.getAuthor().getId().equals(Reference.botOwner)){
            event.getMessage().deleteMessage().queue();
            event.getTextChannel().sendMessage(messageFinal).queue();
        }else{
            event.getTextChannel().sendMessage(event.getAuthor().getName() + ", You do not have permission to run that command.").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }
}
