package com.miningmark48.pearcelbot.commands.music;

import com.miningmark48.pearcelbot.commands.ICommand;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.YoutubeSearch;
import com.miningmark48.pearcelbot.util.music.handler.AudioHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandPlaySearchRemix implements ICommand {

    public static final String desc = "Searches and plays first remix of a track from a YouTube search query.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "splayremix <YouTube search query>";
    public static final String info = desc + " " + usage;


    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String urlToPlay = YoutubeSearch.searchYoutube(args, event, YoutubeSearch.SearchType.REMIX);
        if (urlToPlay != null) AudioHandler.loadAndPlay(event.getTextChannel(), event.getAuthor(), urlToPlay, false);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
