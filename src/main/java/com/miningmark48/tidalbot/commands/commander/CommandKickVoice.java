package com.miningmark48.tidalbot.commands.commander;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.util.FormatUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.RestAction;

public class CommandKickVoice implements ICommand {

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

                String reason = "";
                for (int i = 2; i <= args.length; i++){
                    reason = reason + args[i - 1] + " ";
                    reason = reason.substring(0, 1).toUpperCase() + reason.substring(1);
                }

                if (reason.isEmpty()) {
                    reason = "N/A";
                }

                event.getTextChannel().sendMessage(FormatUtil.formatText(FormatUtil.FormatType.BOLD, event.getAuthor().getName()) + " kicked " + FormatUtil.formatText(FormatUtil.FormatType.BOLD, user.getName()) + " from voice for reason, `" + reason + "`.").queue();
                String finalReason = reason;
                privateChannel.queue(chan -> chan.sendMessage("You have been kicked from the voice channel by " + FormatUtil.formatText(FormatUtil.FormatType.BOLD, event.getAuthor().getName()) + " for reason, `" + finalReason + "`.").queue());
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

    private static boolean createVoice(Guild guild) {
        try {
            guild.createVoiceChannel("temp-kick").complete();
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
            guild.moveVoiceMember(guild.getMember(user), channel).complete();
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
        return "kickvoice <arg:string-username> [arg:string-reason]";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.SPECIFIC;
    }

    @Override
    public Permission[] getPermissions() {
        return new Permission[]{Permission.KICK_MEMBERS};
    }
}
