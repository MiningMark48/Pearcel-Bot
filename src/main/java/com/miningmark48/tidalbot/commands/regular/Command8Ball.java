package com.miningmark48.tidalbot.commands.regular;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.base.ICommandPrivate;
import com.miningmark48.tidalbot.util.FormatUtil;
import com.miningmark48.tidalbot.util.MessageUtil;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class Command8Ball implements ICommand, ICommandPrivate {

    private static final String[] responses = {"Maybe.", "Certainly not.", "I hope so.", "Not in your wildest dreams.", "There is a good chance.", "Quite likely.", "I think so.", "I hope not.", "I hope so.", "Never!", "Ahaha! Really?!?", "Pfft.", "Sorry, bucko.", "Hell, yes.", "Hell to the no.", "The future is bleak.", "The future is uncertain.", "I would rather not say.", "Who cares?", "Possibly.", "Never, ever, ever.", "There is a small chance.", "Yes!"};

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        doCmd(event, args, false);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public void actionPrivate(String[] args, MessageReceivedEvent event) {
        doCmd(event, args, true);
    }

    private static void doCmd(MessageReceivedEvent event, String[] args, boolean isPrivate) {
        Random rand = new Random();
        if (!isPrivate) event.getMessage().delete().queue();
        if (args.length != 0) {
            MessageBuilder builder = new MessageBuilder();
            builder.append(FormatUtil.formatText(FormatUtil.FormatType.BOLD, "The Magic 8 Ball says...") + "\n");
            try {
                for (int i = 0; i <= Integer.valueOf(args[0]) && i <= 25; i++) {
                    int num = rand.nextInt(responses.length);
                    builder.append(FormatUtil.formatText(FormatUtil.FormatType.ITALIC, responses[num]) + "\n");
                }
                MessageUtil.sendMessage(event, builder.build(), isPrivate).queue();
            } catch (NumberFormatException e) {
                MessageUtil.sendMessage(event, "**Error:** *" + args[0] + "* is not a valid argument. Argument must be an int.", isPrivate).queue();
            }
        } else {
            int num = rand.nextInt(responses.length);
            MessageUtil.sendMessage(event, FormatUtil.formatText(FormatUtil.FormatType.BOLD, "The Magic 8 Ball says...") + " \n " + FormatUtil.formatText(FormatUtil.FormatType.ITALIC, responses[num]) + "", isPrivate).queue();
        }
    }

    @Override
    public String getName() {
        return "8ball";
    }

    @Override
    public String getDesc() {
        return "Roll a Magic 8-Ball™";
    }

    @Override
    public String getUsage() {
        return "8ball [arg:int]";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }
}
