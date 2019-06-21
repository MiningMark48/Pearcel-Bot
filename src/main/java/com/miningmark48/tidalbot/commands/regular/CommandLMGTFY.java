package com.miningmark48.tidalbot.commands.regular;

import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandLMGTFY implements ICommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length < 1){
            event.getTextChannel().sendMessage("Not enough args!").queue();
            return;
        }
        StringBuilder lmgtfyLink = new StringBuilder("http://lmgtfy.com/?iie=").append(0).append("&q=");
        for (int i = 0; i <= args.length - 1; i++){
            lmgtfyLink.append(args[i] + (i == args.length - 1 ? "" : "+"));
        }
        event.getMessage().getChannel().sendMessage("Let me Google that for you...").queue(m -> m.editMessage(String.format("Here you go! \n%s", lmgtfyLink.toString())).queue());
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

    @Override
    public String getName() {
        return "lmgtfy";
    }

    @Override
    public String getDesc() {
        return "Let me Google that for you.";
    }

    @Override
    public String getUsage() {
        return "lmgtfy <arg:string>";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }
}
