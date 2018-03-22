package com.miningmark48.pearcelbot.commands.pbc;

import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.FormatUtil;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.RestAction;

public class CommandKickVoice implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length == 0) {
            event.getTextChannel().sendMessage("Missing args!").queue();
        } else {
            Guild guild = event.getGuild();
            if (guild.getMembersByName(args[0], true).stream().findFirst().isPresent()) {
                Member member = guild.getMembersByName(args[0], true).stream().findFirst().get();
                User user = member.getUser();
                RestAction<PrivateChannel> privateChannel = user.openPrivateChannel();
                createVoice(guild);
                moveToVoice(guild, user);
                deleteVoice(guild);
                event.getTextChannel().sendMessage("Kicked " + FormatUtil.formatText(FormatUtil.FormatType.BOLD, user.getName()) + " from voice.").queue();
                privateChannel.queue(chan -> chan.sendMessage("You have been kicked from the voice channel by " + event.getAuthor().getName() + ".").queue());
            } else {
                event.getTextChannel().sendMessage("Error, user not found!").queue();
            }
        }
        event.getMessage().delete().queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

    @Override
    public boolean isRestricted() {
        return true;
    }

    private static boolean createVoice(Guild guild) {
        try {
            guild.getController().createVoiceChannel("temp-kick").complete();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean deleteVoice(Guild guild) {
        try {
            guild.getVoiceChannelsByName("temp-kick", true).stream().findFirst().get().delete().complete();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean moveToVoice(Guild guild, User user) {
        try {
            VoiceChannel channel = guild.getVoiceChannelsByName("temp-kick", true).stream().findFirst().get();
            guild.getController().moveVoiceMember(guild.getMember(user), channel).complete();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "kickvoice";
    }

    @Override
    public String getDesc() {
        return "Kick a user from a voice channel.";
    }

    @Override
    public String getUsage() {
        return "kickvoice <arg:string-username>";
    }

    @Override
    public CommandType getType() {
        return CommandType.PBC;
    }
}
