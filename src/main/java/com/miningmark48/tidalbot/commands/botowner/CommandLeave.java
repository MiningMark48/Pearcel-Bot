package com.miningmark48.tidalbot.commands.botowner;

import com.miningmark48.tidalbot.Main;
import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.reference.Reference;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandLeave implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (event.getAuthor().getId().equalsIgnoreCase(Reference.botOwner)){

            if (args.length > 0) {
                try {
                    Guild guild = Main.jda.getGuildById(args[0]);
                    guild.leave().queue();
                    event.getTextChannel().sendMessage("Left **" + guild.getName() + "** *(" + guild.getId() + ")*.").queue();
                } catch (Exception e) {
                    event.getTextChannel().sendMessage("Error!").queue();
                }
            } else {
                event.getTextChannel().sendMessage("Missing args!").queue();
            }
        } else {
            event.getTextChannel().sendMessage(event.getAuthor().getName() + ", You do not have permission to run that command.").queue();
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDesc() {
        return "Will make the bot leave the current Discord guild.";
    }

    @Override
    public String getUsage() {
        return "leave";
    }

    @Override
    public CommandType getType() {
        return CommandType.OWNER;
    }
}
