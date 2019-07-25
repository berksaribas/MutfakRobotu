package voice;

import core.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

import java.util.List;

public class ListAudioCommand extends Command {
    private String token = "/list";

    public ListAudioCommand() {
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage().getContent())
                .doOnNext(content -> {
                    int pageNumber = 0;
                    if(content.contains(" ")) {
                        pageNumber = Integer.parseInt(content.split(" ")[1]);
                    }

                    int finalPageNumber = pageNumber;
                    event.getMessage().getChannel()
                            .doOnNext(channel -> {
                                List<String> results = VoiceManager.getInstance().getVoiceFileManager().getSoundsByIndex(finalPageNumber);
                                StringBuilder resultText = new StringBuilder();
                                results.forEach(result -> resultText.append(result).append(" , "));
                                channel.createMessage("Page content: " + resultText.toString()).block();
                            }).block();
                }).then();
    }

    public String getToken() {
        return this.token;
    }
}
