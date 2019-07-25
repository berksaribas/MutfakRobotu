package voice;

import core.Command;
import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public class LeaveVoiceChannelCommand extends Command {
    private String token = "/leave";

    public LeaveVoiceChannelCommand() {
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        VoiceManager.getInstance().disconnectFromVoiceConnection();
        return event.getMessage().getChannel()
                .doOnNext(c -> c.createMessage("Leaving the voice channel.").block())
                .then();
    }

    public String getToken() {
        return this.token;
    }
}