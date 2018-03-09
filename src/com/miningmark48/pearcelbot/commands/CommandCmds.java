package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.Main;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.FormatUtil;
import com.miningmark48.pearcelbot.util.Logger;
import com.sun.deploy.trace.LoggerTraceListener;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.RestAction;

import java.awt.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CommandCmds implements ICommand, ICommandPrivate, ICommandInfo {

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

    @Override
    public String getName() {
        return "cmds";
    }

    @Override
    public String getDesc() {
        return "Returns a list of commands.";
    }

    @Override
    public String getUsage() {
        return "cmds";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
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

        EmbedBuilder embedBuilderNormal = new EmbedBuilder();
        EmbedBuilder embedBuilderPBC = new EmbedBuilder();
        EmbedBuilder embedBuilderMusic = new EmbedBuilder();
        EmbedBuilder embedBuilderOther = new EmbedBuilder();

        embedBuilderNormal.setTitle("Normal Commands");
        embedBuilderNormal.setColor(Color.decode("#a2f000"));
        embedBuilderPBC.setTitle("PBC Commands");
        embedBuilderPBC.setColor(Color.decode("#a2f000"));
        embedBuilderMusic.setTitle("Music Commands");
        embedBuilderMusic.setColor(Color.decode("#a2f000"));
        embedBuilderOther.setTitle("Other Commands");
        embedBuilderOther.setColor(Color.decode("#a2f000"));

        Main.commands.forEach((key, value) -> {
            if (value instanceof ICommandInfo) {
                ICommandInfo cmd = (ICommandInfo) value;
                switch (cmd.getType()) {
                    default:
                        embedBuilderOther.addField(cmd.getName(), cmd.getDesc() + "\nUSAGE: " + cmd.getUsage(), true);
                        break;
                    case NORMAL:
                        embedBuilderNormal.addField(cmd.getName(), cmd.getDesc() + "\nUSAGE: " + cmd.getUsage(), true);
                        break;
                    case PBC:
                        embedBuilderPBC.addField(cmd.getName(), cmd.getDesc() + "\nUSAGE: " + cmd.getUsage(), true);
                        break;
                    case MUSIC:
                        embedBuilderMusic.addField(cmd.getName(), cmd.getDesc() + "\nUSAGE: " + cmd.getUsage(), true);
                        break;
                }
            } else {
                embedBuilderOther.addField(key, "Missing Description", true);
            }
        });

        privateChannel.queue(chan -> {
            chan.sendMessage(FormatUtil.bold("Commands:")).queue();
            chan.sendMessage(embedBuilderNormal.build()).queueAfter(500, TimeUnit.MILLISECONDS);
            chan.sendMessage(embedBuilderPBC.build()).queueAfter(1000, TimeUnit.MILLISECONDS);
            chan.sendMessage(embedBuilderMusic.build()).queueAfter(1500, TimeUnit.MILLISECONDS);
            chan.sendMessage(embedBuilderOther.build()).queueAfter(2000, TimeUnit.MILLISECONDS);
        });
    }

}
