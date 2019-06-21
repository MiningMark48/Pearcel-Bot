package com.miningmark48.tidalbot.commands.regular;

import com.google.common.collect.Lists;
import com.miningmark48.tidalbot.Main;
import com.miningmark48.tidalbot.base.CommandType;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.base.ICommandPrivate;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.FormatUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.RestAction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandCmds implements ICommand, ICommandPrivate {

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
        return getName();
    }

    private static void getCmds(MessageReceivedEvent event, boolean isPrivate) {
        if (event.getAuthor() == null) {
            return;
        }

        RestAction<PrivateChannel> privateChannel = event.getAuthor().openPrivateChannel();
        if (!isPrivate) {
            if (event.getAuthor() == null) {
                event.getTextChannel().sendMessage("Sending you a list of commands now. Optionally, visit <https://miningmark48.github.io/tidalwave/projects/tidalbot/>.").queue();
            } else {
                event.getTextChannel().sendMessage("**" + event.getAuthor().getAsMention() + "**, Sending you a list of commands now. Optionally, visit <https://miningmark48.github.io/tidalwave/projects/tidalbot/>.").queue();
            }
        }

        ArrayList<ICommand> commandsRegular = new ArrayList<>();
        ArrayList<ICommand> commandsManager = new ArrayList<>();
        ArrayList<ICommand> commandsAdmin = new ArrayList<>();
        ArrayList<ICommand> commandsOwner = new ArrayList<>();
        ArrayList<ICommand> commandsBotOwner = new ArrayList<>();
        ArrayList<ICommand> commandsMusic = new ArrayList<>();
        ArrayList<String> commandsOther = new ArrayList<>();

        Main.commands.forEach((key, value) -> {
            if (value != null) {
                ICommand cmd = value;
                if (cmd.getName().equalsIgnoreCase(key)) {
                    if (cmd.isMusic()) {
                        commandsMusic.add(cmd);
                        return;
                    }
                    switch (cmd.getPermissionRequired()) {
                        default:
                            commandsOther.add(cmd.getName());
                            break;
                        case REGULAR:
                            commandsRegular.add(cmd);
                            break;
                        case MANAGER:
                            commandsManager.add(cmd);
                            break;
                        case ADMIN:
                            commandsAdmin.add(cmd);
                            break;
                        case OWNER:
                            commandsOwner.add(cmd);
                            break;
                        case BOT_OWNER:
                            commandsBotOwner.add(cmd);
                            break;
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
            List<List<ICommand>> partitionNormal = Lists.partition(commandsRegular, chunkSize);
            List<List<ICommand>> partitionManager = Lists.partition(commandsManager, chunkSize);
            List<List<ICommand>> partitionAdmin = Lists.partition(commandsAdmin, chunkSize);
            List<List<ICommand>> partitionOwner = Lists.partition(commandsOwner, chunkSize);
            List<List<ICommand>> partitionMusic = Lists.partition(commandsMusic, chunkSize);
            List<List<String>> partitionOther = Lists.partition(commandsOther, chunkSize);

            int delay = 250;
            partitionNormal.forEach(cmdChunk -> {
                EmbedBuilder builder = getTemplateBuilder("Normal Commands", partitionNormal.indexOf(cmdChunk), partitionNormal.size());
                cmdChunk.forEach(q -> addToEmbed(builder, q.getName()));
                chan.sendMessage(builder.build()).queueAfter(msgDelay + delay, TimeUnit.MILLISECONDS);
            });
            partitionManager.forEach(cmdChunk -> {
                EmbedBuilder builder = getTemplateBuilder("Manager Commands", partitionManager.indexOf(cmdChunk), partitionManager.size());
                cmdChunk.forEach(q -> addToEmbed(builder, q.getName()));
                chan.sendMessage(builder.build()).queueAfter(msgDelay + delay * 2, TimeUnit.MILLISECONDS);
            });
            partitionAdmin.forEach(cmdChunk -> {
                EmbedBuilder builder = getTemplateBuilder("Administrator Commands", partitionAdmin.indexOf(cmdChunk), partitionAdmin.size());
                cmdChunk.forEach(q -> addToEmbed(builder, q.getName()));
                chan.sendMessage(builder.build()).queueAfter(msgDelay + delay * 3, TimeUnit.MILLISECONDS);
            });
            partitionOwner.forEach(cmdChunk -> {
                EmbedBuilder builder = getTemplateBuilder("Server Owner Commands", partitionOwner.indexOf(cmdChunk), partitionOwner.size());
                cmdChunk.forEach(q -> addToEmbed(builder, q.getName()));
                chan.sendMessage(builder.build()).queueAfter(msgDelay + delay * 4, TimeUnit.MILLISECONDS);
            });
            partitionMusic.forEach(cmdChunk -> {
                EmbedBuilder builder = getTemplateBuilder("Music Commands", partitionMusic.indexOf(cmdChunk), partitionMusic.size());
                cmdChunk.forEach(q -> addToEmbed(builder, q.getName()));
                chan.sendMessage(builder.build()).queueAfter(msgDelay + delay * 5, TimeUnit.MILLISECONDS);
            });
            partitionOther.forEach(cmdChunk -> {
                EmbedBuilder builder = getTemplateBuilder("Other Commands", partitionOther.indexOf(cmdChunk), partitionOther.size());
                cmdChunk.forEach(q -> addToEmbedNoDesc(builder, q));
                chan.sendMessage(builder.build()).queueAfter(msgDelay + delay * 6, TimeUnit.MILLISECONDS);
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
