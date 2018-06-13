package com.miningmark48.tidalbot.commands.botcommander;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.LoggerUtil;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandBotCommander implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length == 0) {
            event.getTextChannel().sendMessage("Missing args!").queue();
            return;
        }

        String userID = args[0].replaceAll("[^\\d]", "");

        if (event.getGuild().getMembers().stream().anyMatch(q -> q.getUser().getId().equalsIgnoreCase(userID))) {
            Member member = event.getGuild().getMembers().stream().filter(q -> q.getUser().getId().equalsIgnoreCase(userID)).findFirst().get();
            ServerConfigHandler.addBotCommander(event, member.getUser().getId());
        } else {
            event.getTextChannel().sendMessage("Could not find user!").queue();
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
        return "botcommander";
    }

    @Override
    public String getDesc() {
        return "Will add or remove someone as a bot commander for " + Reference.botName;
    }

    @Override
    public String getUsage() {
        return "botcommander <arg:string>";
    }

    @Override
    public CommandType getType() {
        return CommandType.PBC;
    }

}
