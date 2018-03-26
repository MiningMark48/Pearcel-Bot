package com.miningmark48.pearcelbot;

import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandPrivate;
import com.miningmark48.pearcelbot.commands.base.InitializeCommands;
import com.miningmark48.pearcelbot.commands.music.soundboard.AudioHandlerSoundboard;
import com.miningmark48.pearcelbot.customcommands.GetCommand;
import com.miningmark48.pearcelbot.messages.InitializeMessages;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.richpresence.PresenceClock;
import com.miningmark48.pearcelbot.util.CmdParserUtil;
import com.miningmark48.pearcelbot.util.ConfigUtil;
import com.miningmark48.pearcelbot.util.LoggerUtil;
import com.miningmark48.pearcelbot.util.features.Clock;
import com.miningmark48.pearcelbot.util.features.music.handler.AudioHandler;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static JDA jda;
    static final CmdParserUtil parser = new CmdParserUtil();

    public static HashMap<String, ICommand> commands = new HashMap<>();

    public static void main(String[] args) {

        if (!ConfigUtil.getConfigs()) {
            return;
        }

        try {
            jda = new JDABuilder(AccountType.BOT).addEventListener(new BotListener()).setToken(Reference.botToken).buildBlocking();
            jda.setAutoReconnect(true);
            jda.getPresence().setGame(Game.of(Game.GameType.DEFAULT, String.format("Do %scmds", Reference.botCommandKey)));
            PresenceClock.runClockGame(jda);
            Clock.runClockUptime();

            LoggerUtil.log(LoggerUtil.LogType.STATUS, "Bot started!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        AudioSourceManagers.registerRemoteSources(AudioHandler.playerManager);
        AudioSourceManagers.registerLocalSource(AudioHandler.playerManager);
        AudioSourceManagers.registerRemoteSources(AudioHandlerSoundboard.playerManager);
        AudioSourceManagers.registerLocalSource(AudioHandlerSoundboard.playerManager);

        InitializeCommands.init();

    }

    static void handleCommand(CmdParserUtil.CommandContainer cmd) throws IOException {
        if (commands.containsKey(cmd.invoke)) {
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);

            if (safe){
                if (cmd.event.getChannelType() == ChannelType.PRIVATE) {
                    if (commands.get(cmd.invoke) instanceof ICommandPrivate) {
                        ((ICommandPrivate) commands.get(cmd.invoke)).actionPrivate(cmd.args, cmd.event);
                    }
                } else {
                    if (commands.get(cmd.invoke).isRestricted()) {
                        if (cmd.event.getMember().getRoles().stream().anyMatch(q-> q.getName().equalsIgnoreCase(Reference.botCommanderRole)) || cmd.event.getGuild().getOwner() == cmd.event.getMember() || cmd.event.getAuthor().getId().equals(Reference.botOwner)) {
                            commands.get(cmd.invoke).action(cmd.args, cmd.event);
                        } else {
                            cmd.event.getTextChannel().sendMessage(cmd.event.getAuthor().getName() + ", You do not have permission to run that command.").queue();
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

}
