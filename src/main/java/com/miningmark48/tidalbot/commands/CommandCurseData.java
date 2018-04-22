package com.miningmark48.tidalbot.commands;

import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.commands.base.ICommandPrivate;
import com.miningmark48.tidalbot.util.features.CurseData;
import com.miningmark48.tidalbot.util.FormatUtil;
import com.miningmark48.tidalbot.util.MessageUtil;
import com.miningmark48.tidalbot.util.DataUtil;
import com.sun.xml.internal.ws.util.StringUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Map;

public class CommandCurseData implements ICommand, ICommandPrivate, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        getData(args, event, false);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public void actionPrivate(String[] args, MessageReceivedEvent event) {
        getData(args, event, true);
    }

    private static String toCurseURL(String projectName){
        return CurseData.PROJECT_URL + projectName.replaceAll(" ", "-");
    }

    private static void getData(String[] args, MessageReceivedEvent event, boolean isPrivate) {

        if (args.length >= 1) {

            if (args[0].contains("@")) {

                MessageUtil.sendMessage(event, "This command uses names registered on Curse, not Discord!", isPrivate).queue();
                return;
            }

            MessageUtil.sendMessage(event, String.format("\uD83D\uDD50 Fetching statistics for %s.", FormatUtil.formatText(FormatUtil.FormatType.ITALIC, args[0])), isPrivate).queue(msg -> {
                MessageUtil.sendTyping(event, isPrivate).queue();
                final CurseData data = new CurseData(args[0]);
                final EmbedBuilder embed = new EmbedBuilder();

                if (!data.exists()) {
                    msg.editMessage("No user could be found by the name of " + FormatUtil.formatText(FormatUtil.FormatType.ITALIC, args[0])).queue();
                    return;
                }
                else if (!data.hasProjects()) {
                    msg.editMessage("No projects found for " + FormatUtil.formatText(FormatUtil.FormatType.ITALIC, args[0])).queue();
                    return;
                }

                int addedProjects = 0;
                long otherDLs = 0;

                for (final Map.Entry<String, Long> set : data.getDownloads().entrySet()) {

                    if (addedProjects >= 10) {

                        otherDLs += set.getValue();
                        continue;
                    }

                    embed.addField(set.getKey(), NumberFormat.getInstance().format(set.getValue()) + " downloads" + DataUtil.SEPERATOR + "[URL](" + toCurseURL(set.getKey()) + ")", true);
                    addedProjects++;
                }

                embed.addBlankField(false);
                embed.addField("Total Projects", String.valueOf(data.getProjectCount()), true);
                embed.addField("Total Downloads", NumberFormat.getInstance().format(data.getTotalDownloads()), true);

                if (addedProjects < data.getProjectCount()) embed.addField("Other Projects - " + (data.getProjectCount() - addedProjects), NumberFormat.getInstance().format(otherDLs), true);

                embed.setColor(Color.decode("#f05422"));
                embed.setFooter(String.format("Data provided by Curse, Requested by %s", event.getAuthor().getName()), null);
                embed.setAuthor(StringUtils.capitalize(args[0]) + "'s Statistics", null, null);

                if (data.hasAvatar()) {
                    embed.setThumbnail(data.getAvatar());
                } else {
                    embed.setThumbnail("https://pbs.twimg.com/profile_images/725406699369058304/0mVMpCrb_400x400.jpg");
                }

                msg.delete().queue();
                MessageUtil.sendMessage(event, embed.build(), isPrivate).queue();
            });

        }
        else {
            MessageUtil.sendMessage(event, "You must specify the name of a user that is registered on Curse!", isPrivate).queue();
        }
    }

    @Override
    public String getName() {
        return "cursedata";
    }

    @Override
    public String getDesc() {
        return "Fetch Minecraft Curse data for a user.";
    }

    @Override
    public String getUsage() {
        return "cursedata";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }

}
