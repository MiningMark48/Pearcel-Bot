package com.miningmark48.pearcelbot;

import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.chatlog.ChatLog;
import com.miningmark48.pearcelbot.util.Logger;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getMessage().getContent().startsWith(Reference.botCommandKey) && !event.getMessage().getAuthor().getId().equalsIgnoreCase(event.getJDA().getSelfUser().getId())){
            Main.handleCommand(Main.parser.parse(event.getMessage().getContent().toLowerCase(), event));
        }

//        if (event.getJDA().getSelfUser() != null && event.getMember() != null) {
//            if(!event.getMessage().getAuthor().getId().equalsIgnoreCase(event.getJDA().getSelfUser().getId())){
//                if (!event.getMember().getEffectiveName().equalsIgnoreCase(event.getJDA().getSelfUser().getName())) {
//                    if (event.getGuild().getMember(event.getJDA().getSelfUser()).getRoles().toString().contains(Reference.botAutoResponseRole)) {
//                        Main.handleMessage(event);
//                    }
//                }
//            }
//        }

        if(!event.getMessage().getAuthor().getId().equalsIgnoreCase(event.getJDA().getSelfUser().getId())){
//            Main.handleCustom(event);
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

}
