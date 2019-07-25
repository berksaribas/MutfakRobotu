import core.CommandExecuter;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import voice.JoinVoiceChannelCommand;
import voice.LeaveVoiceChannelCommand;
import voice.PlayAudioCommand;
import voice.StopAudioCommand;

public class Bot {
    public static void main(String[] args) {
        DiscordClient client = (new DiscordClientBuilder(Config.token)).build();

        CommandExecuter commandExecuter = new CommandExecuter();

        commandExecuter.registerCommand(JoinVoiceChannelCommand.class);
        commandExecuter.registerCommand(PlayAudioCommand.class);
        commandExecuter.registerCommand(LeaveVoiceChannelCommand.class);
        commandExecuter.registerCommand(StopAudioCommand.class);

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    Message msg = event.getMessage();
                    String messageContent = msg.getContent().get();
                    String prefix = messageContent.split(" ")[0];
                    System.out.println(prefix);
                    if(commandExecuter.isPrefixRegistered(prefix)) {
                        commandExecuter.getCommandByPrefix(prefix).execute(event).block();
                    }
                });

        client.login().block();
    }
}