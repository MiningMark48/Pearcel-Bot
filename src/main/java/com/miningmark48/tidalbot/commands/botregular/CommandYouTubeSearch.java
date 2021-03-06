package com.miningmark48.tidalbot.commands.botregular;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.util.FormatUtil;
import com.miningmark48.tidalbot.util.LoggerUtil;
import com.miningmark48.tidalbot.util.YoutubeSearchUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandYouTubeSearch implements ICommand, ICommandInfo {

    String baseChannelURL = "https://www.youtube.com/channel/";
    String baseVideoURL = "https://www.youtube.com/watch?v=";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event){
        Object searchObj = YoutubeSearchUtil.searchYoutube(args, event, YoutubeSearchUtil.SearchType.QUERY);
        if (searchObj instanceof JsonArray) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle(FormatUtil.formatText(FormatUtil.FormatType.BOLD_UNDERLINE, "YouTube Search Results"));
            builder.setFooter("YouTube search performed by Google APIs", null);
            builder.setColor(Color.RED);
            builder.setThumbnail("https://i.imgur.com/wGlpS4b.png");

            for (int i = 0; i <= 10 && i < ((JsonArray) searchObj).getAsJsonArray().size(); i++) {
                JsonObject js = ((JsonArray) searchObj).getAsJsonArray().get(i).getAsJsonObject();
                JsonObject snippet = js.get("snippet").getAsJsonObject();
                builder.addField(snippet.get("title").getAsString(),FormatUtil.formatURL("Video", baseVideoURL + js.get("id").getAsJsonObject().get("videoId").getAsString()) + " by " + FormatUtil.formatURL(snippet.get("channelTitle").getAsString(), baseChannelURL + snippet.get("channelId").getAsString()), false);
            }

            event.getTextChannel().sendMessage(builder.build()).queue();

        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "youtubesearch";
    }

    @Override
    public String getDesc() {
        return "Searches YouTube and returns back a queue.";
    }

    @Override
    public String getUsage() {
        return "youtubesearch <arg:string>";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
