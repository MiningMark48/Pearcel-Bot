package com.miningmark48.tidalbot.commands.music;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.FormatUtil;
import com.miningmark48.tidalbot.util.features.music.handler.AudioHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class CommandVoteSkip implements ICommand {

    public static final String desc = "Vote to skip a track.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "voteskip";
    public static final String info = desc + " " + usage;

    private int voteSkipAmt = 0;
    private int voteSkipAmtNeeded = 2;
    private static ArrayList<String> usersVoted = new ArrayList<>();

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (!usersVoted.contains(event.getAuthor().getId())) {
            voteSkipAmt++;
            usersVoted.add(event.getAuthor().getId());
            event.getTextChannel().sendMessage(FormatUtil.formatText(FormatUtil.FormatType.BOLD,"[" + voteSkipAmt + "/" + voteSkipAmtNeeded + "]") + " Voted to skip currently playing track.").queue();
            if (voteSkipAmt == voteSkipAmtNeeded) {
                event.getTextChannel().sendMessage("Max votes received, skipped currently playing track.").queue();
                AudioHandler.skipTrack(event.getTextChannel());
                usersVoted.clear();
                voteSkipAmt = 0;
            }
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "voteskip";
    }

    @Override
    public String getDesc() {
        return "Vote to skip a track.";
    }

    @Override
    public String getUsage() {
        return "voteskip";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }

    @Override
    public boolean isMusic() {
        return true;
    }
}
