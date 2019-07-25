package voice;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import core.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public class PlayAudioCommand extends Command {
    private String token = "/play";
    private AudioLoadHandler audioLoadHandler;

    public PlayAudioCommand() {
        audioLoadHandler = new AudioLoadHandler();
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        VoiceManager.getInstance().getPlayerManager().loadItem(event.getMessage().getContent().get().replace(getToken() + " ", ""), audioLoadHandler);

        return Mono.empty();
    }

    public String getToken() {
        return this.token;
    }

    private static class AudioLoadHandler implements AudioLoadResultHandler {
        @Override
        public void trackLoaded(AudioTrack track) {
            VoiceManager.getInstance().getAudioPlayer().playTrack(track);
        }

        @Override
        public void playlistLoaded(AudioPlaylist playlist) {

        }

        @Override
        public void noMatches() {

        }

        @Override
        public void loadFailed(FriendlyException exception) {

        }
    }
}