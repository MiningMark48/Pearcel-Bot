package com.miningmark48.pearcelbot.commands.music.soundboard;

import com.miningmark48.pearcelbot.util.Logger;
import com.miningmark48.pearcelbot.util.music.GuildMusicManager;
import com.miningmark48.pearcelbot.util.music.handler.AudioHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class AudioHandlerSoundboard {

    public static AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    public static Guild audio_guild;
    private static Map<Long, GuildMusicManager> musicManagers =  new HashMap<>();

    private static int default_volume = 50;

    public static Map<Guild, TextChannel> musicChannelRef = new HashMap<>();

    public static synchronized void loadAndPlay(final TextChannel channel, User user, final String trackUrl, final boolean isPlaylist) {

        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.player.setVolume(default_volume);
        musicManager.scheduler.setGuildPlaying(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                audio_guild = channel.getGuild();
                play(channel, channel.getGuild(), musicManager, track, user);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();
                List<AudioTrack> tracks = playlist.getTracks();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                if (isPlaylist){
                    tracks.forEach(musicManager.scheduler::queue);
                    play(channel, channel.getGuild(), musicManager, firstTrack, user);
                }else{
                    play(channel, channel.getGuild(), musicManager, firstTrack, user);
                }

            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException exception) {
                Logger.log(Logger.LogType.FATAL, "Could not play track!");
                exception.printStackTrace();
            }
        });
    }

    private static void play(TextChannel channel, Guild guild, GuildMusicManager musicManager, AudioTrack track, User user) {
        connectVoiceChannel(guild.getAudioManager(), user);

        try {
//            AudioHandler.pauseQuiet(guild);
            musicManager.scheduler.queue(track);
        }catch (FriendlyException e){
            AudioHandler.resumeQuiet(guild);
        }

        if (musicChannelRef.containsKey(guild)){
            musicChannelRef.replace(guild, channel);
        }else{
            musicChannelRef.put(guild, channel);
        }

    }

    private static void connectVoiceChannel(AudioManager audioManager, User user) {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            int i = 0;
            int j = audioManager.getGuild().getVoiceChannels().size() - 1;

            for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                if (user != null && voiceChannel.getMembers().contains(user)) {
                    audioManager.openAudioConnection(voiceChannel);
                }else if (!voiceChannel.getMembers().isEmpty()) {
                    audioManager.openAudioConnection(voiceChannel);
                }else
                if (i == j){
                    for (VoiceChannel voiceChannel2 : audioManager.getGuild().getVoiceChannels()) {
                        audioManager.openAudioConnection(voiceChannel2);
                        break;
                    }
                }
                i++;
            }
        }
    }

    private static void leaveVoiceChannel(AudioManager audioManager){
        if (audioManager.isConnected()){
            audioManager.closeAudioConnection();
        }
    }

    public static synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

}
