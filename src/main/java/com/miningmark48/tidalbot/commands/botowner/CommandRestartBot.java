package com.miningmark48.tidalbot.commands.botowner;

import com.miningmark48.tidalbot.Main;
import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.reference.Reference;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandRestartBot implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (event.getAuthor().getId().equalsIgnoreCase(Reference.botOwner)){
            event.getTextChannel().sendMessage("Shutting down...").queue();
            event.getJDA().shutdown();
            Main.setupAndConnectBot();
        } else {
            event.getTextChannel().sendMessage(event.getAuthor().getName() + ", You do not have permission to run that command.").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "restartbot";
    }

    @Override
    public String getDesc() {
        return "Restarts the bot.";
    }

    @Override
    public String getUsage() {
        return "restartbot";
    }

    @Override
    public CommandType getType() {
        return CommandType.OWNER;
    }
}
