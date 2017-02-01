package com.miningmark48.pearcelbot.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.TextInputControl;
import org.apache.commons.io.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class JSONParse {

    public static JsonObject JSONParse(String sUrl){

        try {
            URL url = new URL(sUrl);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject rootObj = root.getAsJsonObject();

            return rootObj;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static JsonObject JSONParseTwitch(String sUrl){

        try {
            URL url = new URL(sUrl);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            String body = IOUtils.toString(in, encoding);
            body = body.replace("/**/foo(", "");
            body = body.replace(")", "");
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(body);
            JsonObject rootObj = root.getAsJsonObject();

            return rootObj;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}
