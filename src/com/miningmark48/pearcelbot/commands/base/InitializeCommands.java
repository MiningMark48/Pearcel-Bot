package com.miningmark48.pearcelbot.commands.base;

import com.miningmark48.pearcelbot.Main;
import com.miningmark48.pearcelbot.commands.*;
import com.miningmark48.pearcelbot.commands.music.*;
import com.miningmark48.pearcelbot.commands.music.role_block.*;
import com.miningmark48.pearcelbot.commands.pbc.*;
import com.miningmark48.pearcelbot.util.LoggerUtil;

public class InitializeCommands {

    public static void init(){

        Main.commands.clear();

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
        Main.commands.put("kickvoice", new CommandKickVoice());
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
        Main.commands.put("voteskip", new CommandVoteSkip());

        //Alias
//        Main.commands.put("help", new CommandCmds());
//        Main.commands.put("addcmd", new CommandAddCommand());
//        Main.commands.put("curseforge", new CommandCurseData());
//        Main.commands.put("delcmd", new CommandDeleteCommand());
//        Main.commands.put("listcmds", new CommandListCommands());
//            //Music
//        Main.commands.put("np", new CommandNowPlaying());
//        Main.commands.put("sb", new CommandSoundboard());

        LoggerUtil.log(LoggerUtil.LogType.STATUS, "Commands Initialized.");

    }

}

