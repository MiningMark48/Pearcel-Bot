package com.miningmark48.tidalbot.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import com.miningmark48.tidalbot.Main;
import com.miningmark48.tidalbot.base.ICommand;
import com.miningmark48.tidalbot.util.comparators.ComparatorCommandName;
import com.miningmark48.tidalbot.util.comparators.ComparatorCommandType;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;

public class UtilCommandFile {

    public static void createCommandFile() {
        UtilLogger.log(UtilLogger.LogType.STATUS, "Creating command file...");

        try {
            String configFile = "commands.json";
            File file = new File(configFile);

            if (!file.exists()) {
                Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonWriter jw = gson.newJsonWriter(writer);

                jw.beginArray();

                ArrayList<ICommand> commands = new ArrayList<>();

                Main.commands.forEach((key, value) -> commands.add(value));

                commands.sort(new ComparatorCommandName());
                commands.sort(new ComparatorCommandType());

                commands.forEach(cmd -> {
                    if (cmd != null) {
                        try {
                            jw.beginObject();
                            jw.name("name").value(cmd.getName());
                            jw.name("type").value(StringUtils.capitalize(cmd.getPermissionRequired().getName()));
                            jw.name("usage").value(cmd.getUsage());
                            jw.name("action").value(StringUtils.capitalize(cmd.getDesc()));
                            jw.endObject();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                jw.endArray();

                writer.close();

                UtilLogger.log(UtilLogger.LogType.STATUS, "Commands file was created.");
            } else {

                try {

                    file.delete();

                    UtilLogger.log(UtilLogger.LogType.STATUS, "Found existing command file, overwriting...");

                    createCommandFile();

                } catch (NullPointerException e) {
                    UtilLogger.log(UtilLogger.LogType.FATAL, "An issue has occurred.");
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
