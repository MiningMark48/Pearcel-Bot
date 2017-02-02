package com.miningmark48.pearcelbot.reference;

import com.miningmark48.pearcelbot.util.JSONRead;

import java.util.HashMap;

public class Reference {

    public static String botVersion = "0.0.1";
    public static String botAuthor = "MiningMark48";
    public static String botOwner = "138819223932633088";                       //Owner ID

    public static String botToken = JSONRead.botToken;
    public static String botName = JSONRead.botName;
    public static String botCommandKey = JSONRead.botCommandKey;
    public static String botCommandCustomKey = botCommandKey + "`";
    public static String botCommanderRole = JSONRead.botCommanderRole;
    public static String botAutoResponseRole = JSONRead.botAutoResponseRole;
    public static String botClientID = JSONRead.botClientID;
    public static boolean doChatLog = JSONRead.doChatLog;

    private static int perms = 70351936;                                        //Perms Int (Discord Calc)
    public static String joinLink = "https://discordapp.com/oauth2/authorize?&client_id="+ botClientID +"&scope=bot&permissions=" + String.valueOf(perms);
    public static HashMap<String, String> commandUsage = new HashMap<>();
    public static HashMap<String, String> commandUsage2 = new HashMap<>();
    public static HashMap<String, String> commandUsagePBC = new HashMap<>();

}
