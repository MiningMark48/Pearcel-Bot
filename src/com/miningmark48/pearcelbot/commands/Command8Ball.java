package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.Command;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Random;

public class Command8Ball implements Command{

    public static final String desc = "Roll a magic 8 Ball";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "8ball [arg]";
    public static final String info = desc + " " + usage;

    private static final String[] responses = {"Maybe.", "Certainly not.", "I hope so.", "Not in your wildest dreams.", "There is a good chance.", "Quite likely.", "I think so.", "I hope not.", "I hope so.", "Never!", "Ahaha! Really?!?", "Pfft.", "Sorry, bucko.", "Hell, yes.", "Hell to the no.", "The future is bleak.", "The future is uncertain.", "I would rather not say.", "Who cares?", "Possibly.", "Never, ever, ever.", "There is a small chance.", "Yes!"};

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        Random rand = new Random();
        event.getMessage().deleteMessage().queue();
        if (args.length != 0) {
            MessageBuilder builder = new MessageBuilder();
            builder.append("**The Magic 8 Ball says...** \n");
            for (int i = 0; i <= Integer.valueOf(args[0]) && i <= 25; i++) {
                int num = rand.nextInt(responses.length);
                builder.append("*" + responses[num] + "*\n");
            }
            event.getTextChannel().sendMessage(builder.build()).queue();
        }else{
            int num = rand.nextInt(responses.length);
            event.getTextChannel().sendMessage("**The Magic 8 Ball says...** \n *" + responses[num] + "*").queue();
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
