package com.miningmark48.tidalbot.commands;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.util.FormatUtil;
import com.miningmark48.tidalbot.util.FormatUtil.FormatType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandBark implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("" +
                FormatUtil.formatText(FormatType.BOLD, "Bark, bark, bark.") + "\n" +
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

    @Override
    public String getName() {
        return "bark";
    }

    @Override
    public String getDesc() {
        return "Bark, bark, bark.";
    }

    @Override
    public String getUsage() {
        return "bark";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
