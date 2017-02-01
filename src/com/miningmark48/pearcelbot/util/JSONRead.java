package com.miningmark48.pearcelbot.util;

import com.google.gson.stream.JsonReader;
import com.miningmark48.pearcelbot.reference.Reference;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONRead {

    public static String botName;
    public static String botToken;
    public static String botCommandKey;
    public static String botClientID;
    public static String botCommanderRole;
    public static String botAutoResponseRole;
    public static boolean doChatLog;

    public static void init(){

        try {

            JsonReader reader = new JsonReader(new FileReader(JSONWrite.fileName));

            reader.beginObject();
            while (reader.hasNext()){
                String name = reader.nextName();

                if(name.equals("botname")){
                    botName = reader.nextString();
                }else if(name.equals("token")){
                    botToken = reader.nextString();
                }else if(name.equals("key")){
                    botCommandKey = reader.nextString();
                }else if(name.equals("client id")){
                    botClientID = reader.nextString();
                }else if(name.equals("bot commander role")){
                    botCommanderRole = reader.nextString();
                }else if(name.equals("bot autoresponse role")){
                    botAutoResponseRole = reader.nextString();
                }else if(name.equals("do chat log")){
                    doChatLog = reader.nextBoolean();
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
