package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.ICommand;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.CurseData;
import com.miningmark48.pearcelbot.util.Tools;
import com.sun.xml.internal.ws.util.StringUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Map;

public class CommandCurseData implements ICommand{

    public static final String desc = "Fetch Minecraft Curse data for a user";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "cursedata <arg>";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length >= 1) {

            if (args[0].contains("@")) {

                event.getTextChannel().sendMessage("This command uses names registered on Curse, not Discord!").queue();
                return;
            }

            event.getTextChannel().sendMessage(String.format("\uD83D\uDD50 Fetching statistics for %s.", Tools.formatText(Tools.FormatType.ITALIC, args[0]))).queue();

            final CurseData data = new CurseData(args[0]);
            final EmbedBuilder embed = new EmbedBuilder();

            if (!data.exists()) {

                event.getTextChannel().sendMessage("No user could be found by the name of " + Tools.formatText(Tools.FormatType.ITALIC, args[0])).queue();
                return;
            }
            else if (!data.hasProjects()) {

                event.getTextChannel().sendMessage("No projects found for " + Tools.formatText(Tools.FormatType.ITALIC, args[0])).queue();
                return;
            }

            int addedProjects = 0;
            long otherDLs = 0;

            for (final Map.Entry<String, Long> set : data.getDownloads().entrySet()) {

                if (addedProjects >= 10) {

                    otherDLs += set.getValue();
                    continue;
                }

                embed.addField(set.getKey(), NumberFormat.getInstance().format(set.getValue()) + " downloads" + Tools.SEPERATOR + "[URL](" + toCurseURL(set.getKey()) + ")", true);
                addedProjects++;
            }

            embed.addBlankField(false);
            embed.addField("Total Projects", String.valueOf(data.getProjectCount()), true);
            embed.addField("Total Downloads", NumberFormat.getInstance().format(data.getTotalDownloads()), true);

            if (addedProjects < data.getProjectCount())
                embed.addField("Other Projects - " + (data.getProjectCount() - addedProjects), NumberFormat.getInstance().format(otherDLs), true);

            embed.setColor(Color.decode("#f05422"));
            embed.setFooter(String.format("Data provided by Curse, Requested by %s", event.getAuthor().getName()), null);
            embed.setAuthor(StringUtils.capitalize(args[0]) + "'s Statistics", null, null);

            if (data.hasAvatar()) {
                embed.setThumbnail(data.getAvatar());
            }else{
                embed.setThumbnail("https://media-corp.cursecdn.com/attachments/0/201/logo4.png");
            }

            event.getTextChannel().sendMessage(embed.build()).queue();

        }
        else {
            event.getTextChannel().sendMessage("You must specify the name of a user that is registered on Curse!").queue();
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    private static String toCurseURL(String projectName){
        return CurseData.PROJECT_URL + projectName.replaceAll(" ", "-");
    }

}
