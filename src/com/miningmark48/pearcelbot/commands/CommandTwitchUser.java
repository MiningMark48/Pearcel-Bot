package com.miningmark48.pearcelbot.commands;

import com.google.gson.JsonObject;
import com.miningmark48.pearcelbot.ICommand;
import com.miningmark48.pearcelbot.ICommandPrivate;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.JSON.JSONParse;
import com.miningmark48.pearcelbot.util.MessageHelper;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.DecimalFormat;

public class CommandTwitchUser implements ICommand, ICommandPrivate {

    public static final String desc = "Get information about a user on Twitch.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "twitchuser <arg>";
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
        JsonObject js2;

        if (args.length <= 0){
            MessageHelper.sendMessage(event, "Missing Argument!", isPrivate);
        }else{
            try {
                js = JSONParse.JSONParseTwitch("https://api.twitch.tv/kraken/channels/" + args[0] + "?callback=foo&client_id=7fr372mwgdks9l4zb0ba2z0najhh84");
                js2 = JSONParse.JSONParseTwitch("https://api.twitch.tv/kraken/users/" + args[0] + "?callback=foo&client_id=7fr372mwgdks9l4zb0ba2z0najhh84");

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(Color.decode("#6441A4"));
                embedBuilder.setFooter("Completed using the Twitch API", "http://i.imgur.com/8vMXQyu.png");

                if (!js.get("logo").isJsonNull()) {
                    embedBuilder.setThumbnail(js.get("logo").getAsString());
                    embedBuilder.setAuthor(js.get("display_name").getAsString(), "http://www.twitch.tv/users/" + args[0], js.get("logo").getAsString());
                } else {
                    embedBuilder.setAuthor(js.get("display_name").getAsString(), "http://www.twitch.tv/users/" + args[0], null);
                }

                if (!js.get("name").isJsonNull()) {
                    embedBuilder.addField("Name", js.get("display_name").getAsString(), true);
                }
                if (!js2.get("bio").isJsonNull()) {
                    embedBuilder.addField("Bio", js2.get("bio").getAsString(), false);
                }
                if (!js2.get("created_at").isJsonNull()) {
                    embedBuilder.addField("Joined", js2.get("created_at").getAsString().substring(0, 10), true);
                }
                if (!js.get("followers").isJsonNull()) {
                    int amount = Integer.parseInt(js.get("followers").getAsString());
                    DecimalFormat format = new DecimalFormat("#,###");
                    embedBuilder.addField("Followers", format.format(amount), true);
                }
                if (!js.get("views").isJsonNull()) {
                    int amount = Integer.parseInt(js.get("views").getAsString());
                    DecimalFormat format = new DecimalFormat("#,###");
                    embedBuilder.addField("Views", format.format(amount), true);
                }
                if (!js.get("mature").isJsonNull()) {
                    embedBuilder.addField("Mature?", js.get("mature").getAsBoolean() ? "Yes" : "No", true);
                }
                if (!js.get("partner").isJsonNull()) {
                    embedBuilder.addField("Partnered?", js.get("partner").getAsBoolean() ? "Yes" : "No", true);
                }

                MessageHelper.sendMessage(event, embedBuilder.build(), isPrivate);

            }catch (NullPointerException e){
                MessageHelper.sendMessage(event, "Error: Could not retrieve user data.", isPrivate);
            }

        }
    }

}
