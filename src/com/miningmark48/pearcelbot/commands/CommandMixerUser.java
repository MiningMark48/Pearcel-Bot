package com.miningmark48.pearcelbot.commands;

import com.google.gson.JsonObject;
import com.miningmark48.pearcelbot.ICommand;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.JSON.JSONParse;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandMixerUser implements ICommand {

    public static final String desc = "Get information about a user on Mixer.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "mixeruser <arg>";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        JsonObject js;

        if (args.length <= 0){
            event.getTextChannel().sendMessage("Missing Argument!").queue();
        }else{
            try {
                js = JSONParse.JSONParse("https://mixer.com/api/v1/channels/" + args[0]);

                JsonObject typeObject = null;

                JsonObject userObject = js.get("user").getAsJsonObject();
                JsonObject channelObject = userObject.get("channel").getAsJsonObject();
                if (!js.get("type").isJsonNull()) typeObject = js.get("type").getAsJsonObject();

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(Color.decode("#20BAED"));
                embedBuilder.setFooter("Completed using the Mixer API", "https://i.imgur.com/iznLTir.png");

                if (!userObject.get("username").isJsonNull()){
                    if (!userObject.get("avatarUrl").isJsonNull()) {
                        embedBuilder.setAuthor(userObject.get("username").getAsString(), "http://mixer.com/" + userObject.get("username").getAsString(), userObject.get("avatarUrl").getAsString());
                    }else{
                        embedBuilder.setAuthor(userObject.get("username").getAsString(), "http://mixer.com/" + userObject.get("username").getAsString(), "https://i.imgur.com/iznLTir.png");
                    }
                }

                if (!userObject.get("bio").isJsonNull() && !userObject.get("bio").getAsString().equalsIgnoreCase("")){
                    embedBuilder.addField("Bio", userObject.get("bio").getAsString(), false);
                }

                if (!channelObject.get("name").isJsonNull()){
                    embedBuilder.addField("Stream Title", channelObject.get("name").getAsString(), false);
                }

                if (typeObject != null && !typeObject.get("name").isJsonNull()){
                    embedBuilder.addField("Stream Type", typeObject.get("name").getAsString(), false);
                }

                if (!channelObject.get("online").isJsonNull()){
                    embedBuilder.addField("Is Online?", channelObject.get("online").getAsBoolean() ? "Yes" : "No", true);
                }

                if (!channelObject.get("partnered").isJsonNull()){
                    embedBuilder.addField("Is Partnered?", channelObject.get("partnered").getAsBoolean() ? "Yes" : "No", true);
                }

                if (!channelObject.get("audience").isJsonNull()){
                    embedBuilder.addField("Audience", channelObject.get("audience").getAsString().substring(0, 1).toUpperCase() + channelObject.get("audience").getAsString().substring(1), true);
                }

                if (!userObject.get("createdAt").isJsonNull()){
                    embedBuilder.addField("Member Since", userObject.get("createdAt").getAsString().substring(0, 10), true);
                }

                if (!userObject.get("updatedAt").isJsonNull()){
                    embedBuilder.addField("Last Updated", userObject.get("updatedAt").getAsString().substring(0, 10), true);
                }

                if (!userObject.get("level").isJsonNull()){
                    embedBuilder.addField("Level", userObject.get("level").getAsString(), true);
                }

                if (!userObject.get("sparks").isJsonNull()){
                    embedBuilder.addField("Sparks", userObject.get("sparks").getAsString(), true);
                }

                if (!channelObject.get("numFollowers").isJsonNull()){
                    embedBuilder.addField("Followers", channelObject.get("numFollowers").getAsString(), true);
                }

                if (!channelObject.get("viewersTotal").isJsonNull()){
                    embedBuilder.addField("Total Viewers", channelObject.get("viewersTotal").getAsString(), true);
                }

                if (!channelObject.get("viewersCurrent").isJsonNull()){
                    embedBuilder.addField("Current Viewers", channelObject.get("viewersCurrent").getAsString(), true);
                }

                if (!channelObject.get("interactive").isJsonNull()){
                    embedBuilder.addField("Is Interactive?", channelObject.get("interactive").getAsBoolean() ? "Yes" : "No", true);
                }

                if (!channelObject.get("vodsEnabled").isJsonNull()){
                    embedBuilder.addField("Vods Enabled?", channelObject.get("vodsEnabled").getAsBoolean() ? "Yes" : "No", true);
                }


                event.getTextChannel().sendMessage(embedBuilder.build()).queue();

            }catch (NullPointerException e){
                event.getTextChannel().sendMessage("Error: Could not retrieve user data.").queue();
                e.printStackTrace();
            }

        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
