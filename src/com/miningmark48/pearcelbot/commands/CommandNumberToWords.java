package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.ICommand;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.NumberToWords;
import com.miningmark48.pearcelbot.util.Tools;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.text.WordUtils;

import java.time.temporal.ChronoUnit;

public class CommandNumberToWords implements ICommand {

    public static final String desc = "Coverts a number to words.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "numtoword <int>";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length <= 0) {
            event.getTextChannel().sendMessage("Error, missing args! " + usage).queue();
        } else {
            event.getTextChannel().sendMessage(Tools.formatText(Tools.FormatType.BOLD_UNDERLINE, args[0] + " as words: \n") + WordUtils.capitalize(NumberToWords.convert(Long.valueOf(args[0])))).queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

}
