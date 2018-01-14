package com.miningmark48.pearcelbot.util;

public class FormatUtil {

    public static String bold(String text){
        return "**" + text + "**";
    }

    public static String italicize(String text){
        return "*" + text + "*";
    }

    public static String strikethrough(String text){
        return "~~" + text + "~~";
    }

    public static String underline(String text){
        return "__" + text + "__";
    }

    public static String makeURL(String displayText, String url){
        return "[" + displayText + "]" + "(" + url + ")";
    }

}
