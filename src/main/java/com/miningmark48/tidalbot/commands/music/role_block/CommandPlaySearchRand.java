package com.miningmark48.tidalbot.commands.music.role_block;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.util.UtilYoutubeSearch;
import com.miningmark48.tidalbot.util.features.music.handler.AudioHandler;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandPlaySearchRand implements ICommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (!ServerConfigHandler.isMusicBlacklisted(event, event.getAuthor().getId())) {
            String urlToPlay = (String) UtilYoutubeSearch.searchYoutube(args, event, UtilYoutubeSearch.SearchType.RANDOM);
            if (urlToPlay != null) AudioHandler.loadAndPlay(event.getTextChannel(), event.getAuthor(), urlToPlay, false);
        } else {
            event.getTextChannel().sendMessage("Sorry " + event.getAuthor().getAsMention() + ", but you do not have permission to use that command. If you think this is a mistake, ask an admin why you have been banned from using music commands.").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "splayrand";
    }

    @Override
    public String getDesc() {
        return "Searches and plays random track from a YouTube search query.";
    }

    @Override
    public String getUsage() {
        return "splayrand <arg:string>";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }

    @Override
    public boolean isMusic() {
        return true;
    }
}
