package com.miningmark48.tidalbot.commands.commander;

import com.google.gson.JsonObject;
import com.miningmark48.tidalbot.base.CommandType;
import com.miningmark48.tidalbot.base.EnumRestrictions;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.util.JSON.JSONParseFile;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.*;

public class CommandDeleteCommand implements ICommand {

    private static String fileName = "custom_commands.json";

    BufferedWriter bufferedWriter = null;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String command = "";
        if (args.length > 0){
            command = args[0];
        }
        try{
            File file = new File(fileName);
            if(file.exists()) {
                JsonObject jsonObj = JSONParseFile.JSONParse(fileName);
                Writer writer = new OutputStreamWriter(new FileOutputStream(fileName) , "UTF-8");
                bufferedWriter = new BufferedWriter(writer);
                JsonObject newJson = jsonObj.getAsJsonObject(event.getGuild().getId());
                if(newJson.get(command) != null) {
                    newJson.remove(command);
                    event.getTextChannel().sendMessage("Removed the command : **" + command + "**").queue();
                }else{
                    event.getTextChannel().sendMessage("**Error:** ICommand does not exist!").queue();
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
    public String getName() {
        return "deletecommand";
    }

    @Override
    public String getDesc() {
        return "Delete a custom command for your server that was previously added.";
    }

    @Override
    public String getUsage() {
        return "deletecommand <arg:string-command name>";
    }

    @Override
    public EnumRestrictions getPermissionRequired() {
        return EnumRestrictions.MANAGER;
    }
}
