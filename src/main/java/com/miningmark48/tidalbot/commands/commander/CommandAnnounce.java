package com.miningmark48.tidalbot.commands.commander;

import com.miningmark48.tidalbot.base.CommandType;
import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandAnnounce implements ICommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String messageFinal = event.getMessage().getContentRaw().substring(10);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Announcement");
        builder.setColor(Color.decode("#2200FF"));
        builder.setThumbnail("http://tw.miningmark48.xyz/img/icons/exclamation_point.png");
        builder.setDescription(messageFinal);

        event.getTextChannel().sendMessage(builder.build()).queue();
        event.getMessage().delete().queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

    @Override
    public String getName() {
        return "announce";
    }

    @Override
    public String getDesc() {
        return "Announces a message to the channel.";
    }

    @Override
    public String getUsage() {
        return "announce <arg:string>";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.MANAGER;
    }
}
