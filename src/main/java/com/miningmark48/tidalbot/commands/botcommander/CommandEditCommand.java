package com.miningmark48.tidalbot.commands.botcommander;

import com.google.gson.JsonObject;
import com.miningmark48.tidalbot.commands.base.CommandType;
import com.miningmark48.tidalbot.commands.base.ICommand;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import com.miningmark48.tidalbot.util.JSON.JSONParseFile;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;

public class CommandEditCommand implements ICommand, ICommandInfo {

    public static String fileName = "custom_commands.json";

    BufferedWriter bufferedWriter = null;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String commandMessage = "";
        String command = "";
        if (args.length > 0){
            command = args[0];
            for (int i = 2; i <= args.length; i++){
                commandMessage = commandMessage + args[i - 1] + " ";
                commandMessage = commandMessage.substring(0, 1).toUpperCase() + commandMessage.substring(1);
            }
        }

        try{
            File file = new File(fileName);
            if(!file.exists()) {
                event.getTextChannel().sendMessage("ERROR").queue();
            }else{
                JsonObject jsonObj = JSONParseFile.JSONParse(fileName);
                Writer writer = new OutputStreamWriter(new FileOutputStream(fileName) , "UTF-8");
                bufferedWriter = new BufferedWriter(writer);
                JsonObject newJson = jsonObj.getAsJsonObject(event.getGuild().getId());
                if(newJson.get(command) != null) {
                    newJson.remove(command);
                    newJson.addProperty(command, commandMessage);
                    event.getTextChannel().sendMessage("Edited the command **" + command + "** to: " + commandMessage).queue();
                }else{
                    event.getTextChannel().sendMessage("**Error:** Command does not exist!").queue();
                }
                bufferedWriter.write(jsonObj.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null){
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public boolean isRestricted() {
        return true;
    }

    @Override
    public String getName() {
        return "editcommand";
    }

    @Override
    public String getDesc() {
        return "Edit a custom command that was previously added.";
    }

    @Override
    public String getUsage() {
        return "editcommand <arg:string-command name> <arg:string-message>";
    }

    @Override
    public CommandType getType() {
        return CommandType.BC;
    }
}
