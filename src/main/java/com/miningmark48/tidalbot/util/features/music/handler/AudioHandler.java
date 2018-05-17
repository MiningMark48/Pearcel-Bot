package com.miningmark48.tidalbot.util.features.music.handler;

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
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

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
                channel.sendMessage("**Added to queue:** " + track.getInfo().title).queue();

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
                    channel.sendMessage("Added " + FormatUtil.formatText(FormatType.BOLD, String.valueOf(playlist.getTracks().size())) + " tracks to queue from playlist: " + playlist.getName()).queue();
                    tracks.forEach(q -> {
                        musicManager.scheduler.queue(q);
                        trackUsers.put(q, user);
                    });
                    play(channel, channel.getGuild(), musicManager, firstTrack, user);
                }else{
                    channel.sendMessage(FormatUtil.formatText(FormatType.BOLD, "Added to queue: ") + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();
                    play(channel, channel.getGuild(), musicManager, firstTrack, user);
                }

            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Could not play: " + FormatUtil.formatText(FormatType.ITALIC, exception.getMessage())).queue();
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
            channel.sendMessage("There is nothing next in the queue!").queue();
        }

    }

    public static void stop(TextChannel channel){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.player.stopTrack();
        musicManager.scheduler.clearQueue();
        leaveVoiceChannel(channel.getGuild().getAudioManager());

        channel.sendMessage("Stopped track and cleared the queue.").queue();
    }

    public static void clearQueue(TextChannel channel){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.clearQueue();

        channel.sendMessage("Cleared the queue.").queue();
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
            embedBuilder.setThumbnail("http://www.icons101.com/icon_png/size_512/id_78717/Music.png");
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

        }else {
            channel.sendMessage(FormatUtil.formatText(FormatType.BOLD, "No songs currently in queue!")).queue();
        }

    }

    public static void pause(TextChannel channel){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        if (!musicManager.player.isPaused()) {
            musicManager.player.setPaused(true);
            channel.sendMessage("Paused current playing track.").queue();
        }else{
            channel.sendMessage("Track is already paused!").queue();
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

            channel.sendMessage(String.format("%s %s %s", FormatUtil.formatText(FormatType.BOLD,"Playing "), track_to_remove.getInfo().title, FormatUtil.formatText(FormatType.BOLD, " next."))).queue();
        }catch (NoSuchElementException e){
            channel.sendMessage(FormatUtil.formatText(FormatType.BOLD,"Track not found.")).queue();
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
            channel.sendMessage(String.format("%s %s from the queue.", FormatUtil.formatText(FormatType.BOLD,"Removed: "), track_to_remove.getInfo().title)).queue();
        }catch (NoSuchElementException e){
            channel.sendMessage(FormatUtil.formatText(FormatType.BOLD,"Track not found.")).queue();
        }

    }

    public static void resume(TextChannel channel){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        if (musicManager.player.isPaused()) {
            musicManager.player.setPaused(false);
            channel.sendMessage("Resumed track.").queue();
        }else{
            channel.sendMessage("Track is already playing!").queue();
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
        if (track != null){
//            channel.sendMessage("**Currently playing:** \n**[** " + MathUtil.getTimeFromLongNoFormatShort(track.getPosition()) + " **/** " + MathUtil.getTimeFromLongNoFormatShort(track.getDuration()) + " **]** " + track.getInfo().title).queue();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("\uD83C\uDFB5 " + FormatUtil.formatText(FormatType.UNDERLINE,"Currently Playing"));
            builder.setColor(Color.decode("#a2f000"));
            builder.addField(track.getInfo().title, "**[** " + MathUtil.getTimeFromLongNoFormatShort(track.getPosition()) + " **/** " + MathUtil.getTimeFromLongNoFormatShort(track.getDuration()) + " **]**" + "\n" + FormatUtil.formatURL(currentTrack.getInfo().author,currentTrack.getInfo().uri), false);
            builder.addField("Added by: ", trackUsers.get(track).getName(), false);
            channel.sendMessage(builder.build()).queue();
        } else {
            channel.sendMessage("Nothing is currently playing!").queue();
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
        } else {
            channel.sendMessage("Playlist is empty!").queue();
        }
    }

    public static void repeat(TextChannel channel, boolean once) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        TrackScheduler.RepeatStatus status = musicManager.scheduler.repeat(once);
        if (status == TrackScheduler.RepeatStatus.SUCCESS){
            channel.sendMessage((!once ? "\uD83D\uDD01" : "\uD83D\uDD02") + " Repeat enabled for: " + FormatUtil.formatText(FormatType.BOLD,musicManager.player.getPlayingTrack().getInfo().title)).queue();
        } else if (status == TrackScheduler.RepeatStatus.TURNED_OFF){
            channel.sendMessage("Repeat disabled.").queue();
        } else if (status == TrackScheduler.RepeatStatus.ERROR) {
            channel.sendMessage("No track is currently playing!").queue();
        }
    }

    public static void restart(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        if (musicManager.player.getPlayingTrack() != null) {
            channel.sendMessage("Restarting track!").queue();
            musicManager.scheduler.repeat(true);
            musicManager.scheduler.nextTrack();
        }
    }

    public static void undo(TextChannel channel){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        if (recentTrack == null || !musicManager.scheduler.getQueue().contains(recentTrack)) {
            channel.sendMessage("No track to undo!").queue();
            return;
        }

        try {
            AudioTrack track_to_remove = recentTrack;
            musicManager.scheduler.getQueue().remove(track_to_remove);
            recentTrack = null;
            channel.sendMessage(String.format("%s %s to the queue.", FormatUtil.formatText(FormatType.BOLD,"Undone adding: "), FormatUtil.formatText(FormatType.ITALIC, track_to_remove.getInfo().title))).queue();
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
