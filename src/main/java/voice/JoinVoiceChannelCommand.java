package voice;

import core.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import reactor.core.publisher.Mono;

public class JoinVoiceChannelCommand extends Command {
    private String token = "/join";

    public JoinVoiceChannelCommand() {
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMember())
                .flatMap(Member::getVoiceState)
                .flatMap(VoiceState::getChannel)
                .flatMap(channel -> channel.join(spec -> {
                    spec.setProvider(VoiceManager.getInstance().getAudioProvider());
                }).doOnNext(voiceConnection -> VoiceManager.getInstance().setVoiceConnection(voiceConnection)))
                .then();
    }

    public String getToken() {
        return this.token;
    }
}