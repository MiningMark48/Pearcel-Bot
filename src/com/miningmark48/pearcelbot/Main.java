package com.miningmark48.pearcelbot;

import com.miningmark48.pearcelbot.commands.InitializeCommands;
import com.miningmark48.pearcelbot.customcommands.GetCommand;
import com.miningmark48.pearcelbot.messages.InitializeMessages;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.CommandParser;
import com.miningmark48.pearcelbot.util.JSONRead;
import com.miningmark48.pearcelbot.util.JSONWrite;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
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

        try{
            jda = new JDABuilder(AccountType.BOT).addListener(new BotListener()).setToken(Reference.botToken).buildBlocking();
            jda.setAutoReconnect(true);
            jda.getPresence().setGame(Game.of("Do ~cmds"));
            //Clock.runClockGame(jda);
            //Clock.runClockUptime(jda);
        }catch (Exception e){
            e.printStackTrace();
        }

        InitializeCommands.init();

    }

    static void handleCommand(CommandParser.CommandContainer cmd){
        if(commands.containsKey(cmd.invoke)){
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);

            if(safe){
                commands.get(cmd.invoke).action(cmd.args, cmd.event);
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }else{
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