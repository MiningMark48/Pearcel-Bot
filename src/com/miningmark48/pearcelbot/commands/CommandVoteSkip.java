package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.Command;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandVoteSkip implements Command{
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

//    public static final String desc = "Vote to skip a song that may be playing.";
//    public static final String usage = "USAGE: " + Reference.botCommandKey + "voteskip";
//    public static final String info = desc + " " + usage;
//
//    private static int skipAmount = 0;
//    private static int toSkipAmount = 2;
//    public static List<String> alreadyVoted = new ArrayList<>();
//
//    @Override
//    public boolean called(String[] args, MessageReceivedEvent event) {
//        return true;
//    }
//
//    @Override
//    public void action(String[] args, MessageReceivedEvent event) {
//
//        if (CommandPlay.player.isPlaying()) {
//            if (alreadyVoted.contains(event.getAuthor().getUsername())) {
//                event.getTextChannel().sendMessage(event.getAuthor().getUsername() + ", You have already voted.");
//            } else {
//                skipAmount++;
//                if(skipAmount != toSkipAmount){
//                    event.getTextChannel().sendMessage("Voted to skip song, need " + (toSkipAmount - skipAmount) + " out of " + toSkipAmount + " more votes to skip song.");
//                }
//            }
//
//            alreadyVoted.add(event.getAuthor().getUsername());
//
//            if (skipAmount == toSkipAmount) {
//                CommandPlay.player.skipToNext();
//                event.getTextChannel().sendMessage("Skipped.");
//                alreadyVoted.clear();
//                skipAmount = 0;
//            }
//        }else{
//            event.getTextChannel().sendMessage("Nothing is playing!");
//        }
//    }
//
//    @Override
//    public void executed(boolean success, MessageReceivedEvent event) {
//
//    }
}
