package com.miningmark48.tidalbot;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.base.ICommandPrivate;
import com.miningmark48.tidalbot.base.InitializeCommands;
import com.miningmark48.tidalbot.commands.music.soundboard.AudioHandlerSoundboard;
import com.miningmark48.tidalbot.commands.custom.GetCommand;
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
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Objects;

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
            jda = new JDABuilder(AccountType.BOT).addEventListeners(new BotListener()).setToken(Reference.botToken).build().awaitReady();
            jda.setAutoReconnect(true);
            jda.getPresence().setActivity(Activity.of(Activity.ActivityType.DEFAULT, String.format("Do %scmds", Reference.botCommandKey)));
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
                        cmd.event.getTextChannel().sendMessage(cmd.event.getAuthor().getName() + ", sorry, but that command has been disabled. If you believe this is a mistake, contact a server admin.").queue();
                        return;
                    }

                    Guild guild = cmd.event.getGuild();
                    User author = cmd.event.getAuthor();
                    switch (commands.get(cmd.invoke).getPermissionRequired()) {
                        default:
                        case REGULAR:
                            commands.get(cmd.invoke).action(cmd.args, cmd.event);
                            break;
                        case MANAGER:
                            if (Objects.requireNonNull(guild.getMember(author)).hasPermission(Permission.MANAGE_CHANNEL)) commands.get(cmd.invoke).action(cmd.args, cmd.event);
                            break;
                        case ADMIN:
                            if (Objects.requireNonNull(guild.getMember(author)).hasPermission(Permission.ADMINISTRATOR)) commands.get(cmd.invoke).action(cmd.args, cmd.event);
                            break;
                        case OWNER:
                            if (Objects.requireNonNull(guild.getOwner()).getUser().equals(author)) commands.get(cmd.invoke).action(cmd.args, cmd.event);
                            break;
                        case SPECIFIC:
                            if (Objects.requireNonNull(guild.getMember(author)).hasPermission(commands.get(cmd.invoke).getPermissions())) commands.get(cmd.invoke).action(cmd.args, cmd.event);
                            break;
                        case BOT_OWNER:
                            if (author.getId().equals(Reference.botOwner)) commands.get(cmd.invoke).action(cmd.args, cmd.event);
                            break;
                    }

                    cmd.event.getTextChannel().sendMessage(cmd.event.getAuthor().getAsMention() + ", sorry, but you do not have permission to run that command. If you believe this is a mistake, contact a server admin.");

//                    if (commands.get(cmd.invoke).getPermissionRequired() == EnumRestrictions.ADMIN) {
//                        if (ServerConfigHandler.isBotCommander(cmd.event, cmd.event.getAuthor().getId()) || cmd.event.getGuild().getOwner() == cmd.event.getMember() || cmd.event.getAuthor().getId().equals(Reference.botOwner)) {
//                            commands.get(cmd.invoke).action(cmd.args, cmd.event);
//                        } else {
//                            cmd.event.getTextChannel().sendMessage(cmd.event.getAuthor().getAsMention() + ", You do not have permission to run that command.").queue();
//                        }
//                    } else {
//                        commands.get(cmd.invoke).action(cmd.args, cmd.event);
//                    }
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
