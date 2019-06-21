package com.miningmark48.tidalbot.commands.botregular;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Arrays;

public class CommandQRCreate implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String baseApiURL = "http://api.qrserver.com/v1/create-qr-code/?size=200x200&margin=10&bgcolor=ffffff&color=000000&data=";
        StringBuilder builder = new StringBuilder();

        if (args.length > 0) {
            try {
                event.getMessage().delete().queue();

                Arrays.stream(args).forEach(q -> builder.append(q).append("%20"));
                Connection.Response img = Jsoup.connect(baseApiURL + builder.toString()).ignoreContentType(true).execute();

                event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + ", here you go!").queue();
                event.getTextChannel().sendFile(img.bodyAsBytes(), args[0] + ".png").queue();

            } catch (IOException e) {
                event.getTextChannel().sendMessage("An error occurred.").queue();
                e.printStackTrace();
            }
        } else {
            event.getTextChannel().sendMessage("**Missing args!**\n\nUSAGE: " + getUsage()).queue();
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "qrcodecreate";
    }

    @Override
    public String getDesc() {
        return "Create a QR code.";
    }

    @Override
    public String getUsage() {
        return "qrcodecreate <arg:string>";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
