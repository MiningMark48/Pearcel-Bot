package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.util.LoggerUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.TreeMap;

public class CommandToRomanNum implements ICommand, ICommandInfo {

    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        int num;

        try {
            num = Integer.parseInt(args[0]);

            if (num > 0) {
                try {
                    if (num <= 4999) {
                        event.getTextChannel().sendMessage(toRoman(num)).queue();
                    }else{
                        event.getTextChannel().sendMessage("Error: Number provided is to large! Must be from 1 - 4999").queue();
                    }
                }catch (StackOverflowError e){
                    event.getTextChannel().sendMessage("Error!").queue();
                    LoggerUtil.log(LoggerUtil.LogType.FATAL, e.toString());
                }

            }else{
                event.getTextChannel().sendMessage("Error: Number provided is to small! Must be from 1 - 4999").queue();
            }

        }catch (NumberFormatException e){
            event.getTextChannel().sendMessage("Error!").queue();
        }



    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    public final static String toRoman(int number){
        int i = map.floorKey(number);
        if (number == i){
            return map.get(number);
        }
        return map.get(i) + toRoman(number - i);
    }

    @Override
    public String getName() {
        return "toromannum";
    }

    @Override
    public String getDesc() {
        return "Turn an integer to a roman numeral.";
    }

    @Override
    public String getUsage() {
        return "toromannum <arg:int>";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
