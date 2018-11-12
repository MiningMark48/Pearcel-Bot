package com.miningmark48.tidalbot.commands.botregular;

import com.google.gson.JsonObject;
import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.util.JSON.JSONParse;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandPigLatin implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        JsonObject js;

        String message = event.getMessage().getContentRaw().substring(10);
        String text = message.replace(" ", "+");

        if (args.length <= 0){
            event.getTextChannel().sendMessage("Missing Arguments!").queue();
        }else{
            js = JSONParse.JSONParse("http://api.funtranslations.com/translate/piglatin.json?text=" + text);
            event.getTextChannel().sendMessage(js.get("contents").getAsJsonObject().get("translated").getAsString()).queue();

        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "piglatin";
    }

    @Override
    public String getDesc() {
        return "Convert text to Pig Latin!";
    }

    @Override
    public String getUsage() {
        return "piglatin <arg:string>";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
