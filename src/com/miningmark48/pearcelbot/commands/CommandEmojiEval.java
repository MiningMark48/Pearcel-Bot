package com.miningmark48.pearcelbot.commands;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringEscapeUtils;

public class CommandEmojiEval implements ICommand, ICommandInfo {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String str = (String)args[0];
        if(str.matches("<:.*:\\d+>"))
        {
            String id = str.replaceAll("<:.*:(\\d+)>", "$1");
            Emote emote = event.getJDA().getEmoteById(id);
            if(emote == null)
            {
                event.getTextChannel().sendMessage("Unknown emoji:\n"
                        +"ID: **"+id+"**\n"
                        +"Guild: Unknown\n"
                        +"URL: https://discordcdn.com/emojis/"+id+".png").queue();
            }
            event.getTextChannel().sendMessage("Emoji **"+emote.getName()+"**:\n"
                    +"ID: **"+emote.getId()+"**\n"
                    +"Guild: "+(emote.getGuild()==null ? "Unknown" : "**"+emote.getGuild().getName()+"**")+"\n"
                    +"URL: "+emote.getImageUrl()).queue();
        }
        if(str.codePoints().count()>10)
        {
            event.getTextChannel().sendMessage("Invalid emoji or input is too long.").queue();
            return;
        }
        StringBuilder builder = new StringBuilder("**Emoji Evaluation:**\n");
        str.codePoints().forEachOrdered(code -> {
            char[] chars = Character.toChars(code);
            String hex = Integer.toHexString(code).toUpperCase();
            while(hex.length()<4)
                hex = "0" + hex;
            builder.append("\n").append("`\\u").append(hex).append("`   ");
            if(chars.length > 1)
            {
                String hex0 = Integer.toHexString(chars[0]).toUpperCase();
                String hex1 = Integer.toHexString(chars[1]).toUpperCase();
                while(hex0.length()<4)
                    hex0 = "0" + hex0;
                while(hex1.length()<4)
                    hex1 = "0" + hex1;

                builder.append("[`\\u").append(hex0).append("\\u").append(hex1).append("`]   ").append("`" + StringEscapeUtils.unescapeJava("\\u" + hex0 + "\\u" + hex1) + "` ");

            }
            builder.append(String.valueOf(chars)).append("   _").append(Character.getName(code)).append("_");
        });
        event.getTextChannel().sendMessage(builder.toString()).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "emojieval";
    }

    @Override
    public String getDesc() {
        return "Evaluate an emoji.";
    }

    @Override
    public String getUsage() {
        return "emojieval <arg:emoji>";
    }

    @Override
    public CommandType getType() {
        return CommandType.NORMAL;
    }
}
