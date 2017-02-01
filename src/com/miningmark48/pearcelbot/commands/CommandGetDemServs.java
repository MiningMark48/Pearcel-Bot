package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.Command;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandGetDemServs implements Command{

    public static final String desc = "List all Guilds the bot is connected to.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "getdemservs";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if(event.getAuthor().getId().equalsIgnoreCase(Reference.botOwner)){
            event.getTextChannel().sendMessage("Check your PMs").queue();
            event.getAuthor().openPrivateChannel().queue();
            event.getAuthor().getPrivateChannel().sendMessage(event.getJDA().getGuilds().toString()).queue();
        }else{
            event.getTextChannel().sendMessage(event.getAuthor().getName() + ", You do not have permission to run that command.");
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
