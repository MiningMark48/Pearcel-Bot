package com.miningmark48.tidalbot.commands.botregular;

import com.google.common.collect.Lists;
import com.miningmark48.tidalbot.Main;
import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.commands.base.ICommandPrivate;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.FormatUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.RestAction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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
                event.getTextChannel().sendMessage("Sending you a list of commands now. Optionally, visit <https://tw.miningmark48.xyz/projects/tidalbot/>.").queue();
            } else {
                event.getTextChannel().sendMessage("**" + event.getAuthor().getAsMention() + "**, Sending you a list of commands now. Optionally, visit <https://tw.miningmark48.xyz/projects/tidalbot/>.").queue();
            }
        }

        ArrayList<ICommandInfo> commandsNormal = new ArrayList<>();
        ArrayList<ICommandInfo> commandsPBC = new ArrayList<>();
        ArrayList<ICommandInfo> commandsMusic = new ArrayList<>();
        ArrayList<ICommandInfo> commandsOwner = new ArrayList<>();
        ArrayList<String> commandsOther = new ArrayList<>();

        Main.commands.forEach((key, value) -> {
            if (value instanceof ICommandInfo) {
                ICommandInfo cmd = (ICommandInfo) value;
                if (cmd.getName().equalsIgnoreCase(key)) {
                    switch (cmd.getType()) {
                        default:
                            commandsOther.add(cmd.getName());
                            break;
                        case NORMAL:
                            commandsNormal.add(cmd);
                            break;
                        case BC:
                            commandsPBC.add(cmd);
                            break;
                        case MUSIC:
                            commandsMusic.add(cmd);
                            break;
                        case OWNER:
                            commandsOwner.add(cmd);
                    }
                }
            } else {
                commandsOther.add(key);
            }
        });

        int chunkSize = 50;
        int msgDelay = 1000;
        privateChannel.queue(chan -> {
            chan.sendMessage(FormatUtil.formatText(FormatUtil.FormatType.BOLD,"Commands:")).queue();
            List<List<ICommandInfo>> partitionNormal = Lists.partition(commandsNormal, chunkSize);
            List<List<ICommandInfo>> partitionPBC = Lists.partition(commandsPBC, chunkSize);
            List<List<ICommandInfo>> partitionMusic = Lists.partition(commandsMusic, chunkSize);
            List<List<ICommandInfo>> partitionOwner = Lists.partition(commandsOwner, chunkSize);
            List<List<String>> partitionOther = Lists.partition(commandsOther, chunkSize);

            partitionNormal.forEach(cmdChunk -> {
                EmbedBuilder builder = getTemplateBuilder("Normal Commands", partitionNormal.indexOf(cmdChunk), partitionNormal.size());
                cmdChunk.forEach(q -> addToEmbed(builder, q.getName()));
                chan.sendMessage(builder.build()).queueAfter(msgDelay, TimeUnit.MILLISECONDS);
            });
            partitionPBC.forEach(cmdChunk -> {
                EmbedBuilder builder = getTemplateBuilder("BC Commands", partitionPBC.indexOf(cmdChunk), partitionPBC.size());
                cmdChunk.forEach(q -> addToEmbed(builder, q.getName()));
                chan.sendMessage(builder.build()).queueAfter(msgDelay + 250, TimeUnit.MILLISECONDS);
            });
            partitionMusic.forEach(cmdChunk -> {
                EmbedBuilder builder = getTemplateBuilder("Music Commands", partitionMusic.indexOf(cmdChunk), partitionMusic.size());
                cmdChunk.forEach(q -> addToEmbed(builder, q.getName()));
                chan.sendMessage(builder.build()).queueAfter(msgDelay + 500, TimeUnit.MILLISECONDS);
            });
            partitionOwner.forEach(cmdChunk -> {
                EmbedBuilder builder = getTemplateBuilder("Owner Commands", partitionOwner.indexOf(cmdChunk), partitionMusic.size());
                cmdChunk.forEach(q -> addToEmbed(builder, q.getName()));
                chan.sendMessage(builder.build()).queueAfter(msgDelay + 500, TimeUnit.MILLISECONDS);
            });
            partitionOther.forEach(cmdChunk -> {
                EmbedBuilder builder = getTemplateBuilder("Other Commands", partitionOther.indexOf(cmdChunk), partitionOther.size());
                cmdChunk.forEach(q -> addToEmbedNoDesc(builder, q));
                chan.sendMessage(builder.build()).queueAfter(msgDelay + 750, TimeUnit.MILLISECONDS);
            });
            chan.sendMessage("Use `" + Reference.botCommandKey + "help <command_name>` for command descriptions and usage.").queueAfter(msgDelay + 1000, TimeUnit.MILLISECONDS);
        });
    }

    private static void addToEmbed(EmbedBuilder embed, String cmdName) {
        embed.addField(cmdName, "", true);
    }

    private static void addToEmbedNoDesc(EmbedBuilder embed, String cmdName) {
        embed.addField(cmdName, "Missing Description!", false);
    }

    private static EmbedBuilder getTemplateBuilder(String title, int chunkNum, int chunkTotal) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#a2f000"));
        builder.setTitle(FormatUtil.formatText(FormatUtil.FormatType.UNDERLINE, title));
        builder.setFooter(chunkNum+1 + "/" + chunkTotal, null);
        return builder;
    }

}
