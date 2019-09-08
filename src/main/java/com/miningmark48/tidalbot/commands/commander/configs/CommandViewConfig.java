package com.miningmark48.tidalbot.commands.commander.configs;

import com.google.gson.JsonArray;
import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

public class CommandViewConfig implements ICommand {

    private static String[] types = {"banned_music"};

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
            case "banned_music":
                sendBMMessage(event);
                break;
        }

    }

    private static void sendArgsMessage(MessageReceivedEvent event) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(types).forEach(q -> builder.append(q).append("\n"));
        event.getTextChannel().sendMessage("Invalid args! Please specify a type. Available types: \n```" + builder.toString() + "```").queue();
    }

    private static void sendBMMessage(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage(listBuilder(event, ServerConfigHandler.getBannedMusicUsers(event), "Current Banned Music Users:")).queue();
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
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.MANAGER;
    }
}
