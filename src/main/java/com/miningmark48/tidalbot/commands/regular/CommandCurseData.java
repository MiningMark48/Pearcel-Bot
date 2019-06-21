package com.miningmark48.tidalbot.commands.regular;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.base.ICommandPrivate;
import com.miningmark48.tidalbot.util.UtilData;
import com.miningmark48.tidalbot.util.UtilFormat;
import com.miningmark48.tidalbot.util.UtilMessage;
import com.miningmark48.tidalbot.util.features.CurseData;
import com.sun.xml.internal.ws.util.StringUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Map;

public class CommandCurseData implements ICommand, ICommandPrivate {

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

                UtilMessage.sendMessage(event, "This command uses names registered on Curse, not Discord!", isPrivate).queue();
                return;
            }

            UtilMessage.sendMessage(event, String.format("\uD83D\uDD50 Fetching statistics for %s.", UtilFormat.formatText(UtilFormat.FormatType.ITALIC, args[0])), isPrivate).queue(msg -> {
                UtilMessage.sendTyping(event, isPrivate).queue();
                final CurseData data = new CurseData(args[0]);
                final EmbedBuilder embed = new EmbedBuilder();

                if (!data.exists()) {
                    msg.editMessage("No user could be found by the name of " + UtilFormat.formatText(UtilFormat.FormatType.ITALIC, args[0])).queue();
                    return;
                }
                else if (!data.hasProjects()) {
                    msg.editMessage("No projects found for " + UtilFormat.formatText(UtilFormat.FormatType.ITALIC, args[0])).queue();
                    return;
                }

                int addedProjects = 0;
                long otherDLs = 0;

                for (final Map.Entry<String, Long> set : data.getDownloads().entrySet()) {

                    if (addedProjects >= 10) {

                        otherDLs += set.getValue();
                        continue;
                    }

                    embed.addField(set.getKey(), NumberFormat.getInstance().format(set.getValue()) + " downloads" + UtilData.SEPERATOR + "[URL](" + toCurseURL(set.getKey()) + ")", true);
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
                UtilMessage.sendMessage(event, embed.build(), isPrivate).queue();
            });

        }
        else {
            UtilMessage.sendMessage(event, "You must specify the name of a user that is registered on Curse!", isPrivate).queue();
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
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }

}
