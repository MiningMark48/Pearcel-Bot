package com.miningmark48.pearcelbot.util.JSON;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class JSONParseFile {

    public static JsonObject JSONParse(String file){
        try {

            JsonParser jp = new JsonParser();
            InputStream inputStream = new FileInputStream(file);
            JsonElement root = jp.parse(new InputStreamReader(inputStream));

            return root.getAsJsonObject();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}
