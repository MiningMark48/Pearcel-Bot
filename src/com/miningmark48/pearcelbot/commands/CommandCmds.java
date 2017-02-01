package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.Command;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CommandCmds implements Command{

    public static final String desc = "Returns a list of commands.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "cmds";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (event.getAuthor() == null) {
            return;
        }

        if (event.getAuthor().getPrivateChannel() == null) {
            event.getAuthor().openPrivateChannel().queue();
        }

        if (event.getAuthor() == null) {
            event.getTextChannel().sendMessage("Sending you a list of commands now.").queue();
        }else {
            event.getTextChannel().sendMessage("**" + event.getAuthor().getAsMention() + "**, Sending you a list of commands now.").queue();
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
        messageBuilderM.append("**Pearcel Bot's Music Commands were moved to DJ Pearcel Bot.** Click this link to get it: *[Coming Soon]*\n");

        //Disabled.
        //messageBuilderM.append("To use auto-response talking (Chat Bot), give *" + event.getJDA().getSelfUser().getName() + "* the role `" + Reference.botAutoResponseRole + "`.\n");

        event.getAuthor().getPrivateChannel().sendMessage(messageBuilder.build()).queue();
        //event.getAuthor().getPrivateChannel().sendMessage(messageBuilder2.build()).queue();
        event.getAuthor().getPrivateChannel().sendMessage(messageBuilderPBC.build()).queue();
        event.getAuthor().getPrivateChannel().sendMessage(messageBuilderM.build()).queue();


    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
