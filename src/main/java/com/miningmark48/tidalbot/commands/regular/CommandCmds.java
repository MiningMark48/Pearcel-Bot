package com.miningmark48.tidalbot.commands.regular;

import com.google.common.collect.Lists;
import com.miningmark48.tidalbot.Main;
import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.base.ICommandPrivate;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.UtilData;
import com.miningmark48.tidalbot.util.UtilFormat;
import com.miningmark48.tidalbot.util.UtilLogger;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.RestAction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandCmds implements ICommand, ICommandPrivate {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        getCmds(event, false);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public void actionPrivate(String[] args, MessageReceivedEvent event) {
        UtilLogger.log(UtilLogger.LogType.INFO, "CALLED");
        getCmds(event, true);
    }

    @Override
    public String getName() {
        return "cmds";
    }

    @Override
    public String getDesc() {
        return "Returns a list of commands.";
    }

    @Override
    public String getUsage() {
        return getName();
    }

    private static void getCmds(MessageReceivedEvent event, boolean isPrivate) {
        event.getAuthor();

        RestAction<PrivateChannel> privateChannel = event.getAuthor().openPrivateChannel();
        if (!isPrivate) {
            event.getAuthor();
            event.getTextChannel().sendMessage("**" + event.getAuthor().getAsMention() + "**, Sending you a list of commands now. Optionally, visit <https://miningmark48.github.io/tidalwave/projects/tidalbot/>.").queue();
        }

        ArrayList<ICommand> commands = new ArrayList<>();

        Main.commands.forEach((key, value) -> {
            if (value != null) {
                if (value.getName().equalsIgnoreCase(key)) {
                    commands.add(value);
                }
            }
        });

        int chunkSize = 15;
        int msgDelay = 1000;
        privateChannel.queue(chan -> {
            //Sort everything
            commands.sort(new ComparatorCommandName());
            commands.sort(new ComparatorCommandType());

            List<List<ICommand>> cmdPartition = Lists.partition(commands, chunkSize);

            chan.sendMessage(UtilFormat.formatText(UtilFormat.FormatType.BOLD,"Commands:")).queue();
            cmdPartition.forEach(cmdChunk -> {
                //Sort partitions
                cmdChunk.sort(new ComparatorCommandName());
                cmdChunk.sort(new ComparatorCommandType());

                StringBuilder builder = new StringBuilder();
                builder.append("```");
                cmdChunk.forEach(q -> builder.append(String.format("[%s] %s - %s (Usage: %s)", q.getPermissionRequired().getName().toUpperCase(), Reference.botCommandKey + q.getName(), q.getDesc(), q.getUsage())).append(q.getPermissionRequired().equals(EnumRestrictions.SPECIFIC) ? String.format(" - PERMS: %s\n", UtilData.toPermCSV(q.getPermissions())) : "\n"));
                builder.append(String.format("``` **[**%s**/**%s**]**\n\n", cmdPartition.indexOf(cmdChunk) + 1, cmdPartition.size()));
                chan.sendMessage(builder).queueAfter(msgDelay + 500 * cmdPartition.indexOf(cmdChunk), TimeUnit.MILLISECONDS);
            });

            chan.sendMessage("Use `" + Reference.botCommandKey + "help <command_name>` for help with a specific command.").queueAfter(msgDelay + 500 * (cmdPartition.size() + 1), TimeUnit.MILLISECONDS);
            chan.sendMessage("Optionally, visit <https://miningmark48.github.io/tidalwave/projects/tidalbot/> for a list of commands.").queueAfter(msgDelay + 500 * (cmdPartition.size() + 2), TimeUnit.MILLISECONDS);
        });
    }


}

class ComparatorCommandType implements Comparator<ICommand> {

    @Override
    public int compare(ICommand a, ICommand b) {
        return a.getPermissionRequired().getName().compareToIgnoreCase(b.getPermissionRequired().getName());
    }

}

class ComparatorCommandName implements Comparator<ICommand> {

    @Override
    public int compare(ICommand a, ICommand b) {
        return a.getName().compareToIgnoreCase(b.getName());
    }

}
