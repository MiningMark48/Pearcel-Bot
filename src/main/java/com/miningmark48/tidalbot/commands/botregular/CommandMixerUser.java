package com.miningmark48.tidalbot.commands.botregular;

import com.google.gson.JsonObject;
import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.commands.base.ICommandPrivate;
import com.miningmark48.tidalbot.util.JSON.JSONParse;
import com.miningmark48.tidalbot.util.MessageUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandMixerUser implements ICommand, ICommandPrivate, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        doCmd(event, args, false);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public void actionPrivate(String[] args, MessageReceivedEvent event) {
        doCmd(event, args, true);
    }

    private static void doCmd(MessageReceivedEvent event, String[] args, boolean isPrivate) {
        JsonObject js;

        if (args.length <= 0){
            MessageUtil.sendMessage(event, "Missing Argument!", isPrivate).queue();
        } else {
            try {
                js = JSONParse.JSONParse("https://mixer.com/api/v1/channels/" + args[0]);

                JsonObject typeObject = null;

                JsonObject userObject = js.get("user").getAsJsonObject();
                if (!js.get("type").isJsonNull()) typeObject = js.get("type").getAsJsonObject();

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(Color.decode("#20BAED"));
                embedBuilder.setFooter("Completed using the Mixer API", "https://i.imgur.com/iznLTir.png");

                if (!userObject.get("username").isJsonNull()){
                    if (!userObject.get("avatarUrl").isJsonNull()) {
                        embedBuilder.setAuthor(userObject.get("username").getAsString(), "http://mixer.com/" + userObject.get("username").getAsString(), userObject.get("avatarUrl").getAsString());
                        embedBuilder.setThumbnail(userObject.get("avatarUrl").getAsString());
                    }else{
                        embedBuilder.setAuthor(userObject.get("username").getAsString(), "http://mixer.com/" + userObject.get("username").getAsString(), "https://i.imgur.com/iznLTir.png");
                    }
                }

                if (!js.get("token").isJsonNull()){
                    embedBuilder.addField("Stream Title", js.get("token").getAsString(), false);
                }

                if (typeObject != null && !typeObject.get("name").isJsonNull()){
                    embedBuilder.addField("Game", typeObject.get("name").getAsString(), false);
                }

                if (!js.get("online").isJsonNull()){
                    embedBuilder.addField("Is Online?", js.get("online").getAsBoolean() ? "Yes" : "No", true);
                }

                if (!js.get("partnered").isJsonNull()){
                    embedBuilder.addField("Is Partnered?", js.get("partnered").getAsBoolean() ? "Yes" : "No", true);
                }

                if (!js.get("audience").isJsonNull()){
                    embedBuilder.addField("Audience", js.get("audience").getAsString().substring(0, 1).toUpperCase() + js.get("audience").getAsString().substring(1), true);
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

                if (!js.get("numFollowers").isJsonNull()){
                    embedBuilder.addField("Followers", js.get("numFollowers").getAsString(), true);
                }

                if (!js.get("viewersTotal").isJsonNull()){
                    embedBuilder.addField("Total Viewers", js.get("viewersTotal").getAsString(), true);
                }

                if (!js.get("viewersCurrent").isJsonNull()){
                    embedBuilder.addField("Current Viewers", js.get("viewersCurrent").getAsString(), true);
                }

                if (!js.get("interactive").isJsonNull()){
                    embedBuilder.addField("Is Interactive?", js.get("interactive").getAsBoolean() ? "Yes" : "No", true);
                }

                if (!js.get("vodsEnabled").isJsonNull()){
                    embedBuilder.addField("Vods Enabled?", js.get("vodsEnabled").getAsBoolean() ? "Yes" : "No", true);
                }

                if (!userObject.get("bio").isJsonNull() && !userObject.get("bio").getAsString().equalsIgnoreCase("")){
                    embedBuilder.addField("Bio", userObject.get("bio").getAsString(), false);
                }

                MessageUtil.sendMessage(event, embedBuilder.build(), isPrivate).queue();

            }catch (NullPointerException e){
                MessageUtil.sendMessage(event, "Error: Could not retrieve user data.", isPrivate).queue();
                e.printStackTrace();
            }

        }
    }

    @Override
    public String getName() {
        return "mixeruser";
    }

    @Override
    public String getDesc() {
        return "Get information about a user on Mixer.";
    }

    @Override
    public String getUsage() {
        return "mixeruser <arg:string>";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
