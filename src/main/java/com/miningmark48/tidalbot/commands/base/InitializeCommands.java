package com.miningmark48.tidalbot.commands.base;

import com.miningmark48.tidalbot.Main;
import com.miningmark48.tidalbot.commands.*;
import com.miningmark48.tidalbot.commands.music.*;
import com.miningmark48.tidalbot.commands.music.role_block.*;
import com.miningmark48.tidalbot.commands.botcommander.*;
import com.miningmark48.tidalbot.util.LoggerUtil;

public class InitializeCommands {

    public static void init(){

        Main.commands.clear();

        //Init
        registerCommand("8ball", new Command8Ball());
        registerCommand("addcommand", new CommandAddCommand());
        registerCommand("announce", new CommandAnnounce());
        registerCommand("bark", new CommandBark());
//        registerCommand("bitcoin", new CommandBitcoin());
        registerCommand("arblacklist", new CommandARBlacklist());
        registerCommand("botinfo", new CommandBotInfo());
        registerCommand("botstats", new CommandBotStats());
        registerCommand("cmds", new CommandCmds());
        registerCommand("coinflip", new CommandCoinFlip());
        registerCommand("cursedata", new CommandCurseData());
        registerCommand("deletecommand", new CommandDeleteCommand());
        registerCommand("diceroll", new CommandDiceRoll());
        registerCommand("editcommand", new CommandEditCommand());
        registerCommand("emojieval", new CommandEmojiEval());
        registerCommand("emojilist", new CommandEmojiList());
        registerCommand("fizzbuzz", new CommandFizzbuzz());
        registerCommand("geoip", new CommandGeoIP());
        registerCommand("githubuser", new CommandGithubUser());
        registerCommand("guildinfo", new CommandGuildInfo());
        registerCommand("help", new CommandHelp());
        registerCommand("kickvoice", new CommandKickVoice());
        registerCommand("listbots", new CommandListBots());
        registerCommand("listcommands", new CommandListCommands());
        registerCommand("lmgtfy", new CommandLMGTFY());
        registerCommand("mixeruser", new CommandMixerUser());
        registerCommand("numtoword", new CommandNumberToWords());
        registerCommand("pastebin", new CommandPastebin());
        registerCommand("patreon", new CommandPatreon());
        registerCommand("ping", new CommandPing());
        registerCommand("poll", new CommandPoll());
        registerCommand("prune", new CommandPrune());
        registerCommand("qrcodecreate", new CommandQRCreate());
        registerCommand("roles", new CommandRoles());
        registerCommand("rps", new CommandRPS());
        registerCommand("say", new CommandSay());
        registerCommand("selfinfo", new CommandSelfInfo());
        registerCommand("serverinvite", new CommandServerInvite());
        registerCommand("slap", new CommandSlap());
        registerCommand("sourcecode", new CommandSourceCode());
        registerCommand("tobinary", new CommandToBinary());
        registerCommand("toromannum", new CommandToRomanNum());
        registerCommand("translate", new CommandTranslate());
        registerCommand("trivia", new CommandTrivia());
        registerCommand("twitchuser", new CommandTwitchUser());
        registerCommand("uptime", new CommandUptime());
        registerCommand("youtubesearch", new CommandYouTubeSearch());
        registerCommand("youtubeuser", new CommandYoutubeUser());

        //Me only
        registerCommand("getdemservs", new CommandGetDemServs());

        //Music
        registerCommand("play", new CommandPlay());
        registerCommand("pplay", new CommandPlayPlaylist());
        registerCommand("splay", new CommandPlaySearch());
        registerCommand("splayrand", new CommandPlaySearchRand());
        registerCommand("splayremix", new CommandPlaySearchRemix());
        registerCommand("skip", new CommandSkip());
        registerCommand("stop", new CommandStop());
        registerCommand("queue", new CommandQueue());
        registerCommand("pause", new CommandPause());
        registerCommand("playnext", new CommandPlayNext());
        registerCommand("remove", new CommandRemove());
        registerCommand("repeat", new CommandRepeat());
        registerCommand("repeatonce", new CommandRepeatOnce());
        registerCommand("resume", new CommandResume());
        registerCommand("nowplaying", new CommandNowPlaying());
        registerCommand("shuffle", new CommandShuffle());
        registerCommand("lyrics", new CommandLyrics());
        registerCommand("restart", new CommandRestart());
        registerCommand("summon", new CommandSummon());
        registerCommand("voteskip", new CommandVoteSkip());
        registerCommand("clear", new CommandClear());

        //Alias
        registerCommand("addcmd", new CommandAddCommand());
        registerCommand("curseforge", new CommandCurseData());
        registerCommand("delcmd", new CommandDeleteCommand());
        registerCommand("listcmds", new CommandListCommands());
            //Music
        registerCommand("np", new CommandNowPlaying());
//        registerCommand("sb", new CommandSoundboard());

        LoggerUtil.log(LoggerUtil.LogType.STATUS, Main.commands.size() + " Commands Initialized.");

    }
    
    private static void registerCommand(String trigger, ICommand command) {
        Main.commands.put(trigger, command);
    }

}

