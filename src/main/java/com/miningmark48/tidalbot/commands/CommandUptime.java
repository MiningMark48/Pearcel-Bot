package com.miningmark48.tidalbot.commands;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.commands.base.ICommandPrivate;
import com.miningmark48.tidalbot.util.features.Clock;
import com.miningmark48.tidalbot.util.MessageUtil;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandUptime implements ICommand, ICommandPrivate, ICommandInfo {

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

        MessageUtil.sendMessage(event, builder.build(), isPrivate).queue();
    }

    @Override
    public String getName() {
        return "uptime";
    }

    @Override
    public String getDesc() {
        return "View the bot's uptime.";
    }

    @Override
    public String getUsage() {
        return "uptime";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
