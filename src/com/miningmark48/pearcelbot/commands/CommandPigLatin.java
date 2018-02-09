package com.miningmark48.pearcelbot.commands;

import com.google.gson.JsonObject;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.JSON.JSONParse;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandPigLatin implements ICommand {

    public static final String desc = "Convert text to Pig Latin!";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "piglatin <arg>";
    public static final String info = desc + " " + usage;

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

}
