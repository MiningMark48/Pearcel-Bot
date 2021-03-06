package com.miningmark48.tidalbot.commands.music.role_block;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.util.DataUtil;
import com.miningmark48.tidalbot.util.LoggerUtil;
import com.miningmark48.tidalbot.util.YoutubeSearchUtil;
import com.miningmark48.tidalbot.util.features.music.handler.AudioHandler;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandPlaySpotify implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (!ServerConfigHandler.isMusicBlacklisted(event, event.getAuthor().getId())) {

            if (args.length > 0) {
                try {
                    Member member = event.getGuild().getMembersByName(args[0], true).stream().findFirst().get();

                    if (!member.getGame().asRichPresence().getApplicationId().equalsIgnoreCase("0")) {
                        event.getTextChannel().sendMessage("Error! That user may not be listening to Spotify!").queue();
                        return;
                    }

                    String trackQuery = member.getGame().asRichPresence().getDetails() + " by " + member.getGame().asRichPresence().getState();
                    String urlToPlay = (String) YoutubeSearchUtil.searchYoutube(trackQuery, event, YoutubeSearchUtil.SearchType.NORMAL);
                    if (urlToPlay != null) AudioHandler.loadAndPlay(event.getTextChannel(), event.getAuthor(), urlToPlay, false);

                } catch (Exception e) {
                    event.getTextChannel().sendMessage("Error!").queue();
                }
            } else {
                event.getTextChannel().sendMessage("Missing args!").queue();
            }

        } else {
            event.getTextChannel().sendMessage("Sorry " + event.getAuthor().getAsMention() + ", but you do not have permission to use that command. If you think this is a mistake, ask an admin why you have been banned from using music commands.").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "playspotify";
    }

    @Override
    public String getDesc() {
        return "Play a track that a user is listening to on Spotify.";
    }

    @Override
    public String getUsage() {
        return "playspotify <username>";
    }

    @Override
    public CommandType getType() {
        return CommandType.MUSIC;
    }
}
