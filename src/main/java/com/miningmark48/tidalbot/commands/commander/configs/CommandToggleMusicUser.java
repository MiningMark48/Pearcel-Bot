package com.miningmark48.tidalbot.commands.commander.configs;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.util.FormatUtil;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.api.entities.Member;
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

        String userID = FormatUtil.removeNonNumeric(args[0]);

        if (event.getGuild().getMembers().stream().anyMatch(q -> q.getUser().getId().equalsIgnoreCase(userID))) {
            Member member = event.getGuild().getMembers().stream().filter(q -> q.getUser().getId().equalsIgnoreCase(userID)).findFirst().get();
            ServerConfigHandler.toggleMusicUserBlacklist(event, member.getUser().getId());
            event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + ", **" + (ServerConfigHandler.isMusicBlacklisted(event, event.getAuthor().getId()) ? "removed" : "added") + "** *" + member.getUser().getName() + "* to the banned music users list.").queue();
        } else {
            event.getTextChannel().sendMessage("Could not find user!").queue();
        }
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
