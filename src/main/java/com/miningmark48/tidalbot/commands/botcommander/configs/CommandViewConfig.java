package com.miningmark48.tidalbot.commands.botcommander.configs;

import com.google.gson.JsonArray;
import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Arrays;

public class CommandViewConfig implements ICommand, ICommandInfo {

    private static String[] types = {"bot_commanders", "banned_music", "ar_blacklist"};

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (args.length <= 0) {
            sendArgsMessage(event);
            return;
        }

        switch (args[0]) {
            default:
                sendArgsMessage(event);
                break;
            case "bot_commanders":
                sendBCMessage(event);
                break;
            case "banned_music":
                sendBMMessage(event);
                break;
            case "ar_blacklist":
                sendARMessage(event);
                break;
        }

    }

    private static void sendArgsMessage(MessageReceivedEvent event) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(types).forEach(q -> builder.append(q).append("\n"));
        event.getTextChannel().sendMessage("Invalid args! Please specify a type. Available types: \n```" + builder.toString() + "```").queue();
    }

    private static void sendBCMessage(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage(listBuilder(event, ServerConfigHandler.getBotCommanders(event), "Current Bot Commanders:")).queue();
    }

    private static void sendBMMessage(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage(listBuilder(event, ServerConfigHandler.getBannedMusicUsers(event), "Current Banned Music Users:")).queue();
    }

    private static void sendARMessage(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage(listBuilder(event, ServerConfigHandler.getBlacklistedARUsers(event), "Current Blacklisted AR Users:")).queue();
    }

    private static String idToName(MessageReceivedEvent event, String id) {
        if (event.getGuild().getMembers().stream().anyMatch(q -> q.getUser().getId().equalsIgnoreCase(id))) {
            return event.getGuild().getMembers().stream().filter(q -> q.getUser().getId().equalsIgnoreCase(id)).findFirst().get().getEffectiveName();
        } else {
            return null;
        }
    }

    private static String listBuilder(MessageReceivedEvent event, JsonArray array, String title) {
        StringBuilder builder = new StringBuilder();
        array.forEach(q -> builder.append(idToName(event, q.getAsString())).append("\n"));
        return "**" + title + "** \n```" + builder.toString() + "```";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "viewconfig";
    }

    @Override
    public String getDesc() {
        return "Will show the current configuration file of the server.";
    }

    @Override
    public String getUsage() {
        return "viewconfig <arg-string>";
    }

    @Override
    public CommandType getType() {
        return CommandType.BC;
    }

    @Override
    public boolean isRestricted() {
        return true;
    }
}
