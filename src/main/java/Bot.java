import core.CommandExecuter;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import voice.*;

public class Bot {
    public static void main(String[] args) {
        DiscordClient client = (new DiscordClientBuilder(Config.token)).build();

        CommandExecuter commandExecuter = new CommandExecuter();

        commandExecuter.registerCommand(JoinVoiceChannelCommand.class);
        commandExecuter.registerCommand(PlayAudioCommand.class);
        commandExecuter.registerCommand(LeaveVoiceChannelCommand.class);
        commandExecuter.registerCommand(StopAudioCommand.class);
        commandExecuter.registerCommand(SaveAudioCommand.class);

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .flatMap(event -> Mono.justOrEmpty(event.getMessage().getContent())
                        .flatMap(content -> Flux.fromIterable(commandExecuter.getCommandEntrySet())
                                .filter(entry -> content.startsWith(entry.getKey()))
                                .flatMap(entry -> entry.getValue().execute(event))
                                .next()))
                .subscribe();

        client.login().block();
    }
}