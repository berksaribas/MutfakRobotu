package core;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public abstract class Command {
    public Command() {
    }

    public abstract Mono<Void> execute(MessageCreateEvent event);

    public abstract String getToken();
}