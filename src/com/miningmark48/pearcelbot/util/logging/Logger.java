package com.miningmark48.pearcelbot.util;

import com.miningmark48.pearcelbot.util.enums.LogType;

public class Logger{

    public static void log(String par1, String message){
        System.out.printf("[%s] %s\n", par1.toUpperCase(), message);
    }

    public static void log(LogType type, String message){
        System.out.printf("[%s] %s\n", type.name().toUpperCase(), message);
    }



}
