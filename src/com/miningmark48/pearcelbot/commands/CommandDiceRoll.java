package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Random;

public class CommandDiceRoll implements ICommand {

    public static final String desc = "Rolls an imaginary dice.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "dice [arg]";
    public static final String info = desc + " " + usage;

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
            if (Integer.valueOf(args[0]) <= Integer.MAX_VALUE && Integer.valueOf(args[0]) >= 2) {
                diceNum = rand.nextInt(Integer.valueOf(args[0])) + 1;
                event.getTextChannel().sendMessage("\uD83C\uDFB2 **Dice Roll: (**" + args[0] + "**) :** " + diceNum + " \uD83C\uDFB2").queue();
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
}
