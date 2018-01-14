package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandRoles implements ICommand {

    public static final String desc = "Get all information about the roles used by " + Reference.botName;
    public static final String usage = "USAGE: " + Reference.botCommandKey + "roles";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("**" + Reference.botCommanderRole + "** - used for controlling admin-only commands such as the say or announce command. \n");
        builder.append("**" + Reference.botAutoResponseRole + "** - used to tell enable auto-response messages for the bot (assign to the bot itself) \n");
        builder.append("**" + Reference.botNoMusicRole + "** - used to prevent a user from controlling the bot's music \n");
        event.getTextChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
