package com.miningmark48.pearcelbot.util;

public class Logger{

    public static void log(String par1, String message){
        System.out.printf("[%s] %s\n", par1.toUpperCase(), message);
    }

    public static void log(LogType type, String message){
        System.out.printf("[%s] %s\n", type.name().toUpperCase(), message);
    }

    public enum LogType{
        INFO,
        DEBUG,
        WARN,
        FATAL,
        STATUS
    }

}
