package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.ICommand;
import com.miningmark48.pearcelbot.ICommandPrivate;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.RestAction;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CommandCmds implements ICommand, ICommandPrivate {

    public static final String desc = "Returns a list of commands.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "cmds";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        getCmds(event, false);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public void actionPrivate(String[] args, MessageReceivedEvent event) {
        getCmds(event, true);
    }

    private static void getCmds(MessageReceivedEvent event, boolean isPrivate) {
        if (event.getAuthor() == null) {
            return;
        }

        RestAction<PrivateChannel> privateChannel = event.getAuthor().openPrivateChannel();

        if (!isPrivate) {
            if (event.getAuthor() == null) {
                event.getTextChannel().sendMessage("Sending you a list of commands now.").queue();
            } else {
                event.getTextChannel().sendMessage("**" + event.getAuthor().getAsMention() + "**, Sending you a list of commands now.").queue();
            }
        }

        Set set = Reference.commandUsage.entrySet();
        Iterator i = set.iterator();

        Set set2 = Reference.commandUsage2.entrySet();
        Iterator j = set2.iterator();

        Set setPBC = Reference.commandUsagePBC.entrySet();
        Iterator k = setPBC.iterator();

        String message = "";
        String message2 = "";
        String messagePBC = "";

        while (i.hasNext()){
            Map.Entry me = (Map.Entry)i.next();
            message = message + me.getKey() + ": " + me.getValue() + "\n";
        }

        while (j.hasNext()){
            Map.Entry me2 = (Map.Entry)j.next();
            message2 = message2 + me2.getKey() + ": " + me2.getValue() + "\n";
        }

        while (k.hasNext()){
            Map.Entry mePBC = (Map.Entry)k.next();
            messagePBC = messagePBC + mePBC.getKey() + ": " + mePBC.getValue() + "\n";
        }

        MessageBuilder messageBuilder = new MessageBuilder();
        MessageBuilder messageBuilder2 = new MessageBuilder();
        MessageBuilder messageBuilderPBC = new MessageBuilder();
        MessageBuilder messageBuilderM = new MessageBuilder();

        messageBuilder.append("**Regular Commands: **\n");
        messageBuilder.append("```" + message + "```");
        messageBuilder2.append("```" + message2 + "```\n");
        messageBuilderPBC.append("**" + Reference.botCommanderRole + " Commands: **\n");
        messageBuilderPBC.append("```" + messagePBC + "```\n");

        messageBuilderM.append("For music commands, do `~~help`\n");
        messageBuilderM.append("`✓` = Works in direct messages");

        privateChannel.queue(chan -> {
            chan.sendMessage(messageBuilder.build()).queue();
            chan.sendMessage(messageBuilder2.build()).queueAfter(1500, TimeUnit.MILLISECONDS);
            chan.sendMessage(messageBuilderPBC.build()).queueAfter(1750, TimeUnit.MILLISECONDS);
            chan.sendMessage(messageBuilderM.build()).queueAfter(2000, TimeUnit.MILLISECONDS);
        });
    }

}
