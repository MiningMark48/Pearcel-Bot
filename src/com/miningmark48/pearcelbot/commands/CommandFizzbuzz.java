package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandFizzbuzz implements ICommand{

    public static final String desc = "Fizzbuzz!";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "fizzbuzz <arg:int>";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        String arg = args.length == 0 ? "15" : args[0];
        int amount = Integer.valueOf(arg);
        int maxAmount = 250;
        if (!(amount <= maxAmount)) amount = maxAmount;
        MessageBuilder builder = new MessageBuilder();

        builder.append(String.format("__**Fizzbuzz!** -- *%s*\n\n__", amount));

        for (int i = 1; i <= amount; i++){
            if (i % 3 == 0){
                if (i % 5 == 0){
                    builder.append("Fizzbuzz" + (i != amount ? ", " : ""));
                }else{
                    builder.append("Fizz" + (i != amount ? ", " : ""));
                }
            }else if(i % 5 == 0){
                builder.append("Buzz" + (i != amount ? ", " : ""));
            }else{
                builder.append(i + (i != amount ? ", " : ""));
            }
        }

        event.getTextChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

}
