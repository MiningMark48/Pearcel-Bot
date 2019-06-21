package com.miningmark48.tidalbot.util.features.music.handler;

import com.miningmark48.tidalbot.util.DefaultEmbeds;
import com.miningmark48.tidalbot.reference.Reference;
import com.miningmark48.tidalbot.util.*;
import com.miningmark48.tidalbot.util.FormatUtil.FormatType;
import com.miningmark48.tidalbot.util.features.music.GuildMusicManager;
import com.miningmark48.tidalbot.util.features.music.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Queue;

@SuppressWarnings("Duplicates")
public class AudioHandler {

    public static Map<AudioTrack, User> trackUsers = new HashMap<>();
    public static AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    private static Map<Long, GuildMusicManager> musicManagers =  new HashMap<>();

    private static int default_volume = 15;
    private static AudioTrack recentTrack;

    public static Map<Guild, TextChannel> musicChannelRef = new HashMap<>();

    public static void loadAndPlay(final TextChannel channel, User user, final String trackUrl, final boolean isPlaylist) {

        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.player.setVolume(default_volume);
        musicManager.scheduler.setGuildPlaying(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                DefaultEmbeds.sendMessage(channel, "Added to Queue", track.getInfo().title, DefaultEmbeds.EmbedType.MUSIC);

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
                    DefaultEmbeds.sendMessage(channel, "Added Playlist to Queue", String.valueOf(playlist.getTracks().size()) + " tracks to queue from playlist: " + playlist.getName(), DefaultEmbeds.EmbedType.MUSIC);
                    tracks.forEach(q -> {
                        musicManager.scheduler.queue(q);
                        trackUsers.put(q, user);
                    });
                    play(channel, channel.getGuild(), musicManager, firstTrack, user);
                }else{
                    DefaultEmbeds.sendMessage(channel, "Added to Queue", firstTrack.getInfo().title + "\n (first track of playlist " + playlist.getName() + ")", DefaultEmbeds.EmbedType.MUSIC);
                    play(channel, channel.getGuild(), musicManager, firstTrack, user);
                }

            }

            @Override
            public void noMatches() {
                DefaultEmbeds.sendMessage(channel, "Nothing Found", trackUrl, DefaultEmbeds.EmbedType.MUSIC);

            }

