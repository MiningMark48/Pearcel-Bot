package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Random;

public class CommandRPS implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length != 0) {
            if (args[0].length() == 1 && (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("p") || args[0].equalsIgnoreCase("s"))) {
                Random rand = new Random();
                int num = rand.nextInt(3);

                MessageBuilder builder = new MessageBuilder();
                builder.append("__**Rock, Paper, Scissors!**__\n\n");

                boolean botWin = false;
                boolean tie = false;
                String playerPick;
                String botPick;

                switch (num) {
                    default:
                    case 0: //R
                        botPick = "Rock";
                        if (args[0].equalsIgnoreCase("r")) {
                            tie = true;
                        } else if (args[0].equalsIgnoreCase("s")) {
                            botWin = true;
                        }
                        break;
                    case 1: //P
                        botPick = "Paper";
                        if (args[0].equalsIgnoreCase("p")) {
                            tie = true;
                        } else if (args[0].equalsIgnoreCase("r")) {
                            botWin = true;
                        }
                        break;
                    case 2: //S
                        botPick = "Scissors";
                        if (args[0].equalsIgnoreCase("s")) {
                            tie = true;
                        } else if (args[0].equalsIgnoreCase("p")) {
                            botWin = true;
                        }
                        break;
                }

                if (args[0].equalsIgnoreCase("r")) {
                    playerPick = "Rock";
                } else if (args[0].equalsIgnoreCase("p")) {
                    playerPick = "Paper";
                } else {
                    playerPick = "Scissors";
                }

                if (botWin) {
                    builder.append("**" + botPick + "** beats **" + playerPick + "**! " + Reference.botName + " wins!");
                } else if (tie) {
                    builder.append("Both of you chose **" + playerPick + "**! It was a tie!");
                } else {
                    builder.append("**" + playerPick + "** beats **" + botPick + "**! " + event.getAuthor().getName() + " wins!");
                }

                event.getTextChannel().sendMessage(builder.build()).queue();

            } else {
                event.getTextChannel().sendMessage("Invalid args! Say **R**, **P**, or **S** after the command.").queue();
            }
        } else {
            event.getTextChannel().sendMessage("Missing args! Say **R**, **P**, or **S** after the command.").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "rps";
    }

    @Override
    public String getDesc() {
        return "Play the classic Rock, Paper, Scissors with " + Reference.botName;
    }

    @Override
    public String getUsage() {
        return "rps <arg:r|p|s>";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
