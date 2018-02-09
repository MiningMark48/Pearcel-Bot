package com.miningmark48.pearcelbot.util.chatlog;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ChatLog {

    public static String fileName = "chatlog//log.txt";

    public static void ChatLog(MessageReceivedEvent event){

        ZonedDateTime dt = event.getMessage().getCreationTime().atZoneSameInstant(ZoneId.of("America/New_York")); //Uses New York Time Zone (EST)
        String time = dt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        String guild = event.getGuild().getName();
        String channel = event.getTextChannel().getName();
        String user = event.getAuthor().getName();
        String message = event.getMessage().getContentRaw();

        if (!JSONChatLog.isChatlogBlacklist(event)) {
            if (!event.getChannel().getType().equals(ChannelType.PRIVATE)) {
                String logMessage = "(" + time + ") [" + guild + "] [" + channel + "] <" + user + "> " + message;

                try (FileWriter fw = new FileWriter(fileName, true);
                     BufferedWriter bw = new BufferedWriter(fw);
                     PrintWriter out = new PrintWriter(bw)) {
                    out.println(logMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}
