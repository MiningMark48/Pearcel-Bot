package com.miningmark48.pearcelbot.commands;

import com.google.gson.JsonObject;
import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.JSON.JSONParse;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandBitcoin implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        JsonObject js;

        try {
            js = JSONParse.JSONParse("https://api.coindesk.com/v1/bpi/currentprice.json");

            JsonObject jsBPI = js.get("bpi").getAsJsonObject();
            JsonObject jsTime = js.get("time").getAsJsonObject();

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(Color.decode("#FF9900"));
            embedBuilder.setFooter("Completed using the CoinDesk API", null);
            embedBuilder.setAuthor("Ƀitcoin", "https://bitcoin.org/", null);
            embedBuilder.setThumbnail("https://image.freepik.com/free-vector/modern-yellow-bitcoin-design_1017-9631.jpg");

            if (!jsBPI.get("USD").isJsonNull()) {
                JsonObject jsMoney = jsBPI.get("USD").getAsJsonObject();
                String rate = jsMoney.get("rate").getAsString();
                embedBuilder.addField("USD", "$" + rate.substring(0, rate.length() - 2), true);
            }
            if (!jsBPI.get("GBP").isJsonNull()) {
                JsonObject jsMoney = jsBPI.get("GBP").getAsJsonObject();
                String rate = jsMoney.get("rate").getAsString();
                embedBuilder.addField("GBP", "£" + rate.substring(0, rate.length() - 2), true);
            }
            if (!jsBPI.get("EUR").isJsonNull()) {
                JsonObject jsMoney = jsBPI.get("EUR").getAsJsonObject();
                String rate = jsMoney.get("rate").getAsString();
                embedBuilder.addField("EUR", "€" + rate.substring(0, rate.length() - 2), true);
            }

            embedBuilder.addBlankField(false);

            if (!jsTime.get("updated").isJsonNull()) {
                embedBuilder.addField("Last Updated", jsTime.get("updated").getAsString(), false);
            }
            if (!js.get("disclaimer").isJsonNull()) {
                embedBuilder.addField("Disclaimer", js.get("disclaimer").getAsString(), false);
            }

            event.getTextChannel().sendMessage(embedBuilder.build()).queue();

        } catch (NullPointerException e) {
            event.getTextChannel().sendMessage("Error: Could not retrieve Bitcoin data.").queue();
            e.printStackTrace();
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

    @Override
    public String getName() {
        return "bitcoin";
    }

    @Override
    public String getDesc() {
        return "Get the [hourly] value of Bitcoin.";
    }

    @Override
    public String getUsage() {
        return "bitcoin";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
