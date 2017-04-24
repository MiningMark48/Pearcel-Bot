package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.Main;
import com.miningmark48.pearcelbot.commands.pbc.*;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.Logger;

public class InitializeCommands {

    public static void init(){

        Main.commands.clear();
        Reference.commandUsage.clear();
        Reference.commandUsage2.clear();
        Reference.commandUsagePBC.clear();

        //Init
        Main.commands.put("8ball", new Command8Ball());
        Main.commands.put("addcommand", new CommandAddCommand());
        Main.commands.put("announce", new CommandAnnounce());
        Main.commands.put("bark", new CommandBark());
        Main.commands.put("botinfo", new CommandBotInfo());
        Main.commands.put("cmds", new CommandCmds());
        Main.commands.put("coinflip", new CommandCoinFlip());
        Main.commands.put("deletecommand", new CommandDeleteCommand());
        Main.commands.put("diceroll", new CommandDiceRoll());
        Main.commands.put("editcommand", new CommandEditCommand());
        Main.commands.put("emojieval", new CommandEmojiEval());
        Main.commands.put("geoip", new CommandGeoIP());
        Main.commands.put("githubuser", new CommandGithubUser());
        Main.commands.put("guildinfo", new CommandGuildInfo());
        Main.commands.put("listbots", new CommandListBots());
        Main.commands.put("listcommands", new CommandListCommands());
        Main.commands.put("lmgtfy", new CommandLMGTFY());
        Main.commands.put("nowplaying", new CommandNowPlaying());
        Main.commands.put("patreon", new CommandPatreon());
        Main.commands.put("ping", new CommandPing());
        Main.commands.put("poll", new CommandPoll());
        Main.commands.put("polln", new CommandPollN());
        Main.commands.put("rps", new CommandRPS());
        Main.commands.put("say", new CommandSay());
        Main.commands.put("selfinfo", new CommandSelfInfo());
        Main.commands.put("serverinvite", new CommandServerInvite());
        Main.commands.put("slap", new CommandSlap());
        Main.commands.put("sourcecode", new CommandSourceCode());
        Main.commands.put("tobinary", new CommandToBinary());
        Main.commands.put("toromannum", new CommandToRomanNum());
        Main.commands.put("translate", new CommandTranslate());
        Main.commands.put("twitchuser", new CommandTwitchUser());
        Main.commands.put("uptime", new CommandUptime());
        Main.commands.put("volume", new CommandVolume());
        Main.commands.put("voteskip", new CommandVoteSkip());
        Main.commands.put("youtubeuser", new CommandYoutubeUser());

        Main.commands.put("getdemservs", new CommandGetDemServs());

        //Alias
        Main.commands.put("help", new CommandCmds());
        Main.commands.put("addcmd", new CommandAddCommand());
        Main.commands.put("delcmd", new CommandDeleteCommand());
        Main.commands.put("listcmds", new CommandListCommands());

        //Cmd Info
            //Normal
        Reference.commandUsage.put("8ball", Command8Ball.info);
        Reference.commandUsage.put("bark", CommandBark.info);
        Reference.commandUsage.put("botinfo", CommandBotInfo.info);
        Reference.commandUsage.put("cmds", CommandCmds.info);
        Reference.commandUsage.put("coinflip", CommandCoinFlip.info);
        Reference.commandUsage.put("diceroll", CommandDiceRoll.info);
        Reference.commandUsage.put("emojieval", CommandEmojiEval.info);
        Reference.commandUsage.put("geoip", CommandGeoIP.info);
        Reference.commandUsage.put("githubuser", CommandGithubUser.info);
        Reference.commandUsage.put("guildinfo", CommandGuildInfo.info);
        Reference.commandUsage.put("listbots", CommandListBots.info);
        Reference.commandUsage.put("listcommands", CommandListCommands.info);
        Reference.commandUsage.put("lmgtfy", CommandLMGTFY.info);
        Reference.commandUsage.put("patreon", CommandPatreon.info);
        Reference.commandUsage.put("ping", CommandPing.info);
        Reference.commandUsage.put("poll", CommandPoll.info);
        Reference.commandUsage.put("polln", CommandPollN.info);
        Reference.commandUsage.put("rps", CommandRPS.info);
        Reference.commandUsage.put("slap", CommandSlap.info);
        Reference.commandUsage.put("selfinfo", CommandSelfInfo.info);
        Reference.commandUsage.put("serverinvite", CommandServerInvite.info);
        Reference.commandUsage.put("sourcecode", CommandSourceCode.info);
        Reference.commandUsage.put("tobinary", CommandToBinary.info);
        Reference.commandUsage.put("toromannum", CommandToRomanNum.info);
        Reference.commandUsage.put("translate", CommandTranslate.info);
        Reference.commandUsage.put("twitchuser", CommandTwitchUser.info);
        Reference.commandUsage.put("uptime", CommandUptime.info);
        Reference.commandUsage.put("youtubeuser", CommandYoutubeUser.info);
            //PBC and Owner
        Reference.commandUsagePBC.put("addcommand", CommandAddCommand.info);
        Reference.commandUsagePBC.put("announce", CommandAnnounce.info);
        Reference.commandUsagePBC.put("deletecommand", CommandDeleteCommand.info);
        Reference.commandUsagePBC.put("editcommand", CommandEditCommand.info);
        Reference.commandUsagePBC.put("say", CommandSay.info);

        Logger.log("status", "Commands Initialized.");

    }

}

