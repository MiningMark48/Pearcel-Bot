package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.Main;
import com.miningmark48.pearcelbot.commands.music.*;
import com.miningmark48.pearcelbot.commands.music.role_block.*;
import com.miningmark48.pearcelbot.commands.music.soundboard.CommandSoundboard;
import com.miningmark48.pearcelbot.commands.pbc.*;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.Logger;

import java.sql.Ref;

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
//        Main.commands.put("bitcoin", new CommandBitcoin());
        Main.commands.put("arblacklist", new CommandARBlacklist());
        Main.commands.put("botinfo", new CommandBotInfo());
        Main.commands.put("botstats", new CommandBotStats());
        Main.commands.put("cmds", new CommandCmds());
        Main.commands.put("coinflip", new CommandCoinFlip());
        Main.commands.put("cursedata", new CommandCurseData());
        Main.commands.put("deletecommand", new CommandDeleteCommand());
        Main.commands.put("diceroll", new CommandDiceRoll());
        Main.commands.put("editcommand", new CommandEditCommand());
        Main.commands.put("emojieval", new CommandEmojiEval());
        Main.commands.put("fizzbuzz", new CommandFizzbuzz());
        Main.commands.put("geoip", new CommandGeoIP());
        Main.commands.put("githubuser", new CommandGithubUser());
        Main.commands.put("guildinfo", new CommandGuildInfo());
        Main.commands.put("listbots", new CommandListBots());
        Main.commands.put("listcommands", new CommandListCommands());
        Main.commands.put("lmgtfy", new CommandLMGTFY());
        Main.commands.put("mixeruser", new CommandMixerUser());
        Main.commands.put("numtoword", new CommandNumberToWords());
        Main.commands.put("patreon", new CommandPatreon());
        Main.commands.put("ping", new CommandPing());
        Main.commands.put("poll", new CommandPoll());
        Main.commands.put("prune", new CommandPrune());
        Main.commands.put("roles", new CommandRoles());
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
        Main.commands.put("youtubeuser", new CommandYoutubeUser());

        //Me only
        Main.commands.put("getdemservs", new CommandGetDemServs());

        //Music
        Main.commands.put("play", new CommandPlay());
        Main.commands.put("pplay", new CommandPlayPlaylist());
        Main.commands.put("splay", new CommandPlaySearch());
        Main.commands.put("splayrand", new CommandPlaySearchRand());
        Main.commands.put("splayremix", new CommandPlaySearchRemix());
        Main.commands.put("skip", new CommandSkip());
        Main.commands.put("stop", new CommandStop());
        Main.commands.put("queue", new CommandQueue());
        Main.commands.put("pause", new CommandPause());
        Main.commands.put("playnext", new CommandPlayNext());
        Main.commands.put("remove", new CommandRemove());
        Main.commands.put("repeat", new CommandRepeat());
        Main.commands.put("repeatonce", new CommandRepeatOnce());
        Main.commands.put("resume", new CommandResume());
        Main.commands.put("nowplaying", new CommandNowPlaying());
        Main.commands.put("shuffle", new CommandShuffle());
        Main.commands.put("lyrics", new CommandLyrics());
        Main.commands.put("restart", new CommandRestart());
        Main.commands.put("soundboard", new CommandSoundboard());

        //Alias
        Main.commands.put("help", new CommandCmds());
        Main.commands.put("addcmd", new CommandAddCommand());
        Main.commands.put("curseforge", new CommandCurseData());
        Main.commands.put("delcmd", new CommandDeleteCommand());
        Main.commands.put("listcmds", new CommandListCommands());
            //Music
        Main.commands.put("np", new CommandNowPlaying());
        Main.commands.put("sb", new CommandSoundboard());

        //Cmd Info
            //Normal
        Reference.commandUsage.put("8ball", Command8Ball.info);
        Reference.commandUsage.put("bark", CommandBark.info);
