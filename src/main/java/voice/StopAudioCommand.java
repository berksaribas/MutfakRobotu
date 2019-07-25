package voice;

import core.Command;
import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public class StopAudioCommand extends Command {
    private String token = "/stop";

    public StopAudioCommand() {
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        VoiceManager.getInstance().getAudioPlayer().stopTrack();
        return event.getMessage().getChannel()
                .doOnNext(c -> c.createMessage("Stopping the playback.").block())
                .then();
    }

    public String getToken() {
        return this.token;
    }
}
