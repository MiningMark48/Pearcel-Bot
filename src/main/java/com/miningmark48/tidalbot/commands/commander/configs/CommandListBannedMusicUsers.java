package com.miningmark48.tidalbot.commands.commander.configs;

import com.google.gson.JsonArray;
import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class CommandListBannedMusicUsers implements ICommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        JsonArray musicUsers = ServerConfigHandler.getBannedMusicUsers(event);
        ArrayList<String> users = new ArrayList<>();

        if (!musicUsers.isJsonNull()) {
            musicUsers.forEach(q -> users.add(q.getAsString()));
            StringBuilder builder = new StringBuilder();
            users.forEach(q -> builder.append(event.getGuild().getMembers().stream().filter(q2 -> q2.getUser().getId().equalsIgnoreCase(q)).findFirst().get().getUser().getName() + "\n"));

            event.getTextChannel().sendMessage("Banned Music Users: \n```" + builder.toString() + "```").queue();
        } else {
            event.getTextChannel().sendMessage("Server has no users banned for using music.").queue();
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.MANAGER;
    }

    @Override
    public String getName() {
        return "listbannedmusic";
    }

    @Override
    public String getDesc() {
        return "Shows all users banned for using music on the server.";
    }

    @Override
    public String getUsage() {
        return "listbannedmusic";
    }

}
