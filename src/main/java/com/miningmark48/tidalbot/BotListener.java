package com.miningmark48.tidalbot;

import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.LoggerUtil;
import com.miningmark48.tidalbot.util.features.chatlog.ChatLog;
import com.miningmark48.tidalbot.util.features.serverconfig.ServerConfigHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {

    public static String key = Reference.botCommandKey;

    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        if (event.getJDA().getStatus() == JDA.Status.ATTEMPTING_TO_RECONNECT || event.getJDA().getStatus() == JDA.Status.CONNECTING_TO_WEBSOCKET){
            return;
        }

        if(event.getMessage().getContentRaw().startsWith(Reference.botCommandKey) && !event.getMessage().getAuthor().getId().equalsIgnoreCase(event.getJDA().getSelfUser().getId())) {
            Main.handleCommand(Main.parser.parse(event.getMessage().getContentRaw(), event));
        }

        if (event.getJDA().getSelfUser() != null && event.getMember() != null && !event.getMessage().getAuthor().getId().equalsIgnoreCase(event.getJDA().getSelfUser().getId())) {
            if (!event.getMember().getEffectiveName().equalsIgnoreCase(event.getJDA().getSelfUser().getName()) && ServerConfigHandler.isAREnabled(event)) {
                if (!ServerConfigHandler.isUserARBlacklisted(event, event.getAuthor().getId())) {
                    Main.handleMessage(event);
                }
            }
        }

        if (event.getMessage().getContentRaw().startsWith(Reference.botCommandCustomKey) && !event.getMessage().getAuthor().getId().equalsIgnoreCase(event.getJDA().getSelfUser().getId())) {
            Main.handleCustom(event);
        }

        if (Reference.doChatLog && !event.getChannel().getType().equals(ChannelType.PRIVATE)) {
            ChatLog.ChatLog(event);
        }

    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event){

        TextChannel tc = event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getName().equalsIgnoreCase("welcome")).findFirst().orElse(null);

        if(tc != null) {
            tc.sendMessage("Welcome **" + event.getMember().getEffectiveName() + "** to **" + event.getGuild().getName() + "**!").queue();
        }

    }

    @Override
    public void onReady(ReadyEvent event){
        //event.getJDA().getAccountManager().update();
        LoggerUtil.log(LoggerUtil.LogType.STATUS, "Logged in as: " + event.getJDA().getSelfUser().getName());
    }

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
//        GuildJoinChat.joinedVoice(event);
//        if (event.getGuild().getAudioManager().isConnected() && event.getChannelJoined() == event.getGuild().getAudioManager().getConnectedChannel() && event.getMember().getUser() != Main.jda.getSelfUser()) {
//            if (event.getGuild().getAudioManager().getConnectedChannel().getMembers().size() > 1) {
//                if (AudioHandler.getGuildAudioPlayer(event.getGuild()).player.isPaused()) {
//                    AudioHandler.getGuildAudioPlayer(event.getGuild()).player.setPaused(false);
//                }
//            }
//        }
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
//        if (event.getGuild().getAudioManager().isConnected() && event.getChannelLeft() == event.getGuild().getAudioManager().getConnectedChannel() && event.getMember().getUser() != Main.jda.getSelfUser()) {
//            if (event.getGuild().getAudioManager().getConnectedChannel().getMembers().size() <= 1) {
//                if (!AudioHandler.getGuildAudioPlayer(event.getGuild()).player.isPaused()) {
//                    AudioHandler.getGuildAudioPlayer(event.getGuild()).player.setPaused(true);
//                }
//            }
//        }
    }

}
