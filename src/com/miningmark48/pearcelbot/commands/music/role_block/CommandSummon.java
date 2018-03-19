package com.miningmark48.pearcelbot.commands.music.role_block;

import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.features.music.handler.AudioHandler;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

@SuppressWarnings("ConstantConditions")
public class CommandSummon implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (!event.getMember().getRoles().toString().contains(Reference.botNoMusicRole)) {
            event.getMessage().delete().queue();
            if (event.getGuild().getVoiceChannels().stream().anyMatch(q -> q.getMembers().stream().anyMatch(q2 -> q2.equals(event.getMember())))) {
                VoiceChannel channel = event.getGuild().getVoiceChannels().stream().filter(q -> q.getMembers().stream().anyMatch(q2 -> q2.equals(event.getMember()))).findFirst().get();
                if (event.getGuild().getAudioManager().getConnectedChannel() != channel) {
                    AudioHandler.moveVoice(event.getGuild().getAudioManager(), channel);
                    event.getTextChannel().sendMessage("I've been summoned!").queue();
                } else {
                    event.getTextChannel().sendMessage("I am already in that voice channel!").queue();
                }
            } else {
                event.getTextChannel().sendMessage("You must be in a voice channel to summon me!").queue();
            }

        } else {
            event.getTextChannel().sendMessage("Sorry " + event.getAuthor().getAsMention() + ", but you do not have permission to use that command. If you think this is a mistake, ask an admin why you have the `" + Reference.botNoMusicRole + "` role.").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

    @Override
    public String getName() {
        return "summon";
    }

    @Override
    public String getDesc() {
        return "Summon the bot to the voice channel you are currently in.";
    }

    @Override
    public String getUsage() {
        return "summon";
    }

    @Override
    public CommandType getType() {
        return CommandType.PBC;
    }
}
