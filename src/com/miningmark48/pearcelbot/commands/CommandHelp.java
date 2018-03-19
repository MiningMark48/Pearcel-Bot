package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.Main;
import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.util.FormatUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandHelp implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length > 0) {
            if (Main.commands.entrySet().stream().anyMatch(q -> q.getKey().equalsIgnoreCase(args[0]))) {
                ICommand command = (Main.commands.entrySet().stream().filter(q -> q.getKey().equalsIgnoreCase(args[0])).findFirst().get().getValue());
                if (command instanceof ICommandInfo) {
                    ICommandInfo commandInfo = (ICommandInfo) command;
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(Color.decode("#a2f000"));
                    builder.setAuthor(commandInfo.getName());
                    builder.setDescription((commandInfo.getDesc() + "\n\n" + FormatUtil.formatText(FormatUtil.FormatType.BOLD, "USAGE: ")+ commandInfo.getUsage()));
                    event.getTextChannel().sendMessage(builder.build()).queue();
                }
            }
        } else {
            event.getTextChannel().sendMessage("Missing arguments!").queue();
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDesc() {
        return "Get help for a specific command.";
    }

    @Override
    public String getUsage() {
        return "help <arg:string>";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }

}
