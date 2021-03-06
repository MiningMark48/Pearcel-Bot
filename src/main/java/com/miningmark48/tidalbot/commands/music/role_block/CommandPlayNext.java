package com.miningmark48.tidalbot.commands.music.role_block;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.MessageUtil;
import com.miningmark48.tidalbot.util.features.music.handler.AudioHandler;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandPlayNext implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (args.length == 0) {
            event.getTextChannel().sendMessage("Missing args! \n**Usage: **" + getUsage()).queue();
            return;
        }

        if (!ServerConfigHandler.isMusicBlacklisted(event, event.getAuthor().getId())) {
            AudioHandler.playNext(event.getTextChannel(), args);
        } else {
            event.getTextChannel().sendMessage("Sorry " + event.getAuthor().getAsMention() + ", but you do not have permission to use that command. If you think this is a mistake, ask an admin why you have been banned from using music commands.").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "playnext";
    }

    @Override
    public String getDesc() {
        return "Make a track play next in the queue by keyword.";
    }

    @Override
    public String getUsage() {
        return "playnext <keyword - in queue>";
    }

    @Override
    public CommandType getType() {
        return CommandType.MUSIC;
    }
}
