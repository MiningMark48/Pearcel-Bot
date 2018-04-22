package com.miningmark48.pearcelbot.commands;

import com.google.gson.JsonObject;
import com.miningmark48.pearcelbot.commands.base.CommandType;
import com.miningmark48.pearcelbot.commands.base.ICommand;
import com.miningmark48.pearcelbot.commands.base.ICommandInfo;
import com.miningmark48.pearcelbot.commands.base.ICommandPrivate;
import com.miningmark48.pearcelbot.util.JSON.JSONParse;
import com.miningmark48.pearcelbot.util.MessageUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandTranslate implements ICommand, ICommandPrivate, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        doCmd(event, args, false);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public void actionPrivate(String[] args, MessageReceivedEvent event) {
        doCmd(event, args, true);
    }

    private static void doCmd(MessageReceivedEvent event, String[] args, boolean isPrivate) {

        // KEY trnsl.1.1.20160905T061211Z.a343ec6cd67c26fa.e72589fa4bb315cdd7d5b894e7b3ad76c50f2180
        JsonObject js;

        String message;
        String languageCode;

        if (args[0].contains("-") && args[0].length() == 5) {
            message = event.getMessage().getContentRaw().substring(16);
            languageCode = args[0];
        } else {
            message = event.getMessage().getContentRaw().substring(11);
            languageCode = "en-es";
        }
        String text = message.replace(" ", "+");

        if (args.length <= 0) {
            MessageUtil.sendMessage(event, "Missing Arguments!", isPrivate).queue();
        } else {
            js = JSONParse.JSONParse("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20160905T061211Z.a343ec6cd67c26fa.e72589fa4bb315cdd7d5b894e7b3ad76c50f2180&text=" + text + "&lang=" + languageCode);
            if (event.getAuthor() != null) {
                MessageUtil.sendMessage(event, String.format("Here you go %s!", event.getAuthor().getAsMention()), isPrivate).queue();
            }
            MessageUtil.sendMessage(event, "**Translated**_\"" + message + "_ \" (**" + languageCode + "**) to:", isPrivate).queue();
            MessageUtil.sendMessage(event, js.get("text").getAsJsonArray().get(0).getAsString(), isPrivate).queue();
            event.getMessage().delete().queue();
        }

    }

    @Override
    public String getName() {
        return "translate";
    }

    @Override
    public String getDesc() {
        return "Translate text to another language.";
    }

    @Override
    public String getUsage() {
        return "translate <arg:language-code>";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
