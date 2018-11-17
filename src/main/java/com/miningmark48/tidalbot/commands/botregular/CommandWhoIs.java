package com.miningmark48.tidalbot.commands.botregular;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.util.FormatUtil;
import com.miningmark48.tidalbot.util.MessageUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandWhoIs implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        MessageUtil.sendTyping(event);

        if (args.length <= 0) {
            event.getTextChannel().sendMessage("Missing args! \n\n**Usage:** `" + getUsage() + "`").queue();
            return;
        }

        String username = String.join(" ", args);

        Guild guild = event.getGuild();
        Member member = guild.getMembersByName(username, true).stream().findFirst().orElse(guild.getMemberById(FormatUtil.removeNonNumeric(username)));

        if (member == null) {
            event.getTextChannel().sendMessage("**Error:** User not found.").queue();
            return;
        }

        User user = member.getUser();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.decode("#7289da"));
        embedBuilder.setAuthor(FormatUtil.getDefaultString(user.getName()), null, user.getAvatarUrl());
        embedBuilder.setThumbnail(user.getAvatarUrl());

        embedBuilder.addField("Name", FormatUtil.getDefaultString(user.getName()), true);
        embedBuilder.addField("Nickname", FormatUtil.getDefaultString(member.getNickname()), true);
        embedBuilder.addField("ID", FormatUtil.getDefaultString(user.getId()), true);
        embedBuilder.addField("Created On", FormatUtil.getDefaultString(user.getCreationTime().toString().substring(0, 10)), true);
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
    public CommandType getType() {
        return CommandType.NORMAL;
    }

}
