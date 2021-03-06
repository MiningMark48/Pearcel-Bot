package com.miningmark48.tidalbot.commands.botcommander.configs;

import com.miningmark48.tidalbot.Main;
import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandToggleCommand implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (args.length <= 0) {
            event.getTextChannel().sendMessage("Missing args!").queue();
            return;
        }

        if (!Main.commands.containsKey(args[0]) || args[0].equalsIgnoreCase(this.getName())) {
            event.getTextChannel().sendMessage("That is not a valid command!").queue();
            return;
        }

        ServerConfigHandler.toggleCommandBlacklist(event, args[0]);
        event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + ", **" + (ServerConfigHandler.isCommandBlacklisted(event, args[0]) ? "Disabled" : "Enabled") + "** the *" + args[0] + "* command.").queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "togglecommand";
    }

    @Override
    public String getDesc() {
        return "Toggle the ability to use a command.";
    }

    @Override
    public String getUsage() {
        return "togglecommand <arg:string>";
    }

    @Override
    public CommandType getType() {
        return CommandType.BC;
    }

    @Override
    public boolean isRestricted() {
        return true;
    }
}
