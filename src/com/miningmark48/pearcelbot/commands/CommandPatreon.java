package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandPatreon implements ICommand, ICommandInfo {

    private String pateronCreatorLink = "https://www.patreon.com/user?u=201693";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#E6461A"));
        builder.setAuthor("Patreon", pateronCreatorLink, "https://c4.patreon.com/toolbox/patreon_logo.png");
        builder.setThumbnail("https://c4.patreon.com/toolbox/patreon_logo.png");
        builder.setDescription(String.format("Support MiningMark48, the developer of %s, on Patreon! \n\n%s", Reference.botName, pateronCreatorLink));
        builder.addBlankField(false);
        builder.addField(new MessageEmbed.Field("Why?", "'Pledging to me will result in more frequent updates to my projects as well as my eternal gratitude.' -MiningMark48", false));

        event.getTextChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "patreon";
    }

    @Override
    public String getDesc() {
        return "Support MiningMark48 on Patreon! ...or not.";
    }

    @Override
    public String getUsage() {
        return "patreon";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
