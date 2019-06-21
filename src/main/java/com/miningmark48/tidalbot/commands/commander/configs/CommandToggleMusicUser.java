package com.miningmark48.tidalbot.commands.commander.configs;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.util.UtilFormat;
import com.miningmark48.tidalbot.util.UtilUserSelection;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandToggleMusicUser implements ICommand {

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
        User user = UtilUserSelection.getUserByAny(event.getGuild(), args[0], event.getMessage());
        if (user == null) {
            event.getTextChannel().sendMessage("Error, user not found!").queue();
            return;
        }
        ServerConfigHandler.toggleMusicUserBlacklist(event, user.getId());
        event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + ", **" + (ServerConfigHandler.isMusicBlacklisted(event, event.getAuthor().getId()) ? "removed" : "added") + "** *" + user.getName() + "* to the banned music users list.").queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "togglemusic";
    }

    @Override
    public String getDesc() {
        return "Will toggle a user's ability to use the music commands.";
    }

    @Override
    public String getUsage() {
        return "togglemusic <arg:string>";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.MANAGER;
    }

}
