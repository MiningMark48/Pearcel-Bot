package com.miningmark48.tidalbot.commands.custom;

import com.google.gson.JsonObject;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.JSON.JSONParseFile;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetCommand {

    public static void init(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase();

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
        HashMap<String, String> aliases = new HashMap<>();

        aliases.put("bot", event.getJDA().getSelfUser().getName());
        aliases.put("channel", event.getTextChannel().getName());
        aliases.put("channel_topic", event.getTextChannel().getTopic());
        aliases.put("client_id", Reference.botClientID);
        aliases.put("command_key", Reference.botCommandKey);
        aliases.put("custom_command_key", Reference.botCommandCustomKey);
        aliases.put("activity", Objects.requireNonNull(event.getJDA().getPresence().getActivity()).getName());
        aliases.put("guild", event.getGuild().getName());
        aliases.put("guild_owner", Objects.requireNonNull(event.getGuild().getOwner()).getUser().getName());
        aliases.put("status", event.getJDA().getPresence().getStatus().getKey());

        for (Map.Entry<String, String> entry : aliases.entrySet()) {
            commandResponse = commandResponse.replace(String.format("{%s}", entry.getKey()), entry.getValue());
        }

        commandResponse = aliasRand(commandResponse);

        return commandResponse;

    }

    @SuppressWarnings("Duplicates")
    private static String aliasRand(String message){
        Random rand = new Random();
        Pattern regex = Pattern.compile("\\{(rand:)(.*[0-9])}");
        Matcher matcher = regex.matcher(message);

        while (matcher.find()){
            if (matcher.group().length() != 0){
                try{
                    message = message.replaceAll(regex.pattern(), String.valueOf(rand.nextInt(Integer.parseInt(matcher.group(2)))));
                }catch (NumberFormatException e){
                    message = "**ERROR:** NumberFormatException";
                }
            }
        }

        return message;
    }

}
