package com.miningmark48.tidalbot.commands.regular;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.util.UtilFormat;
import com.miningmark48.tidalbot.util.UtilMessage;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CommandPastebin implements ICommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length > 0) {
            String sUrl = "https://pastebin.com/raw/" + args[0];
            try {
                UtilMessage.sendTyping(event).queue();
                URL url = new URL(sUrl);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                MessageBuilder builder = new MessageBuilder();

                String line;
                while ((line = reader.readLine()) != null && builder.length() <= 1500) {
                    builder.append(line).append("\n");
                }
                reader.close();

                builder.append("\n").append(UtilFormat.formatText(UtilFormat.FormatType.BOLD_ITALIC, sUrl + " \n"));

                UtilMessage.sendMessage(event, builder.build()).queue();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            UtilMessage.sendMessage(event, "Missing args! Please supply Pastebin code!").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "pastebin";
    }

    @Override
    public String getDesc() {
        return "Fetch data from a Pastebin.";
    }

    @Override
    public String getUsage() {
        return "pastebin <arg:string>";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }
}
