package com.miningmark48.tidalbot.commands.botowner;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.reference.Reference;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandAnnounceAll implements ICommand {

    private String[] annChans = {"announcements", "announcement", "general", "meta", "general-discussion", "information", "info"};

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (event.getAuthor().getId().equalsIgnoreCase(Reference.botOwner)){

            String message = "";
            for (int i = 1; i <= args.length; i++){
                message = message + args[i - 1] + " ";
                message = message.substring(0, 1).toUpperCase() + message.substring(1);
            }

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Announcement");
            builder.setColor(Color.decode("#2200FF"));
            builder.setThumbnail("http://tw.miningmark48.xyz/img/icons/exclamation_point.png");
            builder.setFooter("This message was sent to all servers with " + Reference.botName + " connected.", null);
            builder.setDescription(message);

            event.getJDA().getGuilds().forEach(q -> {
                TextChannel channel = null;
                for (String chan : annChans) {
                    if (channel == null && !q.getTextChannelsByName(chan, true).isEmpty()) channel = q.getTextChannelsByName(chan, true).get(0);
                }
                if (channel == null) channel = q.getTextChannels().get(0);

                channel.sendMessage(builder.build()).queue();

            });

        } else {
            event.getTextChannel().sendMessage(event.getAuthor().getName() + ", You do not have permission to run that command.").queue();
        }


    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "announceall";
    }

    @Override
    public String getDesc() {
        return "Announce a message to all servers.";
    }

    @Override
    public String getUsage() {
        return "announce <arg:string>";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.BOT_OWNER;
    }
}
