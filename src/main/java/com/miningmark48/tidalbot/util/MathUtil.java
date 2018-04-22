package com.miningmark48.tidalbot.util;

public class MathUtil {

    public static String getTimeFromLong(Long ms){
        long secs = (ms / 1000) % 60 ;
        long mins = ((ms / (1000*60)) % 60);
        long hours = ((ms / (1000*60*60)) % 24);
        long days =  ((ms / (1000*60*60*24)) % 24);

        return (days > 0 ? (days + "d ") : "") + (hours > 0 ? (hours + "h ") : "") + (mins > 0 ? (mins + "m ") : "") + (secs > 0 ? (secs + "s") : "");
    }

    public static String getTimeFromLongNoFormatShort(Long ms){
        long secs = (ms / 1000) % 60 ;
        long mins = ((ms / (1000*60)) % 60);
        long hours = ((ms / (1000*60*60)) % 24);

        return (hours > 0 ? ((hours > 9 ? (hours) : "0" + hours)) : "00") + ":" + (mins > 0 ? ((mins > 9 ? (mins) : "0" + mins)) : "00") + ":" + (secs > 0 ? ((secs > 9 ? (secs) : "0" + secs)) : "00");

    }

    public static String getTimeFromLongNoFormat(Long ms){
        long secs = (ms / 1000) % 60 ;
        long mins = ((ms / (1000*60)) % 60);
        long hours = ((ms / (1000*60*60)) % 24);
        long days =  ((ms / (1000*60*60*24)) % 24);

        return (days > 0 ? ((days > 9 ? (days) : "0" + days)) : "00") + ":" + (hours > 0 ? ((hours > 9 ? (hours) : "0" + hours)) : "00") + ":" + (mins > 0 ? ((mins > 9 ? (mins) : "0" + mins)) : "00") + ":" + (secs > 0 ? ((secs > 9 ? (secs) : "0" + secs)) : "00");

    }

}
