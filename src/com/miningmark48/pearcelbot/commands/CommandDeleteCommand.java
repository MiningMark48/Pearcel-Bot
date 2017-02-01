package com.miningmark48.pearcelbot.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.miningmark48.pearcelbot.Command;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.JSONParseFile;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;

public class CommandDeleteCommand implements Command{

    public static final String desc = "Delete a custom command for your server that was previously added.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "deletecommand <arg-command name>";
    public static final String info = desc + " " + usage;

    public static String fileName = "custom_commands.json";

    BufferedWriter bufferedWriter = null;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        event.getTextChannel().sendMessage("Disabled for revisions! Sorry for any inconvenience.").queue();

//        if (event.getMember().getRoles().toString().contains(Reference.botCommanderRole) || event.getGuild().getOwner() == event.getMember() || event.getAuthor().getId().equals(Reference.botOwner)){
//
//            String command = "";
//
//            if (args.length > 0){
//                command = args[0];
//            }
//
//            try{
//
//                File file = new File(fileName);
//
//                if(file.exists()) {
//
//                    JsonObject jsonObj = JSONParseFile.JSONParse(fileName);
//
//                    Writer writer = new OutputStreamWriter(new FileOutputStream(fileName) , "UTF-8");
//                    bufferedWriter = new BufferedWriter(writer);
//
//                    JsonObject newJson = jsonObj.getAsJsonObject(event.getGuild().getId());
//                    if(newJson.get(command) != null) {
//                        newJson.remove(command);
//                        event.getTextChannel().sendMessage("Removed the command : **" + command + "**").queue();
//                    }else{
//                        event.getTextChannel().sendMessage("**Error:** Command does not exist!").queue();
//                    }
//
//                    bufferedWriter.write(jsonObj.toString());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (bufferedWriter != null){
//                        bufferedWriter.close();
//                    }
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
//
//        }else{
//            event.getTextChannel().sendMessage(event.getAuthor().getName() + ", You do not have permission to run that command, use " + Reference.botCommandKey + "voteskip instead.").queue();
//        }


    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

}
