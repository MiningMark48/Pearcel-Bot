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

public class CommandGithubUser implements ICommand, ICommandPrivate, ICommandInfo {

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
                js = JSONParse.JSONParse("https://api.github.com/users/" + args[0]);

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(Color.decode("#6cc644"));
                embedBuilder.setFooter("Completed using the Github API", "http://i.imgur.com/nrqDJDf.png");

                if (!js.get("avatar_url").isJsonNull()) {
                    embedBuilder.setThumbnail(js.get("avatar_url").getAsString());
                    embedBuilder.setAuthor(js.get("login").getAsString(), "http://www.github.com/" + args[0], js.get("avatar_url").getAsString());
                } else {
                    embedBuilder.setAuthor(js.get("login").getAsString(), "http://www.github.com/" + args[0], null);
                }

                if (!js.get("name").isJsonNull()) {
                    embedBuilder.addField("Name", js.get("name").getAsString(), true);
                }
                if (!js.get("company").isJsonNull()) {
                    embedBuilder.addField("Company", js.get("company").getAsString(), true);
                }
                if (!js.get("location").isJsonNull()) {
                    embedBuilder.addField("Location", js.get("location").getAsString(), true);
                }
                if (!js.get("email").isJsonNull()) {
                    embedBuilder.addField("Email", js.get("email").getAsString(), true);
                }
                if (!js.get("bio").isJsonNull()) {
                    embedBuilder.addField("Bio", js.get("bio").getAsString(), false);
                }
                if (!js.get("followers").isJsonNull()) {
                    embedBuilder.addField("Followers", js.get("followers").getAsString(), true);
                }
                if (!js.get("following").isJsonNull()) {
                    embedBuilder.addField("Following", js.get("following").getAsString(), true);
                }
                if (!js.get("created_at").isJsonNull()) {
                    embedBuilder.addField("Joined", js.get("created_at").getAsString().substring(0, 10), true);
                }

               MessageUtil.sendMessage(event, embedBuilder.build(), isPrivate).queue();

            } catch (NullPointerException e) {
                MessageUtil.sendMessage(event, "Error: Could not retrieve user data.", isPrivate).queue();
            }

        }
    }

    @Override
    public String getName() {
        return "githubuser";
    }

    @Override
    public String getDesc() {
        return "Get information about a user on Github.";
    }

    @Override
    public String getUsage() {
        return "githubuser <arg:string>";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
