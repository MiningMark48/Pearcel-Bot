package com.miningmark48.pearcelbot.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.JSON.JSONParse;
import com.miningmark48.pearcelbot.util.MessageHelper;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.math.BigInteger;
import java.text.DecimalFormat;

public class CommandYoutubeUser implements ICommand, ICommandPrivate {

    public static final String desc = "Get information about a user on Info.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "youtubeuser <arg>";
    public static final String info = desc + " " + usage;

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

        if (args.length <= 0) {
            MessageHelper.sendMessage(event, "Missing Argument!", isPrivate);
        } else {
            try {
                js = JSONParse.JSONParse("https://www.googleapis.com/youtube/v3/channels?&key=AIzaSyBnt38rBPV1WAZGx6imcMvp0GuuQU15YKE&part=statistics,brandingSettings&forUsername=" + args[0]);
                JsonElement js2 = js.get("items");
                JsonObject jsonObject = js2.getAsJsonArray().get(0).getAsJsonObject();
                JsonObject jsonObject2 = jsonObject.get("statistics").getAsJsonObject();
                JsonObject jsonObject3 = jsonObject.get("brandingSettings").getAsJsonObject();
                JsonObject jsonObject4 = jsonObject3.get("channel").getAsJsonObject();
                JsonObject jsonObject5 = jsonObject3.get("image").getAsJsonObject();

                EmbedBuilder embedBuilder = new EmbedBuilder();

                embedBuilder.setColor(Color.decode("#e52d27"));
                embedBuilder.setFooter("Completed using the Youtube Data API", "http://i.imgur.com/vwYII9E.png");
                if (!jsonObject5.get("bannerImageUrl").isJsonNull()) {
                    embedBuilder.setThumbnail(jsonObject5.get("bannerImageUrl").getAsString());
                }
                if (!jsonObject.get("id").isJsonNull()) {
                    embedBuilder.setAuthor(jsonObject4.get("title").getAsString(), "http://www.youtube.com/channel/" + jsonObject.get("id"), "http://i.imgur.com/vwYII9E.png");
                }else{
                    embedBuilder.setAuthor(jsonObject4.get("title").getAsString(), null, "http://i.imgur.com/vwYII9E.png");
                }

                embedBuilder.addField("Name", jsonObject4.get("title").getAsString(), true);

                if (!jsonObject2.get("subscriberCount").isJsonNull()) {
                    BigInteger amount = new BigInteger(jsonObject2.get("subscriberCount").getAsString());
                    DecimalFormat format = new DecimalFormat("#,###");
                    embedBuilder.addField("Subscribers", format.format(amount), true);
                }
                if (!jsonObject2.get("viewCount").isJsonNull()) {
                    BigInteger amount = new BigInteger(jsonObject2.get("viewCount").getAsString());
                    DecimalFormat format = new DecimalFormat("#,###");
                    embedBuilder.addField("Views", format.format(amount), true);
                }
                if (!jsonObject2.get("videoCount").isJsonNull()) {
                    BigInteger amount = new BigInteger(jsonObject2.get("videoCount").getAsString());
                    DecimalFormat format = new DecimalFormat("#,###");
                    embedBuilder.addField("Videos", format.format(amount), true);
                }
                if (!jsonObject4.get("description").isJsonNull()) {
                    embedBuilder.addField("Description", jsonObject4.get("description").getAsString(), false);
                }

                MessageHelper.sendMessage(event, embedBuilder.build(), isPrivate);

            } catch (NullPointerException | IndexOutOfBoundsException e) {
                MessageHelper.sendMessage(event, "Error: Could not retrieve user data, channel may be to small for detection.", isPrivate);
            }

        }
    }

}
