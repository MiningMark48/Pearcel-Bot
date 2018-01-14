package com.miningmark48.pearcelbot.commands.music.role_block;

import com.miningmark48.pearcelbot.commands.ICommand;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.music.handler.AudioHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandPlay implements ICommand {

    public static final String desc = "Plays a track from a url.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "play <track url>";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (!event.getAuthor().getJDA().getRoles().toString().contains(Reference.botNoMusicRole)) {
            AudioHandler.loadAndPlay(event.getTextChannel(), event.getAuthor(), args[0], false);
        } else {
            event.getTextChannel().sendMessage("Sorry " + event.getAuthor().getAsMention() + ", but you do not have permission to use that command. If you think this is a mistake, ask an admin why you have the `" + Reference.botNoMusicRole + "` role.").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
