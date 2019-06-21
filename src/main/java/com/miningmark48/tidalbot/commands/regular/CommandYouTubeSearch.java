package com.miningmark48.tidalbot.commands.regular;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.util.UtilFormat;
import com.miningmark48.tidalbot.util.YoutubeSearchUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandYouTubeSearch implements ICommand {

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
            builder.setTitle(UtilFormat.formatText(UtilFormat.FormatType.BOLD_UNDERLINE, "YouTube Search Results"));
            builder.setFooter("YouTube search performed by Google APIs", null);
            builder.setColor(Color.RED);
            builder.setThumbnail("https://i.imgur.com/wGlpS4b.png");

            for (int i = 0; i <= 10 && i < ((JsonArray) searchObj).getAsJsonArray().size(); i++) {
                JsonObject js = ((JsonArray) searchObj).getAsJsonArray().get(i).getAsJsonObject();
                JsonObject snippet = js.get("snippet").getAsJsonObject();
                builder.addField(snippet.get("title").getAsString(), UtilFormat.formatURL("Video", baseVideoURL + js.get("id").getAsJsonObject().get("videoId").getAsString()) + " by " + UtilFormat.formatURL(snippet.get("channelTitle").getAsString(), baseChannelURL + snippet.get("channelId").getAsString()), false);
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
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }
}
