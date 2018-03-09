package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.reference.Reference;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandLMGTFY implements ICommand, ICommandInfo {

    public static final String desc = "Let me Google that for you.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "lmgtfy <arg>";
    public static final String info = desc + " " + usage;

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
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
