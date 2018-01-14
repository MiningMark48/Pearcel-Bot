package com.miningmark48.pearcelbot.commands.music.role_block;

import com.miningmark48.pearcelbot.commands.ICommand;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.YoutubeSearch;
import com.miningmark48.pearcelbot.util.music.handler.AudioHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandPlaySearch implements ICommand {

    public static final String desc = "Searches and plays first track from a YouTube search query.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "splay <YouTube search query>";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String urlToPlay = YoutubeSearch.searchYoutube(args, event, YoutubeSearch.SearchType.NORMAL);
        if (urlToPlay != null) AudioHandler.loadAndPlay(event.getTextChannel(), event.getAuthor(), urlToPlay, false);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
