package com.miningmark48.pearcelbot.util.JSON;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import java.io.*;

public class JSONWrite {

    public static String fileName = "config.json";

    public static void init(){


        try{

            File file = new File(fileName);

            if(!file.exists()) {

                Writer writer = new OutputStreamWriter(new FileOutputStream(fileName) , "UTF-8");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonWriter jw = gson.newJsonWriter(writer);

                jw.beginObject();

                jw.name("botname").value("Pearcel Bot");
                jw.name("token").value("PLACE YOUR TOKEN HERE");
                jw.name("key").value("~");
                jw.name("client id").value("PLACE CLIENT ID HERE");
                jw.name("bot commander role").value("PBC");
                jw.name("bot autoresponse role").value("PBAR");
                jw.name("do chat log").value(true);

                jw.endObject();
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
