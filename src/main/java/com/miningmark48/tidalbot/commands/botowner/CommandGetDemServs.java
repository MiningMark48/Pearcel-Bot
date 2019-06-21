package com.miningmark48.tidalbot.commands.botowner;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.reference.Reference;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandGetDemServs implements ICommand {

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
                String guildID = guild.getId();
                builder.append(guildName).append(" - ").append(guildID).append("\n");
            }
            builder.append("```");
            event.getAuthor().openPrivateChannel().queue(chan -> chan.sendMessage(builder.build()).queue());
        }else{
            event.getTextChannel().sendMessage(event.getAuthor().getName() + ", You do not have permission to run that command.").queue();
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
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.BOT_OWNER;
    }
}
