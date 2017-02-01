package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.ICommand;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandBark implements ICommand {

    public static final String desc = "Bark, bark, bark.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "bark";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("" +
                "**Bark, bark, bark.**\n" +
                "─────────▄──────────────▄\n" +
                "────────▌▒█───────────▄▀▒▌\n" +
                "────────▌▒▒▀▄───────▄▀▒▒▒▐\n" +
                "───────▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐\n" +
                "─────▄▄▀▒▒▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐\n" +
                "───▄▀▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▀██▀▒▌\n" +
                "──▐▒▒▒▄▄▄▒▒▒▒▒▒▒▒▒▒▒▒▒▀▄▒▒▌\n" +
                "──▌▒▒▐▄█▀▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐\n" +
                "─▐▒▒▒▒▒▒▒▒▒▒▒▌██▀▒▒▒▒▒▒▒▒▀▄▌\n" +
                "─▌▒▀▄██▄▒▒▒▒▒▒▒▒▒▒▒░░░░▒▒▒▒▌\n" +
                "─▌▀▐▄█▄█▌▄▒▀▒▒▒▒▒▒░░░░░░▒▒▒▐\n" +
                "▐▒▀▐▀▐▀▒▒▄▄▒▄▒▒▒▒▒░░░░░░▒▒▒▒▌\n" +
                "▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒░░░░░░▒▒▒▐\n" +
                "─▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒▒▒░░░░▒▒▒▒▌\n" +
                "─▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐\n" +
                "──▀▄▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▄▒▒▒▒▌\n" +
                "────▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀\n" +
                "───▐▀▒▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀\n" +
                "──▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▀▀").queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
