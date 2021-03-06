package com.miningmark48.tidalbot.commands.base;

import com.miningmark48.tidalbot.Main;
import com.miningmark48.tidalbot.commands.botcommander.configs.*;
import com.miningmark48.tidalbot.commands.botowner.CommandAnnounceAll;
import com.miningmark48.tidalbot.commands.botowner.CommandGetDemServs;
import com.miningmark48.tidalbot.commands.botowner.CommandLeave;
import com.miningmark48.tidalbot.commands.botowner.CommandRestartBot;
import com.miningmark48.tidalbot.commands.botregular.*;
import com.miningmark48.tidalbot.commands.botregular.Command8Ball;
import com.miningmark48.tidalbot.commands.botregular.CommandCmds;
import com.miningmark48.tidalbot.commands.botregular.CommandServerInvite;
import com.miningmark48.tidalbot.commands.botregular.CommandSourceCode;
import com.miningmark48.tidalbot.commands.botregular.CommandARBlacklist;
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
//        registerCommand("bitcoin", new CommandBitcoin());
        registerCommand("botcommander", new CommandBotCommander());
        registerCommand("arblacklist", new CommandARBlacklist());
        registerCommand("botinfo", new CommandBotInfo());
        registerCommand("botstats", new CommandBotStats());
        registerCommand("cmds", new CommandCmds());
        registerCommand("coinflip", new CommandCoinFlip());
        registerCommand("cursedata", new CommandCurseData());
        registerCommand("deletecommand", new CommandDeleteCommand());
        registerCommand("diceroll", new CommandDiceRoll());
        registerCommand("disabledcommands", new CommandDisabledCommands());
        registerCommand("editcommand", new CommandEditCommand());
        registerCommand("emojieval", new CommandEmojiEval());
        registerCommand("emojilist", new CommandEmojiList());
        registerCommand("githubuser", new CommandGithubUser());
        registerCommand("guildinfo", new CommandGuildInfo());
        registerCommand("help", new CommandHelp());
        registerCommand("listbannedmusic", new CommandListBannedMusicUsers());
        registerCommand("listcommanders", new CommandListCommanders());
        registerCommand("kickvoice", new CommandKickVoice());
        registerCommand("listbots", new CommandListBots());
        registerCommand("listcommands", new CommandListCommands());
        registerCommand("lmgtfy", new CommandLMGTFY());
        registerCommand("mixeruser", new CommandMixerUser());
        registerCommand("pastebin", new CommandPastebin());
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
        registerCommand("togglear", new CommandToggleAR());
        registerCommand("togglecommand", new CommandToggleCommand());
        registerCommand("togglemusic", new CommandToggleMusicUser());
        registerCommand("togglenpmessages", new CommandToggleNPMessages());
        registerCommand("toromannum", new CommandToRomanNum());
        registerCommand("translate", new CommandTranslate());
        registerCommand("trivia", new CommandTrivia());
        registerCommand("twitchuser", new CommandTwitchUser());
        registerCommand("uptime", new CommandUptime());
        registerCommand("viewconfig", new CommandViewConfig());
        registerCommand("whois", new CommandWhoIs());
        registerCommand("youtubesearch", new CommandYouTubeSearch());
        registerCommand("youtubeuser", new CommandYoutubeUser());

        //Owner only
        registerCommand("announceall", new CommandAnnounceAll());
        registerCommand("getdemservs", new CommandGetDemServs());
        registerCommand("leave", new CommandLeave());
        registerCommand("restartbot", new CommandRestartBot());

        //Music
        registerCommand("play", new CommandPlay());
        registerCommand("playspotify", new CommandPlaySpotify());
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
        registerCommand("undo", new CommandUndo());
        registerCommand("voteskip", new CommandVoteSkip());
        registerCommand("clear", new CommandClear());

        //Alias
        registerCommand("addcmd", new CommandAddCommand());
        registerCommand("commands", new CommandCmds());
        registerCommand("curseforge", new CommandCurseData());
        registerCommand("customcommands", new CommandListCommands());
        registerCommand("delcmd", new CommandDeleteCommand());
        registerCommand("flipcoin", new CommandCoinFlip());
        registerCommand("listcmds", new CommandListCommands());
        registerCommand("rolldice", new CommandDiceRoll());
        registerCommand("rolldie", new CommandDiceRoll());
        registerCommand("snap", new CommandPrune());
            //Music
        registerCommand("np", new CommandNowPlaying());
//        registerCommand("sb", new CommandSoundboard());

        LoggerUtil.log(LoggerUtil.LogType.INFO, Main.commands.size() + " Commands Initialized.");

    }
    
    private static void registerCommand(String trigger, ICommand command) {
        Main.commands.put(trigger, command);
    }

}

