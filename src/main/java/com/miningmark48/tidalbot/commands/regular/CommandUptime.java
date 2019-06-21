package com.miningmark48.tidalbot.commands.regular;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.base.ICommandPrivate;
import com.miningmark48.tidalbot.util.MessageUtil;
import com.miningmark48.tidalbot.util.features.Clock;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandUptime implements ICommand, ICommandPrivate {

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
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }
}
