package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.ICommand;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Random;

public class CommandCoinFlip implements ICommand {

    public static final String desc = "Flip an imaginary coin.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "coin";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        Random rand = new Random();

        int num;

        num = rand.nextInt(2);

        event.getMessage().delete().queue();
        event.getTextChannel().sendMessage(String.format("\uD83D\uDCB0 **You flipped a coin and got...** *%s* \uD83D\uDCB0", num == 0 ? "Heads" : "Tails")).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
