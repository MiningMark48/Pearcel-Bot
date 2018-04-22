package com.miningmark48.tidalbot.commands;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Random;

public class CommandDiceRoll implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        Random rand = new Random();

        int diceNum;
        int diceNumDefault = 6;

        if (args.length == 0){
            diceNum = rand.nextInt(diceNumDefault) + 1;
            event.getTextChannel().sendMessage("\uD83C\uDFB2 **Dice Roll:** " + diceNum + " \uD83C\uDFB2").queue();
        }else{
            int diceArg = Math.abs(Integer.valueOf(args[0]));
            if (diceArg >= 2) {
                diceNum = rand.nextInt(diceArg) + 1;
                event.getTextChannel().sendMessage("\uD83C\uDFB2 **Dice Roll: (**" + diceArg + "**) :** " + diceNum + " \uD83C\uDFB2").queue();
            }else{
                diceNum = diceNumDefault;
                event.getTextChannel().sendMessage("Invalid, rolling to " + diceNum + ".").queue();
            }
        }

        event.getMessage().delete().queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "diceroll";
    }

    @Override
    public String getDesc() {
        return "Rolls an imaginary dice.";
    }

    @Override
    public String getUsage() {
        return "diceroll [arg:int]";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }

}
