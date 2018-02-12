package com.miningmark48.pearcelbot.util.music;

import com.miningmark48.pearcelbot.commands.music.soundboard.AudioHandlerSoundboard;
import com.miningmark48.pearcelbot.util.FormatUtil;
import com.miningmark48.pearcelbot.util.Logger;
import com.miningmark48.pearcelbot.util.music.handler.AudioHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.core.entities.Guild;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private Queue<AudioTrack> queue;
    private Guild guildPlaying;
    private AudioTrack repeat_track;
    private boolean isRepeat;
    private boolean isRepeatOnce;

    /**
    * @param player The audio player this scheduler uses
    */
    public TrackScheduler(AudioPlayer player) {
    this.player = player;
    this.queue = new LinkedList<>();
    }

    /**
    * Add the next track to queue or play right away if nothing is in the queue.
    *
    * @param track The track to play or add to queue.
    */
    public void queue(AudioTrack track) {
        // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
        // something is playing, it returns false and does nothing. In that case the player was already playing so this
        // track goes to the queue instead.
        if (!player.startTrack(track, true)) {
          queue.offer(track);
        }
    }

    /**
    * Start the next track, stopping the current one if it is playing.
    */
    public boolean nextTrack() {
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        try {
            AudioTrack track;
            if (!isRepeat) {
                track = queue.poll().makeClone();
            } else {
                track = repeat_track.makeClone();
                if (isRepeatOnce){
                    isRepeat = false;
                    isRepeatOnce = false;
                }
            }
            try {
                AudioHandler.musicChannelRef.get(getGuildPlaying()).sendMessage(String.format("%s %s", FormatUtil.bold("Now Playing: "), track.getInfo().title)).queue();
            } catch (NullPointerException e) {
                Logger.log(Logger.LogType.INFO, "No track available.");
                return false;
            }
            player.startTrack(track, false);
            return true;
        } catch (NullPointerException e){
            Logger.log(Logger.LogType.INFO, "No track found next in queue!");
            return false;
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
    // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if (endReason.mayStartNext) {
          nextTrack();
          AudioHandler.resumeQuiet(AudioHandlerSoundboard.audio_guild);
        }else{
//            getGuildPlaying().getAudioManager().closeAudioConnection();
        }
    }

    public void clearQueue(){
        queue.clear();
    }

    public Queue<AudioTrack> getQueue() {
        return queue;
    }

    public void setQueue(Queue<AudioTrack> new_queue){
        queue = new_queue;
    }

    public void shuffle(){
        Collections.shuffle((List<?>) queue);
    }

    public RepeatStatus repeat(boolean once){
        isRepeat = !isRepeat;
        if (isRepeat) {
            if (player.getPlayingTrack() != null){
                repeat_track = player.getPlayingTrack();
                isRepeatOnce = once;
                return RepeatStatus.SUCCESS;
            }
            return RepeatStatus.ERROR;
        }
        return RepeatStatus.TURNED_OFF;
    }

    public Guild getGuildPlaying() {
        return guildPlaying;
    }

    public void setGuildPlaying(Guild guildPlaying) {
        this.guildPlaying = guildPlaying;
    }

    public enum RepeatStatus {
        SUCCESS,
        ERROR,
        TURNED_OFF
    }

}
