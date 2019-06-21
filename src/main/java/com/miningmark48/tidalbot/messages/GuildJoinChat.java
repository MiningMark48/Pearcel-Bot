package com.miningmark48.tidalbot.messages;

import com.google.gson.JsonObject;
import com.miningmark48.tidalbot.util.JSON.JSONParseFile;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuildJoinChat {

    private static final String file = "misc//voiceChatMessages.json";

    private static Member member;

    public static void joinedVoice(GuildVoiceJoinEvent event){

        JsonObject jsonObject = JSONParseFile.JSONParse(file);

        Guild guild = event.getGuild();
        member = event.getMember();
        String memberID = event.getMember().getUser().getId();
        List<TextChannel> textChannels = guild.getTextChannels();
        String guildID = guild.getId();

        JsonObject guildObj = jsonObject.get(guildID).getAsJsonObject();
        String messageChannelId = guildObj.get("text_channel").getAsString();
        JsonObject memberIds = guildObj.get("member_ids").getAsJsonObject();

        if (!memberIds.get(member.getUser().getId()).isJsonNull()){
            String message = aliasChange(memberIds.get(memberID).getAsString());
            message = commentRemove(message);
            textChannels.stream().filter(channel -> channel.getId().contentEquals(messageChannelId)).findFirst().get().sendMessage(message).queue();
        }

    }

    private static String aliasChange(String message){
        char key = '%';

        message = message.replace(key + "name" + key, member.getUser().getName());
        message = message.replace(key + "nickname" + key, member.getNickname() != null ? member.getNickname() : member.getUser().getName());
        message = message.replace(key + "id" + key, member.getUser().getId());
        message = message.replace(key + "vchannel" + key, member.getVoiceState().inVoiceChannel() ? member.getVoiceState().getChannel().getName() : "..");

        message = aliasRand(message);

        return message;
    }

    private static String aliasRand(String message){
        Random rand = new Random();
        Pattern regex = Pattern.compile("%(rand:)(.*[0-9])%");
        Matcher matcher = regex.matcher(message);

        while (matcher.find()){
            if (matcher.group().length() != 0){
                try{
                    message = message.replaceAll(regex.pattern(), String.valueOf(rand.nextInt(Integer.parseInt(matcher.group(2)))));
                }catch (NumberFormatException e){
                    message = "**ERROR:** NumberFormatException";
                }
            }
        }

        return message;
    }

    private static String commentRemove(String message){
        Pattern regex = Pattern.compile("\\?.*\\?");

        message = message.replaceAll(regex.pattern(), "");

        return message;
    }

}
