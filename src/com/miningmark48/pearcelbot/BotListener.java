package com.miningmark48.pearcelbot;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.miningmark48.pearcelbot.commands.CommandARBlacklist;
import com.miningmark48.pearcelbot.commands.pbc.CommandAddCommand;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.messages.GuildJoinChat;
import com.miningmark48.pearcelbot.util.JSON.JSONParseFile;
import com.miningmark48.pearcelbot.util.chatlog.ChatLog;
import com.miningmark48.pearcelbot.util.logging.LogType;
import com.miningmark48.pearcelbot.util.logging.Logger;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class BotListener extends ListenerAdapter {

    public static String key = Reference.botCommandKey;

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if (event.getJDA().getStatus() == JDA.Status.ATTEMPTING_TO_RECONNECT || event.getJDA().getStatus() == JDA.Status.CONNECTING_TO_WEBSOCKET){
            return;
        }

        if(event.getMessage().getContent().startsWith(Reference.botCommandKey) && !event.getMessage().getAuthor().getId().equalsIgnoreCase(event.getJDA().getSelfUser().getId())) {
            Main.handleCommand(Main.parser.parse(event.getMessage().getContent().toLowerCase(), event));
        }

        if (event.getJDA().getSelfUser() != null && event.getMember() != null && !event.getMessage().getAuthor().getId().equalsIgnoreCase(event.getJDA().getSelfUser().getId())) {
            if (!event.getMember().getEffectiveName().equalsIgnoreCase(event.getJDA().getSelfUser().getName()) && event.getGuild().getMember(event.getJDA().getSelfUser()).getRoles().toString().contains(Reference.botAutoResponseRole)) {
                if (!isBlacklisted(event.getAuthor().getId())) {
                    Main.handleMessage(event);
                }
            }
        }

        if (event.getMessage().getContent().startsWith(Reference.botCommandCustomKey) && !event.getMessage().getAuthor().getId().equalsIgnoreCase(event.getJDA().getSelfUser().getId())) {
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
        Logger.log("status", "Logged in as: " + event.getJDA().getSelfUser().getName());
    }

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        GuildJoinChat.joinedVoice(event);
    }

    private static boolean isBlacklisted(String id) {

        JsonObject jsonObj = JSONParseFile.JSONParse(CommandARBlacklist.fileName);
        return jsonObj != null && jsonObj.getAsJsonArray("blacklist").contains(new JsonPrimitive(id));

    }

}
