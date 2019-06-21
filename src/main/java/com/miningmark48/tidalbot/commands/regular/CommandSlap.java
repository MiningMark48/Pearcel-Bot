package com.miningmark48.tidalbot.commands.regular;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandSlap implements ICommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("*slaps " + args[0] + " with a fish.* \uD83D\uDC1F").queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

    @Override
    public String getName() {
        return "slap";
    }

    @Override
    public String getDesc() {
        return "Slap someone.";
    }

    @Override
    public String getUsage() {
        return "slap <arg:string>";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }
}
