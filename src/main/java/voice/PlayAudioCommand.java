package voice;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import core.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;
import utils.FileUtils;

public class PlayAudioCommand extends Command {
    private String token = "/play";
    private AudioLoadHandler audioLoadHandler;

    public PlayAudioCommand() {
        audioLoadHandler = new AudioLoadHandler();
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        String soundName = event.getMessage().getContent().get().replace(getToken() + " ", "");
        String soundLocation = VoiceManager.getInstance().getVoiceFileManager().retrieveSound(soundName);

        audioLoadHandler.setEvent(event);
        audioLoadHandler.setSoundName(soundName);

        VoiceManager.getInstance().getPlayerManager().loadItem(soundLocation, audioLoadHandler);

        return Mono.empty();
    }

    public String getToken() {
        return this.token;
    }

    private static class AudioLoadHandler implements AudioLoadResultHandler {
        private MessageCreateEvent messageCreateEvent;
        private String soundName;
        @Override
        public void trackLoaded(AudioTrack track) {
            VoiceManager.getInstance().getAudioPlayer().playTrack(track);
        }

        @Override
        public void playlistLoaded(AudioPlaylist playlist) {

        }

        @Override
        public void noMatches() {
            messageCreateEvent.getMessage().getChannel()
                    .doOnNext(c -> c.createMessage("Couldn't find it. Could it be: " + VoiceManager.getInstance().getVoiceFileManager().findMostSimilarSoundName(soundName)).block()).block();
        }

        @Override
        public void loadFailed(FriendlyException exception) {

        }

        public void setEvent(MessageCreateEvent messageCreateEvent) {
            this.messageCreateEvent = messageCreateEvent;
        }

        public void setSoundName(String soundName) {
            this.soundName = soundName;
        }
    }
}