//        Reference.commandUsage.put("bitcoin", CommandBitcoin.info);
        Reference.commandUsage.put("arblacklist", CommandARBlacklist.info);
        Reference.commandUsage.put("botinfo", CommandBotInfo.info);
        Reference.commandUsage.put("botstats", CommandBotStats.info);
        Reference.commandUsage.put("cmds", CommandCmds.info);
        Reference.commandUsage.put("coinflip", CommandCoinFlip.info);
        Reference.commandUsage.put("cursedata", CommandCurseData.info);
        Reference.commandUsage.put("diceroll", CommandDiceRoll.info);
        Reference.commandUsage.put("emojieval", CommandEmojiEval.info);
        Reference.commandUsage.put("fizzbuzz", CommandFizzbuzz.info);
        Reference.commandUsage.put("geoip", CommandGeoIP.info);
        Reference.commandUsage.put("githubuser", CommandGithubUser.info);
        Reference.commandUsage.put("guildinfo", CommandGuildInfo.info);
        Reference.commandUsage.put("listbots", CommandListBots.info);
        Reference.commandUsage.put("listcommands", CommandListCommands.info);
        Reference.commandUsage.put("lmgtfy", CommandLMGTFY.info);
        Reference.commandUsage.put("mixeruser", CommandMixerUser.info);
        Reference.commandUsage.put("numtoword", CommandNumberToWords.info);
        Reference.commandUsage.put("patreon", CommandPatreon.info);
        Reference.commandUsage2.put("ping", CommandPing.info);
        Reference.commandUsage2.put("poll", CommandPoll.info);
        Reference.commandUsage2.put("roles", CommandRoles.info);
        Reference.commandUsage2.put("rps", CommandRPS.info);
        Reference.commandUsage2.put("slap", CommandSlap.info);
        Reference.commandUsage2.put("selfinfo", CommandSelfInfo.info);
        Reference.commandUsage2.put("serverinvite", CommandServerInvite.info);
        Reference.commandUsage2.put("sourcecode", CommandSourceCode.info);
        Reference.commandUsage2.put("tobinary", CommandToBinary.info);
        Reference.commandUsage2.put("toromannum", CommandToRomanNum.info);
        Reference.commandUsage2.put("translate", CommandTranslate.info);
        Reference.commandUsage2.put("twitchuser", CommandTwitchUser.info);
        Reference.commandUsage2.put("uptime", CommandUptime.info);
        Reference.commandUsage2.put("youtubeuser", CommandYoutubeUser.info);
            //PBC and Owner
        Reference.commandUsagePBC.put("addcommand", CommandAddCommand.info);
        Reference.commandUsagePBC.put("announce", CommandAnnounce.info);
        Reference.commandUsagePBC.put("deletecommand", CommandDeleteCommand.info);
        Reference.commandUsagePBC.put("editcommand", CommandEditCommand.info);
        Reference.commandUsagePBC.put("prune", CommandPrune.info);
        Reference.commandUsagePBC.put("say", CommandSay.info);

        //Music
        Reference.commandUsageMusic.put("lyrics", CommandLyrics.info);
        Reference.commandUsageMusic.put("nowplaying", CommandNowPlaying.info);
        Reference.commandUsageMusic.put("pause", CommandPause.info);
        Reference.commandUsageMusic.put("play", CommandPlay.info);
        Reference.commandUsageMusic.put("playnext", CommandPlayNext.info);
        Reference.commandUsageMusic.put("pplay", CommandPlayPlaylist.info);
        Reference.commandUsageMusic.put("splayrand", CommandPlaySearchRand.info);
        Reference.commandUsageMusic.put("splayremix", CommandPlaySearchRemix.info);
        Reference.commandUsageMusic.put("queue", CommandQueue.info);
        Reference.commandUsageMusic.put("remove", CommandRemove.info);
        Reference.commandUsageMusic.put("repeat", CommandRepeat.info);
        Reference.commandUsageMusic.put("repeatonce", CommandRepeatOnce.info);
        Reference.commandUsageMusic.put("resume", CommandResume.info);
        Reference.commandUsageMusic.put("shuffle", CommandShuffle.info);
        Reference.commandUsageMusic.put("skip", CommandSkip.info);
        Reference.commandUsageMusic.put("stop", CommandStop.info);
        Reference.commandUsageMusic.put("restart", CommandRestart.info);
        Reference.commandUsageMusic.put("soundboard", CommandSoundboard.info);

        Logger.log(Logger.LogType.STATUS, "Commands Initialized.");

    }

}

