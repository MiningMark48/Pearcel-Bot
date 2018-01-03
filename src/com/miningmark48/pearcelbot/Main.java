package com.miningmark48.pearcelbot;

import com.miningmark48.pearcelbot.commands.ICommand;
import com.miningmark48.pearcelbot.commands.ICommandPrivate;
import com.miningmark48.pearcelbot.commands.InitializeCommands;
import com.miningmark48.pearcelbot.customcommands.GetCommand;
import com.miningmark48.pearcelbot.messages.InitializeMessages;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.Clock;
import com.miningmark48.pearcelbot.util.CommandParser;
import com.miningmark48.pearcelbot.util.JSON.JSONRead;
import com.miningmark48.pearcelbot.util.JSON.JSONWrite;
import com.miningmark48.pearcelbot.util.logging.Logger;
import com.miningmark48.pearcelbot.util.logging.LogType;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.HashMap;

public class Main {

    public static JDA jda;
    static final CommandParser parser = new CommandParser();

    public static HashMap<String, ICommand> commands = new HashMap<>();

    public static void main(String[] args){

        JSONWrite.init();
        JSONRead.init();

        try {
            jda = new JDABuilder(AccountType.BOT).addEventListener(new BotListener()).setToken(Reference.botToken).buildBlocking();
            jda.setAutoReconnect(true);
            jda.getPresence().setGame(Game.of(String.format("Do %scmds", Reference.botCommandKey)));
            Clock.runClockGame(jda);
            Clock.runClockUptime();

            Logger.log(LogType.INFO, "Bot started!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        InitializeCommands.init();

    }

    static void handleCommand(CommandParser.CommandContainer cmd){
        if (commands.containsKey(cmd.invoke)) {
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);

            if (safe){
                if (cmd.event.getChannelType() == ChannelType.PRIVATE) {
                    if (commands.get(cmd.invoke) instanceof ICommandPrivate) {
                        ((ICommandPrivate) commands.get(cmd.invoke)).actionPrivate(cmd.args, cmd.event);
                    }
                } else {
                    commands.get(cmd.invoke).action(cmd.args, cmd.event);
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
