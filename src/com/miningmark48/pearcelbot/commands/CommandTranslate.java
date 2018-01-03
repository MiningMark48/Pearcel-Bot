package com.miningmark48.pearcelbot.commands;

import com.google.gson.JsonObject;
import com.miningmark48.pearcelbot.ICommand;
import com.miningmark48.pearcelbot.ICommandPrivate;
import com.miningmark48.pearcelbot.messages.MessageGeneral;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.JSON.JSONParse;
import com.miningmark48.pearcelbot.util.MessageHelper;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandTranslate implements ICommand, ICommandPrivate {

    public static final String desc = "Translate text";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "translate <arg>";
    public static final String info = desc + " " + usage;

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
            message = event.getMessage().getContent().substring(16);
            languageCode = args[0];
        } else {
            message = event.getMessage().getContent().substring(11);
            languageCode = "en-es";
        }
        String text = message.replace(" ", "+");

        if (args.length <= 0) {
            MessageHelper.sendMessage(event, "Missing Arguments!", isPrivate);
        } else {
            js = JSONParse.JSONParse("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20160905T061211Z.a343ec6cd67c26fa.e72589fa4bb315cdd7d5b894e7b3ad76c50f2180&text=" + text + "&lang=" + languageCode);
            if (event.getAuthor() != null) {
                MessageHelper.sendMessage(event, String.format("Here you go %s!", event.getAuthor().getAsMention()), isPrivate);
            }
            MessageHelper.sendMessage(event, "**Translated**_\"" + message + "_ \" (**" + languageCode + "**) to:", isPrivate);
            MessageHelper.sendMessage(event, js.get("text").getAsJsonArray().get(0).getAsString(), isPrivate);
            event.getMessage().delete().queue();
        }

    }

}
