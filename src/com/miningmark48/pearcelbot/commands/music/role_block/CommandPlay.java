package com.miningmark48.pearcelbot.commands.music.role_block;

import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.DataUtil;
import com.miningmark48.pearcelbot.util.YoutubeSearchUtil;
import com.miningmark48.pearcelbot.util.features.music.handler.AudioHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandPlay implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (event.getMember() == null) { //Webhook support
            String urlToPlay = (String) YoutubeSearchUtil.searchYoutube(args, event, YoutubeSearchUtil.SearchType.NORMAL);
            if (urlToPlay != null) AudioHandler.loadAndPlay(event.getTextChannel(), event.getAuthor(), urlToPlay, false);
            return;
        }
        if (!event.getMember().getRoles().toString().contains(Reference.botNoMusicRole)) {
            if (DataUtil.isValidURL(args[0])) {
                AudioHandler.loadAndPlay(event.getTextChannel(), event.getAuthor(), args[0], false);
            } else {
                String urlToPlay = (String) YoutubeSearchUtil.searchYoutube(args, event, YoutubeSearchUtil.SearchType.NORMAL);
                if (urlToPlay != null) AudioHandler.loadAndPlay(event.getTextChannel(), event.getAuthor(), urlToPlay, false);
            }
        } else {
            event.getTextChannel().sendMessage("Sorry " + event.getAuthor().getAsMention() + ", but you do not have permission to use that command. If you think this is a mistake, ask an admin why you have the `" + Reference.botNoMusicRole + "` role.").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getDesc() {
        return "Plays a track from a url or query.";
    }

    @Override
    public String getUsage() {
        return "play <track url> OR play <search query>";
    }

    @Override
    public CommandType getType() {
        return CommandType.MUSIC;
    }
}
