package com.miningmark48.tidalbot.commands.regular;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.util.UtilFormat;
import com.miningmark48.tidalbot.util.UtilMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandWhoIs implements ICommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        UtilMessage.sendTyping(event);

        if (args.length <= 0) {
            event.getTextChannel().sendMessage("Missing args! \n\n**Usage:** `" + getUsage() + "`").queue();
            return;
        }

        String username = String.join(" ", args);

        Guild guild = event.getGuild();
        Member member = guild.getMembersByName(username, true).stream().findFirst().orElse(guild.getMemberById(UtilFormat.removeNonNumeric(username)));

        if (member == null) {
            event.getTextChannel().sendMessage("**Error:** User not found.").queue();
            return;
        }

        User user = member.getUser();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.decode("#7289da"));
        embedBuilder.setAuthor(UtilFormat.getDefaultString(user.getName()), null, user.getAvatarUrl());
        embedBuilder.setThumbnail(user.getAvatarUrl());

        embedBuilder.addField("Name", UtilFormat.getDefaultString(user.getName()), true);
        embedBuilder.addField("Nickname", UtilFormat.getDefaultString(member.getNickname()), true);
        embedBuilder.addField("ID", UtilFormat.getDefaultString(user.getId()), true);
        embedBuilder.addField("Created On", UtilFormat.getDefaultString(user.getTimeCreated().toString().substring(0, 10)), true);
        embedBuilder.addField("Is Bot?", user.isBot() ? "Yes" : "No", true);

        event.getTextChannel().sendMessage(embedBuilder.build()).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "whois";
    }

    @Override
    public String getDesc() {
        return "Get info about a user on the server.";
    }

    @Override
    public String getUsage() {
        return "whois <string:Username | User ID>";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }

}
