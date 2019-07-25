package voice;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import discord4j.voice.AudioProvider;
import discord4j.voice.VoiceConnection;

public class VoiceManager {
    private static VoiceManager instance;
    private AudioPlayerManager playerManager;
    private AudioProvider audioProvider;
    private AudioPlayer audioPlayer;
    private VoiceConnection voiceConnection;
    private VoiceFileManager voiceFileManager;

    private VoiceManager() {
        this.playerManager = new DefaultAudioPlayerManager();
        this.playerManager.getConfiguration().setFrameBufferFactory(com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer::new);
        //AudioSourceManagers.registerRemoteSources(this.playerManager);
        AudioSourceManagers.registerLocalSource(this.playerManager);
        this.audioPlayer = this.playerManager.createPlayer();
        this.audioProvider = new LavaplayerAudioProvider(this.audioPlayer);
        this.voiceFileManager = new VoiceFileManager();
    }

    public void setVoiceConnection(VoiceConnection voiceConnection) {
        this.voiceConnection = voiceConnection;
    }

    public void disconnectFromVoiceConnection() {
        if (this.voiceConnection != null) {
            this.voiceConnection.disconnect();
            this.voiceConnection = null;
        }
    }

    public VoiceFileManager getVoiceFileManager() {
        return voiceFileManager;
    }

    public static VoiceManager getInstance() {
        if (instance == null) {
            instance = new VoiceManager();
        }

        return instance;
    }


    public AudioPlayerManager getPlayerManager() {
        return this.playerManager;
    }


    public AudioPlayer getAudioPlayer() {
        return this.audioPlayer;
    }


    public AudioProvider getAudioProvider() {
        return this.audioProvider;
    }

}