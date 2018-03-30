package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandGetDemServs implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if(event.getAuthor().getId().equalsIgnoreCase(Reference.botOwner)){
            MessageBuilder builder = new MessageBuilder();
            event.getTextChannel().sendMessage("Check your DMs").queue();
            event.getAuthor().openPrivateChannel().queue();
            builder.append("```");
            for (Guild guild : event.getJDA().getGuilds()){
                String guildName = guild.getName();
                builder.append(guildName + "\n");
            }
            builder.append("```");
            event.getAuthor().openPrivateChannel().queue(chan -> chan.sendMessage(builder.build()).queue());
        }else{
            event.getTextChannel().sendMessage(event.getAuthor().getName() + ", You do not have permission to run that command.");
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "getdemservs";
    }

    @Override
    public String getDesc() {
        return "List all Guilds the bot is connected to.";
    }

    @Override
    public String getUsage() {
        return "getdemservs";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
