package com.miningmark48.pearcelbot.util.features.music;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.miningmark48.pearcelbot.util.FormatUtil;
import com.miningmark48.pearcelbot.util.LoggerUtil;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LyricLookup {

    private final static String songLyricsURL = "http://www.songlyrics.com";

    public static List<String> getSongLyrics(String band, String songTitle) throws IOException {
        List<String> lyrics= new ArrayList<String>();

        try {
            Document doc = Jsoup.connect(songLyricsURL + "/" + band.replace(" ", "-").toLowerCase()+"/" + songTitle.replace(" ", "-").toLowerCase()+"-lyrics/").userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
            String title = doc.title();
            System.out.println(title);
            Element p = doc.select("p.songLyricsV14").get(0);
            for (Node e: p.childNodes()) {
                if (e instanceof TextNode) {
                    lyrics.add(((TextNode)e).getWholeText());
                }
            }
            return lyrics;
        }catch (HttpStatusException e){
            return null;
        }
    }

    public static void postLyrics(TextChannel channel, String[] query){
        StringBuilder builder = new StringBuilder();
        Arrays.stream(query).forEach(builder::append);
        String original = builder.toString();

        String[] split = original.split("::");

        String artist = split[0];
        String title = split[1];

        LoggerUtil.log(LoggerUtil.LogType.DEBUG, "Artist: " + artist);
        LoggerUtil.log(LoggerUtil.LogType.DEBUG, "Title: " + title);

        try {
            channel.sendMessage(FormatUtil.formatText(FormatUtil.FormatType.BOLD,String.format("Lyrics for %s - %s:\n\n", artist.toUpperCase(), title.toUpperCase()))).queue();

            List lyrics = getSongLyrics(artist, title);
            StringBuilder lyricStringBuilder = new StringBuilder();
            String lyricString;
            lyrics.stream().forEach(queryLyric -> lyricStringBuilder.append(queryLyric + "\n"));
            lyricString = lyricStringBuilder.toString();
            Iterable<String> result = Splitter.fixedLength(1999).split(lyricString);
            String[] lyrics_parts = Iterables.toArray(result, String.class);
            for (String part : lyrics_parts){
                MessageBuilder messageBuilder = new MessageBuilder();
                messageBuilder.append(part);
                channel.sendMessage(messageBuilder.build()).queue();
            }

            channel.sendMessage(FormatUtil.formatText(FormatUtil.FormatType.BOLD,"Lyrics powered by SongLyrics.com")).queue();

        } catch (IOException | NullPointerException e) {
            channel.sendMessage(FormatUtil.formatText(FormatUtil.FormatType.BOLD,"ERROR:") + " Lyrics not found.").queue();
        }

    }

}
