package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.Clock;
import com.miningmark48.pearcelbot.util.MessageHelper;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandUptime implements ICommand, ICommandPrivate {

    public static final String desc = "View the bot's uptime.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "uptime";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        doCmd(event, false);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public void actionPrivate(String[] args, MessageReceivedEvent event) {
        doCmd(event, true);
    }

    private static void doCmd(MessageReceivedEvent event, boolean isPrivate) {
        int seconds = Clock.uptimeSeconds;
        int minutes = Clock.uptimeMinutes;
        int hours = Clock.uptimeHours;
        int days = Clock.uptimeDays;

        MessageBuilder builder = new MessageBuilder();

        builder.append(String.format("\uD83D\uDD50 `%sd:%sh:%sm:%ss`\t", days, hours, minutes, seconds));

        builder.append("**" + event.getJDA().getSelfUser().getName() + "** has been running for ");

        if (days != 0){
            builder.append("**" + days + "**" + (days == 1 ? " day, " : " days, "));
        }
        if (hours != 0){
            builder.append("**" + hours + "**" + (hours == 1 ? " hour, " : " hours, "));
        }
        if (minutes != 0){
            builder.append("**" + minutes + "**" + (minutes == 1 ? " minute, " : " minutes, "));
        }
        if (seconds != 0) {
            if (minutes != 0) {
                builder.append(" and ");
            }
            builder.append("**" + seconds + "**" + (seconds == 1 ? " second." : " seconds."));
        }

        MessageHelper.sendMessage(event, builder.build(), isPrivate);
    }

}
