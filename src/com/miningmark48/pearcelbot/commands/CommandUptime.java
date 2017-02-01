package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.Command;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.Clock;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandUptime implements Command{

    public static final String desc = "View the bot's uptime.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "uptime";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

//        int seconds = Clock.uptimeSeconds;
//        int minutes = Clock.uptimeMinutes;
//        int hours = Clock.uptimeHours;
//        int days = Clock.uptimeDays;
//
//        MessageBuilder builder = new MessageBuilder();
//
//        builder.append(String.format("`%sd:%sh:%sm:%ss`\t", days, hours, minutes, seconds));
//
//        builder.append("**" + event.getJDA().getSelfUser().getName() + "** has been running for ");
//
//        if (days != 0){
//            builder.append("**" + days + "**" + (days == 1 ? " day, " : " days, "));
//        }
//        if (hours != 0){
//            builder.append("**" + hours + "**" + (hours == 1 ? " hour, " : " hours, "));
//        }
//        if (minutes != 0){
//            builder.append("**" + minutes + "**" + (minutes == 1 ? " minute, " : " minutes, "));
//        }
//        if (seconds != 0) {
//            if (minutes != 0) {
//                builder.append(" and ");
//            }
//            builder.append("**" + seconds + "**" + (seconds == 1 ? " second." : " seconds."));
//        }

//        event.getTextChannel().sendMessage(builder.build()).queue();

        event.getTextChannel().sendMessage("Currently disabled.").queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
