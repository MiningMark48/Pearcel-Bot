package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandToBinary implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        int arg = Integer.parseInt(args[0]);
        String binaryNum1 = Integer.toBinaryString(arg);
        String binaryNum2;

        switch (binaryNum1.length()){
            case 1:
                binaryNum2 = "000" + binaryNum1;
                break;
            case 2:
                binaryNum2 = "00" + binaryNum1;
                break;
            case 3:
                binaryNum2 = "0" + binaryNum1;
                break;
            default:
                binaryNum2 = binaryNum1;
                break;
        }

        event.getTextChannel().sendMessage(binaryNum2).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "tobinary";
    }

    @Override
    public String getDesc() {
        return "Convert a number to binary.";
    }

    @Override
    public String getUsage() {
        return "tobinary <arg:int>";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }

}
