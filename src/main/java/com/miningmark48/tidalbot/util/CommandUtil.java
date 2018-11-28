package com.miningmark48.tidalbot.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import com.miningmark48.tidalbot.Main;
import com.miningmark48.tidalbot.commands.base.ICommandInfo;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

public class CommandUtil {

    public static void createCommandFile() {
        LoggerUtil.log(LoggerUtil.LogType.STATUS, "Creating command file...");

        try {
            String configFile = "commands.json";
            File file = new File(configFile);

            if (!file.exists()) {
                Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonWriter jw = gson.newJsonWriter(writer);

                jw.beginArray();

                Main.commands.forEach((key, value) -> {

                    if (value instanceof ICommandInfo) {
                        ICommandInfo cmd = (ICommandInfo) value;
                        if (cmd.getName().equalsIgnoreCase(key)) {
                            try {
                                jw.beginObject();
                                jw.name("name").value(((ICommandInfo) value).getName());
                                jw.name("type").value(StringUtils.capitalize(((ICommandInfo) value).getType().toString().toLowerCase()));
                                jw.name("usage").value(((ICommandInfo) value).getUsage());
                                jw.name("action").value(StringUtils.capitalize(((ICommandInfo) value).getDesc()));
                                jw.endObject();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                jw.endArray();

                writer.close();

                LoggerUtil.log(LoggerUtil.LogType.STATUS, "Commands file was created.");
            } else {

                try {

                    file.delete();

                    LoggerUtil.log(LoggerUtil.LogType.STATUS, "Found existing command file, overwriting...");

                    createCommandFile();

                } catch (NullPointerException e) {
                    LoggerUtil.log(LoggerUtil.LogType.FATAL, "An issue has occurred.");
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
