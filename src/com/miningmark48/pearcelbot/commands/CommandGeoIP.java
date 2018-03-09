package com.miningmark48.pearcelbot.commands;

import com.google.gson.JsonObject;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.JSON.JSONParse;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandGeoIP implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        JsonObject js;

        if (args.length <= 0){
            js = JSONParse.JSONParse("http://freegeoip.net/json/");
        }else{
            js = JSONParse.JSONParse("http://freegeoip.net/json/" + args[0]);
        }

        MessageBuilder builder = new MessageBuilder();
        builder.append("**IP:** " + js.get("ip").getAsString());
        builder.append("\n**Location:** " + js.get("city").getAsString() + ", " + js.get("region_name").getAsString() + ", " + js.get("country_name").getAsString());

        event.getTextChannel().sendMessage(builder.build()).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "geoip";
    }

    @Override
    public String getDesc() {
        return "Get geographic information about an IP.";
    }

    @Override
    public String getUsage() {
        return "geoip [arg:IP|website]";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
