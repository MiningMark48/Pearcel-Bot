package com.miningmark48.tidalbot;

import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandPrivate;
import com.miningmark48.tidalbot.commands.base.InitializeCommands;
import com.miningmark48.tidalbot.commands.music.soundboard.AudioHandlerSoundboard;
import com.miningmark48.tidalbot.customcommands.GetCommand;
import com.miningmark48.tidalbot.messages.InitializeMessages;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.richpresence.PresenceClock;
import com.miningmark48.tidalbot.util.CmdParserUtil;
import com.miningmark48.tidalbot.util.CommandUtil;
import com.miningmark48.tidalbot.util.ConfigUtil;
import com.miningmark48.tidalbot.util.LoggerUtil;
import com.miningmark48.tidalbot.util.features.Clock;
import com.miningmark48.tidalbot.util.features.music.handler.AudioHandler;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.HashMap;

public class Main {

    public static JDA jda;
    static final CmdParserUtil parser = new CmdParserUtil();

    public static HashMap<String, ICommand> commands = new HashMap<>();

    public static void main(String[] args) {
        setupAndConnectBot();
    }

    public static void setupAndConnectBot() {
        if (!ConfigUtil.getConfigs()) {
            return;
        }

        try {
            jda = new JDABuilder(AccountType.BOT).addEventListener(new BotListener()).setToken(Reference.botToken).buildBlocking();
            jda.setAutoReconnect(true);
            jda.getPresence().setGame(Game.of(Game.GameType.DEFAULT, String.format("Do %scmds", Reference.botCommandKey)));
            PresenceClock.runClockGame(jda);
            Clock.runClockUptime();

            ServerConfigHandler.setupConfig();

            LoggerUtil.log(LoggerUtil.LogType.STATUS, "Bot started!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        AudioSourceManagers.registerRemoteSources(AudioHandler.playerManager);
        AudioSourceManagers.registerLocalSource(AudioHandler.playerManager);
        AudioSourceManagers.registerRemoteSources(AudioHandlerSoundboard.playerManager);
        AudioSourceManagers.registerLocalSource(AudioHandlerSoundboard.playerManager);

        InitializeCommands.init();

        CommandUtil.createCommandFile();

    }

    static void handleCommand(CmdParserUtil.CommandContainer cmd) {
        if (commands.containsKey(cmd.invoke)) {
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);

            if (safe){
                if (cmd.event.getChannelType() == ChannelType.PRIVATE) {
                    if (commands.get(cmd.invoke) instanceof ICommandPrivate) {
                        ((ICommandPrivate) commands.get(cmd.invoke)).actionPrivate(cmd.args, cmd.event);
                    }
                } else {

                    if (isCommandBlacklisted(cmd.invoke, cmd.event)) {
                        cmd.event.getTextChannel().sendMessage(cmd.event.getAuthor().getName() + ", That command has been disabled.").queue();
                        return;
                    }

                    if (commands.get(cmd.invoke).isRestricted()) {
                        if (ServerConfigHandler.isBotCommander(cmd.event, cmd.event.getAuthor().getId()) || cmd.event.getGuild().getOwner() == cmd.event.getMember() || cmd.event.getAuthor().getId().equals(Reference.botOwner)) {
                            commands.get(cmd.invoke).action(cmd.args, cmd.event);
                        } else {
                            cmd.event.getTextChannel().sendMessage(cmd.event.getAuthor().getAsMention() + ", You do not have permission to run that command.").queue();
                        }
                    } else {
                        commands.get(cmd.invoke).action(cmd.args, cmd.event);
                    }
                }
                commands.get(cmd.invoke).executed(safe, cmd.event);
            } else {
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }

        }
    }

    static void handleMessage(MessageReceivedEvent event){
        InitializeMessages.init(event);
    }

    static void handleCustom(MessageReceivedEvent event){
        GetCommand.init(event);
    }

    private static boolean isCommandBlacklisted(String command, MessageReceivedEvent event) {
        return ServerConfigHandler.isCommandBlacklisted(event, command);
    }

}
