package com.miningmark48.tidalbot.commands.botregular;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.util.FormatUtil;
import com.miningmark48.tidalbot.util.MessageUtil;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CommandPastebin implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length > 0) {
            String sUrl = "https://pastebin.com/raw/" + args[0];
            try {
                MessageUtil.sendTyping(event).queue();
                URL url = new URL(sUrl);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                MessageBuilder builder = new MessageBuilder();

                String line;
                while ((line = reader.readLine()) != null && builder.length() <= 1500) {
                    builder.append(line).append("\n");
                }
                reader.close();

                builder.append("\n").append(FormatUtil.formatText(FormatUtil.FormatType.BOLD_ITALIC, sUrl + " \n"));

                MessageUtil.sendMessage(event, builder.build()).queue();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            MessageUtil.sendMessage(event, "Missing args! Please supply Pastebin code!").queue();
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
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
