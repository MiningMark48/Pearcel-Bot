package com.miningmark48.tidalbot;

import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.base.ICommandPrivate;
import com.miningmark48.tidalbot.base.InitializeCommands;
import com.miningmark48.tidalbot.commands.custom.GetCommand;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.richpresence.PresenceClock;
import com.miningmark48.tidalbot.util.UtilCommandParser;
import com.miningmark48.tidalbot.util.UtilCommandFile;
import com.miningmark48.tidalbot.util.UtilConfig;
import com.miningmark48.tidalbot.util.UtilLogger;
import com.miningmark48.tidalbot.util.features.Clock;
import com.miningmark48.tidalbot.util.features.music.handler.AudioHandler;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Objects;

public class Main {

    public static JDA jda;
    static final UtilCommandParser parser = new UtilCommandParser();

    public static HashMap<String, ICommand> commands = new HashMap<>();

    public static void main(String[] args) {
        setupAndConnectBot();
    }

    public static void setupAndConnectBot() {
        if (!UtilConfig.getConfigs()) {
            return;
        }

        try {
            jda = new JDABuilder(AccountType.BOT).addEventListeners(new BotListener()).setToken(Reference.botToken).build().awaitReady();
            jda.setAutoReconnect(true);
            jda.getPresence().setActivity(Activity.of(Activity.ActivityType.DEFAULT, String.format("Do %scmds", Reference.botCommandKey)));
            PresenceClock.runClockGame(jda);
            Clock.runClockUptime();

            ServerConfigHandler.setupConfig();

            UtilLogger.log(UtilLogger.LogType.STATUS, "Bot started!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        AudioSourceManagers.registerRemoteSources(AudioHandler.playerManager);
        AudioSourceManagers.registerLocalSource(AudioHandler.playerManager);
//        AudioSourceManagers.registerRemoteSources(AudioHandlerSoundboard.playerManager);
//        AudioSourceManagers.registerLocalSource(AudioHandlerSoundboard.playerManager);

        InitializeCommands.init();

        UtilCommandFile.createCommandFile();

    }

    static void handleCommand(UtilCommandParser.CommandContainer cmd) {
        if (commands.containsKey(cmd.invoke)) {
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);

            if (safe){
                if (cmd.event.getChannelType().equals(ChannelType.PRIVATE)) {
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

//                    cmd.event.getTextChannel().sendMessage(cmd.event.getAuthor().getAsMention() + ", sorry, but you do not have permission to run that command. If you believe this is a mistake, contact a server admin.").queue();

                }
                commands.get(cmd.invoke).executed(safe, cmd.event);
            } else {
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }

        }
    }

    static void handleCustom(MessageReceivedEvent event){
        GetCommand.init(event);
    }

    private static boolean isCommandBlacklisted(String command, MessageReceivedEvent event) {
        return ServerConfigHandler.isCommandBlacklisted(event, command);
    }

}
