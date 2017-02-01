package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.Command;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandNowPlaying implements Command{
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

//    public static final String desc = "See what is currently playing.";
//    public static final String usage = "USAGE: " + Reference.botCommandKey + "nowplaying";
//    public static final String info = desc + " " + usage;
//
//    @Override
//    public boolean called(String[] args, MessageReceivedEvent event) {
//        return true;
//    }
//
//    @Override
//    public void action(String[] args, MessageReceivedEvent event) {
//
//        if(CommandPlay.player.isPlaying()) {
//
//            AudioTimestamp currentTime = CommandPlay.player.getCurrentTimestamp();
//            AudioInfo info = CommandPlay.player.getCurrentAudioSource().getInfo();
//            if(info.getError() == null){
//                event.getTextChannel().sendMessage("**Playing:** " + info.getTitle() + "\n" + "**Time:**      [" + currentTime.getTimestamp() + " / " + info.getDuration().getTimestamp() + "]");
//            }else{
//                event.getTextChannel().sendMessage("**Playing:** " + info.getTitle() + "\n" + "**Time:**      [" + currentTime.getTimestamp() + " / (N/A)]");
//            }
//
//        }else{
//            event.getTextChannel().sendMessage("The player is not playing anything right now!");
//        }
//
//    }
//
//    @Override
//    public void executed(boolean success, MessageReceivedEvent event) {
//
//    }
}
