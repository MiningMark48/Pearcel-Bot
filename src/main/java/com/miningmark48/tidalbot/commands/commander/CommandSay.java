package com.miningmark48.tidalbot.commands.commander;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandSay implements ICommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String messageFinal = event.getMessage().getContentRaw().substring(5);
        event.getMessage().delete().queue();
        event.getTextChannel().sendMessage(messageFinal).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

    @Override
    public String getName() {
        return "say";
    }

    @Override
    public String getDesc() {
        return "Make the bot say something.";
    }

    @Override
    public String getUsage() {
        return "say <arg:string>";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.MANAGER;
    }
}