            @Override
            public void loadFailed(FriendlyException exception) {
                DefaultEmbeds.sendMessage(channel, "Could Not Play", exception.getMessage(), DefaultEmbeds.EmbedType.MUSIC);
            }
        });
    }

    private static void play(TextChannel channel, Guild guild, GuildMusicManager musicManager, AudioTrack track, User user) {
        connectVoiceChannel(guild.getAudioManager(), user);

        try {
            musicManager.scheduler.queue(track);
            trackUsers.put(track, user);
            recentTrack = track;
        }catch (FriendlyException e){
            channel.sendMessage(FormatUtil.formatText(FormatType.BOLD, "An error occurred while trying to play that track!")).queue();
            DefaultEmbeds.sendMessage(channel, "There is nothing next in the queue!", DefaultEmbeds.EmbedType.MUSIC);
        }

        if (musicChannelRef.containsKey(guild)){
            musicChannelRef.replace(guild, channel);
        }else{
            musicChannelRef.put(guild, channel);
        }

    }

    public static void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        if (!musicManager.scheduler.nextTrack()) {
            DefaultEmbeds.sendMessage(channel, "There is nothing next in the queue!", DefaultEmbeds.EmbedType.MUSIC);
        }

    }

    public static void stop(TextChannel channel){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.player.stopTrack();
        musicManager.scheduler.clearQueue();
        musicManager.scheduler.repeatDisable();
        leaveVoiceChannel(channel.getGuild().getAudioManager());

        DefaultEmbeds.sendMessage(channel, "Stopped track and cleared the queue.", DefaultEmbeds.EmbedType.MUSIC);
    }

    public static void clearQueue(TextChannel channel){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.clearQueue();

        DefaultEmbeds.sendMessage(channel, "Cleared the queue.", DefaultEmbeds.EmbedType.MUSIC);

    }

    public static void getQueue(TextChannel channel){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        Queue<AudioTrack> queue = musicManager.scheduler.getQueue();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if (queue.size() > 0) {
            long queueTime = 0;
            for (AudioTrack t : queue) {
                queueTime += t.getInfo().length;
            }

            embedBuilder.setTitle("Current Queue (" + queue.size() + ") [" + (MathUtil.getTimeFromLong(queueTime) + "]:\n"));
            embedBuilder.setThumbnail("http://tw.miningmark48.xyz/img/icons/music.png");
            embedBuilder.setFooter("Provided by " + Reference.botName + ". Made by MiningMark48.", "http://miningmark48.xyz/img/logo/logo.png");
            embedBuilder.setColor(Color.decode("#a2f000"));

            AudioTrack currentTrack = musicManager.player.getPlayingTrack();
            embedBuilder.addField("Currently Playing", "**[** " + MathUtil.getTimeFromLongNoFormatShort(currentTrack.getPosition()) + " **/** " + MathUtil.getTimeFromLongNoFormatShort(currentTrack.getDuration()) + " **]** " + currentTrack.getInfo().title + "\n" + FormatUtil.formatURL(currentTrack.getInfo().author, currentTrack.getInfo().uri) + (trackUsers.get(currentTrack) != null ? (" - " + "*Added by " + trackUsers.get(currentTrack).getName() + "*") : ""), false);
            embedBuilder.addBlankField(false);

            int i = 1;
            int queueList = 5;
            for (AudioTrack track : queue) {
                if (i <= queueList){
                    embedBuilder.addField("(" + i + ") " + track.getInfo().title, (MathUtil.getTimeFromLong(track.getDuration()) + " - " + "*Added by " + trackUsers.get(track).getName() + "*\n" + FormatUtil.formatURL(track.getInfo().author, track.getInfo().uri)), false);
                } else {
                    int queueSize = queue.size() - (queueList + 1);
                    if (queueSize != 0) embedBuilder.addField("Plus " + queueSize + " more.", "", false);
                    break;
                }
                i++;

            }
            channel.sendMessage(embedBuilder.build()).queue();

        } else {
            DefaultEmbeds.sendMessage(channel, "Queue is Empty", DefaultEmbeds.EmbedType.MUSIC);
        }

    }

    public static void pause(TextChannel channel){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        if (!musicManager.player.isPaused()) {
            musicManager.player.setPaused(true);
            DefaultEmbeds.sendMessage(channel, "Paused currently playing track", DefaultEmbeds.EmbedType.MUSIC);
        }else{
            DefaultEmbeds.sendMessage(channel, "Track is already paused!", DefaultEmbeds.EmbedType.MUSIC);
        }
    }

    public static void pauseQuiet(Guild guild){
        GuildMusicManager musicManager = getGuildAudioPlayer(guild);
        if (!musicManager.player.isPaused()) {
            musicManager.player.setPaused(true);
        }
    }

    public static void playNext(TextChannel channel, String[] keyword_query){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        StringBuilder builder = new StringBuilder();
        String keyword;
        Arrays.stream(keyword_query).forEach(q -> builder.append(q).append(Arrays.asList(keyword_query).indexOf(q) == keyword_query.length - 1 ? "" : " "));

        keyword = builder.toString();

        LoggerUtil.log(LoggerUtil.LogType.DEBUG, keyword);

        channel.sendMessage(String.format("Searching queue for %s.", FormatUtil.formatText(FormatType.ITALIC, keyword))).queue();

        try {
            AudioTrack track_to_remove = musicManager.scheduler.getQueue().stream().filter(audioTrack -> audioTrack.getInfo().title.toLowerCase().contains(keyword.toLowerCase())).findFirst().get();
            musicManager.scheduler.getQueue().remove(track_to_remove);

            Deque<AudioTrack> deQueue =(Deque<AudioTrack>) musicManager.scheduler.getQueue();
            deQueue.addFirst(track_to_remove);

            musicManager.scheduler.setQueue(deQueue);

            DefaultEmbeds.sendMessage(channel, "Playing Next", track_to_remove.getInfo().title, DefaultEmbeds.EmbedType.MUSIC);
        }catch (NoSuchElementException e){
            DefaultEmbeds.sendMessage(channel, "Track not found.", DefaultEmbeds.EmbedType.MUSIC);
        }

    }

    public static void remove(TextChannel channel, String[] keyword_query){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        StringBuilder builder = new StringBuilder();
        String keyword;
        Arrays.stream(keyword_query).forEach(q -> builder.append(q).append(Arrays.asList(keyword_query).indexOf(q) == keyword_query.length - 1 ? "" : " "));

        keyword = builder.toString();

        channel.sendMessage(String.format("Searching queue for %s.", FormatUtil.formatText(FormatType.ITALIC, keyword))).queue();

        try {
            AudioTrack track_to_remove = musicManager.scheduler.getQueue().stream().filter(audioTrack -> audioTrack.getInfo().title.toLowerCase().contains(keyword.toLowerCase())).findFirst().get();
            musicManager.scheduler.getQueue().remove(track_to_remove);
            DefaultEmbeds.sendMessage(channel, "Removed From Queue", track_to_remove.getInfo().title, DefaultEmbeds.EmbedType.MUSIC);
        }catch (NoSuchElementException e){
            DefaultEmbeds.sendMessage(channel, "Track not found.", DefaultEmbeds.EmbedType.MUSIC);
        }

    }

    public static void resume(TextChannel channel){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        if (musicManager.player.isPaused()) {
            musicManager.player.setPaused(false);
            DefaultEmbeds.sendMessage(channel, "Resumed track", DefaultEmbeds.EmbedType.MUSIC);
        }else{
            DefaultEmbeds.sendMessage(channel, "Track is already playing", DefaultEmbeds.EmbedType.MUSIC);
        }
    }

    public static void resumeQuiet(Guild guild){
        GuildMusicManager musicManager = getGuildAudioPlayer(guild);
        if (musicManager.player.isPaused()) {
            musicManager.player.setPaused(false);
        }
    }

    public static void nowPlaying(TextChannel channel){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        AudioTrack track = musicManager.player.getPlayingTrack();
        AudioTrack currentTrack = musicManager.player.getPlayingTrack();
        if (track != null) {
//            channel.sendMessage("**Currently playing:** \n**[** " + MathUtil.getTimeFromLongNoFormatShort(track.getPosition()) + " **/** " + MathUtil.getTimeFromLongNoFormatShort(track.getDuration()) + " **]** " + track.getInfo().title).queue();

            DefaultEmbeds.sendMessage(channel, "Now Playing", ("**" + track.getInfo().title + "**\n\n" + ("**[** " + MathUtil.getTimeFromLongNoFormatShort(track.getPosition()) + " **/** " + MathUtil.getTimeFromLongNoFormatShort(track.getDuration()) + " **]**") + "\n" + (FormatUtil.formatURL(currentTrack.getInfo().author,currentTrack.getInfo().uri)) + "\n\n" + (trackUsers.get(track) != null ? ("**Added by:** " + trackUsers.get(track).getName()) : "")), DefaultEmbeds.EmbedType.MUSIC);

        } else {
            DefaultEmbeds.sendMessage(channel, "Nothing is currently playing", DefaultEmbeds.EmbedType.MUSIC);
        }

    }

    public static void shuffle(TextChannel channel, String[] args){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        int i = 1;
        if (args.length <= 0 || args[0].isEmpty()) {
            musicManager.scheduler.shuffle();
        } else {
            for (i = 0; i < Integer.valueOf(args[0]) && i < 100; i++) {
                musicManager.scheduler.shuffle();
            }
        }
        if (!musicManager.scheduler.getQueue().isEmpty()){
            channel.sendMessage("Playlist was shuffled " + NumWordUtil.convert(i) + " " + (i == 1 ? "time" : "times") + ".").queue();
            DefaultEmbeds.sendMessage(channel, "Queue Shuffled " + StringUtils.capitalize(NumWordUtil.convert(i)) + " " + (i == 1 ? "Time" : "Times"), DefaultEmbeds.EmbedType.MUSIC);
        } else {
            DefaultEmbeds.sendMessage(channel, "Queue is empty!", DefaultEmbeds.EmbedType.MUSIC);
        }
    }

    public static void repeat(TextChannel channel, boolean once) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        TrackScheduler.RepeatStatus status = musicManager.scheduler.repeat(once);
        if (status == TrackScheduler.RepeatStatus.SUCCESS){
            channel.sendMessage((!once ? "\uD83D\uDD01" : "\uD83D\uDD02") + " Repeat enabled for: " + FormatUtil.formatText(FormatType.BOLD,musicManager.player.getPlayingTrack().getInfo().title)).queue();
        } else if (status == TrackScheduler.RepeatStatus.TURNED_OFF){
            DefaultEmbeds.sendMessage(channel, "Repeat disabled", DefaultEmbeds.EmbedType.MUSIC);
        } else if (status == TrackScheduler.RepeatStatus.ERROR) {
            DefaultEmbeds.sendMessage(channel, "No track is currently playing", DefaultEmbeds.EmbedType.MUSIC);
        }
    }

    public static void restart(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        if (musicManager.player.getPlayingTrack() != null) {
            DefaultEmbeds.sendMessage(channel, "Restarting track!", DefaultEmbeds.EmbedType.MUSIC);
            musicManager.scheduler.repeat(true);
            musicManager.scheduler.nextTrack();
        }
    }

    public static void undo(TextChannel channel){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        if (recentTrack == null || !musicManager.scheduler.getQueue().contains(recentTrack)) {
            DefaultEmbeds.sendMessage(channel, "No track to undo!", DefaultEmbeds.EmbedType.MUSIC);
            return;
        }

        try {
            AudioTrack track_to_remove = recentTrack;
            musicManager.scheduler.getQueue().remove(track_to_remove);
            recentTrack = null;
            DefaultEmbeds.sendMessage(channel, "Undone", track_to_remove.getInfo().title, DefaultEmbeds.EmbedType.MUSIC);
        } catch (Exception e){
            channel.sendMessage(FormatUtil.formatText(FormatType.BOLD,"Error: ") + e.getMessage()).queue();
        }

    }

    public static void moveVoice(AudioManager audioManager, VoiceChannel channel) {
        audioManager.openAudioConnection(channel);
    }

    private static void connectVoiceChannel(AudioManager audioManager, User user) {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            int i = 0;
            int j = audioManager.getGuild().getVoiceChannels().size();

            for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                if (user != null && voiceChannel.getMembers().stream().anyMatch(q -> q.equals(user))) {
                    audioManager.openAudioConnection(voiceChannel);
                } else if (!voiceChannel.getMembers().isEmpty()) {
                    audioManager.openAudioConnection(voiceChannel);
                } else
                    if (i == j){
                        audioManager.getGuild().getVoiceChannels().stream().findFirst().ifPresent(audioManager::openAudioConnection);
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
