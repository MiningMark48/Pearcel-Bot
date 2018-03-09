package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandRoles implements ICommand, ICommandInfo {

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

    @Override
    public String getName() {
        return "roles";
    }

    @Override
    public String getDesc() {
        return "Get all information about the roles used by " + Reference.botName;
    }

    @Override
    public String getUsage() {
        return "roles";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
