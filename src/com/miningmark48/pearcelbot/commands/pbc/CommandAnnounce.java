package com.miningmark48.pearcelbot.commands.pbc;

import com.miningmark48.pearcelbot.commands.ICommand;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandAnnounce implements ICommand {

    public static final String desc = "Announces to the channel.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "announce <arg>";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        String messageFinal = event.getMessage().getContent().substring(10);

        if (event.getMember().getRoles().toString().contains(Reference.botCommanderRole) || event.getGuild().getOwner() == event.getMember() || event.getAuthor().getId().equals(Reference.botOwner)){
            event.getTextChannel().sendMessage("**-----Announcement-----**\n" + messageFinal + "\n**----------------------------**").queue();
            event.getMessage().delete().queue();
        }else{
            event.getTextChannel().sendMessage(event.getAuthor().getName() + ", You do not have permission to run that command.").queue();
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }
}
