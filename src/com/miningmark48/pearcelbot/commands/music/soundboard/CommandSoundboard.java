package com.miningmark48.pearcelbot.commands.music.soundboard;

import com.miningmark48.pearcelbot.commands.ICommand;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.music.handler.AudioHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

@SuppressWarnings("Duplicates")
public class CommandSoundboard implements ICommand {

    public static final String desc = "Have the bot play a specific sound";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "soundboard <arg>";
    public static final String info = desc + " " + usage;

    private static final String baseURL = "http://miningmark48.xyz/projects/pearcelbot/sfx/";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length > 0) {
            if (!event.getMember().getRoles().toString().contains(Reference.botNoMusicRole)) {
                switch (args[0].toLowerCase()) {
                    case "cena":
                        AudioHandlerSoundboard.loadAndPlay(event.getTextChannel(), event.getAuthor(), baseURL + "john_cena.mp3", false);
                        break;
                    case "china":
                        AudioHandlerSoundboard.loadAndPlay(event.getTextChannel(), event.getAuthor(), baseURL + "china.mp3", false);
                        break;
                    case "correct":
                        AudioHandlerSoundboard.loadAndPlay(event.getTextChannel(), event.getAuthor(), baseURL + "correct.mp3", false);
                        break;
                    case "creeper":
                        AudioHandlerSoundboard.loadAndPlay(event.getTextChannel(), event.getAuthor(), baseURL + "creeper.mp3", false);
                        break;
                    case "easy":
                        AudioHandlerSoundboard.loadAndPlay(event.getTextChannel(), event.getAuthor(), baseURL + "that_was_easy.mp3", false);
                        break;
                    case "illuminati":
                        AudioHandlerSoundboard.loadAndPlay(event.getTextChannel(), event.getAuthor(), baseURL + "illuminati.mp3", false);
                        break;
                    case "ping":
                        AudioHandlerSoundboard.loadAndPlay(event.getTextChannel(), event.getAuthor(), baseURL + "discord_ping.mp3", false);
                        break;
                    case "swamp":
                        AudioHandlerSoundboard.loadAndPlay(event.getTextChannel(), event.getAuthor(), baseURL + "swamp.mp3", false);
                        break;
                    case "trombone":
                        AudioHandlerSoundboard.loadAndPlay(event.getTextChannel(), event.getAuthor(), baseURL + "trombone.mp3", false);
                        break;
                    case "wah":
                        AudioHandlerSoundboard.loadAndPlay(event.getTextChannel(), event.getAuthor(), baseURL + "wah.mp3", false);
                        break;
                    case "wario":
                        AudioHandlerSoundboard.loadAndPlay(event.getTextChannel(), event.getAuthor(), baseURL + "wario.mp3", false);
                        break;
                    default:
                        break;
                }
                event.getMessage().delete().queue();
            } else {
                event.getTextChannel().sendMessage("Sorry " + event.getAuthor().getAsMention() + ", but you do not have permission to use that command. If you think this is a mistake, ask an admin why you have the `" + Reference.botNoMusicRole + "` role.").queue();
            }
        } else  {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.BLUE);
            builder.setFooter("soundboard <sound>", null);
            builder.setTitle("Available Sounds");
            builder.addField("Cena", "And his name is...", true);
            builder.addField("China", "China!", true);
            builder.addField("Correct", "Ba-Ding!", true);
            builder.addField("Creeper", "SSssssSSSsss", true);
            builder.addField("Easy", "That was easy!", true);
            builder.addField("Illuminati", "That was easy!", true);
            builder.addField("Ping", "Boink!", true);
            builder.addField("Swamp", "...my swamp?!", true);
            builder.addField("Trombone", "Wah, wah, wah...", true);
            builder.addField("Wah", "Wah!", true);
            builder.addField("Wario", "Imma Wario!", true);

            event.getTextChannel().sendMessage(builder.build()).queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

}
