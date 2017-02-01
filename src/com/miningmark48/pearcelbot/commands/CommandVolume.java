package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.ICommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandVolume implements ICommand {
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

//    public static final String desc = "Set the audio volume of the bot.";
//    public static final String usage = "USAGE: " + Reference.botCommandKey + "volume <arg>";
//    public static final String info = desc + " " + usage;
//
//    public static float volume = 50.0f /1000;
//
//    @Override
//    public boolean called(String[] args, MessageReceivedEvent event) {
//        return true;
//    }
//
//    @Override
//    public void action(String[] args, MessageReceivedEvent event) {
//        if (event.getGuild().getRolesByName(event.getAuthor().getName(), true).contains(Reference.botAutoResponseRole) || event.getGuild().getOwner() == event.getAuthor() || event.getAuthor().getId() == Reference.botOwner){
//            if(CommandPlay.player.isPlaying()) {
//                Float arg = Float.parseFloat(args[0]);
//
//                if(arg <= 100) {
//                    volume = arg/1000;
//                    CommandPlay.player.setVolume(volume);
//                }else{
//                    event.getTextChannel().sendMessage("Volume to high, please use 1 to 100.");
//                }
//
//                int blocks = (Integer.parseInt(args[0]) / 10) - 1;
//                MessageBuilder builder = new MessageBuilder();
//                builder.appendString("Volume: " + args[0] + "%\n|");
//                for (int i = 0; i <= blocks; i++){
//                    builder.appendString("\u2587 ");
//                }
//                int negBlocks = (10 - blocks) - 2;
//                for(int i = 0; i <= negBlocks; i++){
//                    builder.appendString("\u2581 ");
//                }
//                builder.appendString("|\n");
//                event.getTextChannel().sendMessage(builder.build());
//
//            }
//        }else{
//            event.getTextChannel().sendMessage(event.getAuthor().getUsername() + ", You do not have permission to run that command.");
//        }
//
//
//    }
//
//    @Override
//    public void executed(boolean success, MessageReceivedEvent event) {
//
//    }
}
