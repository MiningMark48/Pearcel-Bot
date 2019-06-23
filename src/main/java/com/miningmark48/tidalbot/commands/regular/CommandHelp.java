package com.miningmark48.tidalbot.commands.regular;

import com.miningmark48.tidalbot.Main;
import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.util.UtilData;
import com.miningmark48.tidalbot.util.UtilFormat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Objects;

public class CommandHelp implements ICommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length > 0) {
            if (Main.commands.entrySet().stream().anyMatch(q -> q.getKey().equalsIgnoreCase(args[0]))) {
                ICommand command = (Objects.requireNonNull(Main.commands.entrySet().stream().filter(q -> q.getKey().equalsIgnoreCase(args[0])).findFirst().orElse(null)).getValue());
                if (command != null) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(Color.decode("#a2f000"));
                    builder.setAuthor(command.getName());
                    builder.setDescription(String.format("%s\n\n**USAGE:** %s\n\n **TYPE:** %s\n %s", command.getDesc(), command.getUsage(), command.getPermissionRequired().getName(), (command.getPermissionRequired().equals(EnumRestrictions.SPECIFIC) ? String.format("**PERMS:** %s", UtilData.toPermCSV(command.getPermissions())) : "")));
                    event.getTextChannel().sendMessage(builder.build()).queue();
                }
            }
        } else {
            event.getTextChannel().sendMessage("Missing arguments!").queue();
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDesc() {
        return "Get help for a specific command.";
    }

    @Override
    public String getUsage() {
        return "help <arg:string-command_name>";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.REGULAR;
    }

}
