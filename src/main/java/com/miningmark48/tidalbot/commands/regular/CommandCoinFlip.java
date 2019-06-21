package com.miningmark48.tidalbot.commands.regular;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.util.LoggerUtil;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class CommandCoinFlip implements ICommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        LoggerUtil.log(LoggerUtil.LogType.INFO, "FLIP");
        Random rand = new Random();
        int num;

        num = rand.nextInt(2);
        event.getMessage().delete().queue();
        event.getTextChannel().sendMessage(String.format("\uD83D\uDCB0 **You flipped a coin and got...** *%s* \uD83D\uDCB0", num == 0 ? "Heads" : "Tails")).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "coinflip";
    }

    @Override
    public String getDesc() {
        return "Flip an imaginary coin.";
    }

    @Override
    public String getUsage() {
        return "coinflip";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }
}
