package com.miningmark48.pearcelbot.customcommands;

import com.google.gson.JsonObject;
import com.miningmark48.pearcelbot.commands.pbc.CommandAddCommand;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.JSON.JSONParseFile;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class GetCommand {

    public static void init(MessageReceivedEvent event) {
        String message = event.getMessage().getContent().toLowerCase();

        if (message.startsWith(Reference.botCommandCustomKey)) {

            String args[] = message.split(" ", 2);
            String command = args[0];
            command = command.replace(Reference.botCommandCustomKey, "");

            JsonObject jsonObj = JSONParseFile.JSONParse(CommandAddCommand.fileName);
            JsonObject jsonGuildCommands = null;
            if (jsonObj != null) {
                jsonGuildCommands = jsonObj.get(event.getGuild().getId()).getAsJsonObject();
            }

            if (jsonGuildCommands != null && jsonGuildCommands.get(command) != null) {
                String commandResponse = jsonGuildCommands.get(command).getAsString();

                event.getTextChannel().sendMessage(aliasReplace(commandResponse, event)).queue();

            }

        }
    }

    private static String aliasReplace(String commandResponse, MessageReceivedEvent event){
        commandResponse = commandResponse.replace("{bot}", event.getJDA().getSelfUser().getName());
        commandResponse = commandResponse.replace("{channel}", event.getTextChannel().getName());
        commandResponse = commandResponse.replace("{client_id}", Reference.botClientID);
        commandResponse = commandResponse.replace("{command_key}", Reference.botCommandCustomKey);
        commandResponse = commandResponse.replace("{current_game}", event.getJDA().getPresence().getGame().getName());
        commandResponse = commandResponse.replace("{game}", event.getJDA().getPresence().getGame().getName());
        commandResponse = commandResponse.replace("{guild}", event.getGuild().getName());
        commandResponse = commandResponse.replace("{key}", Reference.botCommandCustomKey);
        commandResponse = commandResponse.replace("{owner}", event.getGuild().getOwner().getUser().getName());
        commandResponse = commandResponse.replace("{role_commander}", Reference.botCommanderRole);
        commandResponse = commandResponse.replace("{role_auto_response}", Reference.botAutoResponseRole);
        commandResponse = commandResponse.replace("{server}", event.getGuild().getName());
        commandResponse = commandResponse.replace("{status}", event.getJDA().getPresence().getStatus().getKey());

        return commandResponse;

    }

}
