package com.miningmark48.pearcelbot;

import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.chatlog.ChatLog;
import com.miningmark48.pearcelbot.util.Logger;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Queue;

public class BotListener extends ListenerAdapter {

    public static String key = Reference.botCommandKey;

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if (event.getJDA().getStatus() == JDA.Status.ATTEMPTING_TO_RECONNECT || event.getJDA().getStatus() == JDA.Status.CONNECTING_TO_WEBSOCKET){
            return;
        }

        if(event.getMessage().getContent().startsWith(Reference.botCommandKey) && !event.getMessage().getAuthor().getId().equalsIgnoreCase(event.getJDA().getSelfUser().getId())){
            Main.handleCommand(Main.parser.parse(event.getMessage().getContent().toLowerCase(), event));
        }

        if (event.getJDA().getSelfUser() != null && event.getMember() != null) {
            if(!event.getMessage().getAuthor().getId().equalsIgnoreCase(event.getJDA().getSelfUser().getId())){
                if (!event.getMember().getEffectiveName().equalsIgnoreCase(event.getJDA().getSelfUser().getName())) {
                    if (event.getGuild().getMember(event.getJDA().getSelfUser()).getRoles().toString().contains(Reference.botAutoResponseRole)) {
                        Main.handleMessage(event);
                    }
                }
            }
        }

        if(event.getMessage().getContent().startsWith(Reference.botCommandCustomKey) && !event.getMessage().getAuthor().getId().equalsIgnoreCase(event.getJDA().getSelfUser().getId())){
            Main.handleCustom(event);
        }

        if (Reference.doChatLog && !event.getChannel().getType().equals(ChannelType.PRIVATE)) {
            ChatLog.ChatLog(event);
        }

        music(event); //Temporary

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

    //Temp
    private void music(MessageReceivedEvent event){
        if (!event.isFromType(ChannelType.TEXT))
            return;

        String[] command = event.getMessage().getContent().split(" ", 2);
        if (!command[0].startsWith(key))
            return;

        if ((key + "join").equals(command[0]))
        {
            doAlert(event);
        }
        else if ((key + "leave").equals(command[0]))
        {
            doAlert(event);
        }
        else if ((key + "play").equals(command[0]))
        {
            doAlert(event);
        }
        else if ((key + "pplay").equals(command[0]) && command.length == 2)
        {
            doAlert(event);
        }
        else if ((key + "skip").equals(command[0]))
        {
            doAlert(event);
        }
        else if ((key + "pause").equals(command[0]))
        {
            doAlert(event);
        }
        else if ((key + "resume").equals(command[0]))
        {
            doAlert(event);
        }
        else if ((key + "stop").equals(command[0]))
        {
            doAlert(event);
        }
        else if ((key + "volume").equals(command[0]))
        {
            doAlert(event);
        }
        else if ((key + "restart").equals(command[0]))
        {
            doAlert(event);
        }
        else if ((key + "repeat").equals(command[0]))
        {
            doAlert(event);
        }
        else if ((key + "reset").equals(command[0]))
        {
            doAlert(event);
        }
        else if ((key + "nowplaying").equals(command[0]) || (key + "np").equals(command[0]))
        {
            doAlert(event);
        }
        else if ((key + "list").equals(command[0]) || (key + "queue").equals(command[0]))
        {
            doAlert(event);
        }
        else if ((key + "shuffle").equals(command[0]))
        {
            doAlert(event);
        }
    }

    private void doAlert(MessageReceivedEvent event){
        event.getTextChannel().sendMessage("The music functionality has been moved to DJ Pearcel Bot. Click this link to add it: https://discordapp.com/oauth2/authorize?client_id=276447269417648138&scope=bot&permissions=3148800").queue();
        event.getTextChannel().sendMessage("").queue();
    }

}
