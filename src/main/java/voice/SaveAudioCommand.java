package voice;

import core.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Attachment;
import reactor.core.publisher.Mono;
import utils.FileUtils;

import java.io.IOException;

public class SaveAudioCommand extends Command {
    private String token = "/save";

    public SaveAudioCommand() {
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage().getAttachments())
                .doOnNext(attachments -> {
                    for (Attachment attachment : attachments) {
                        if(!FileUtils.isPlayableAudioFile(attachment.getFilename())) {
                            event.getMessage().getChannel()
                                    .flatMap(channel -> channel.createMessage("The file is not an audio file or is not supported."))
                                    .block();
                            continue;
                        }
                        try {
                            String filename = attachment.getFilename();
                            FileUtils.downloadFile(attachment.getProxyUrl(), filename);

                            String playname = VoiceManager.getInstance().getVoiceFileManager().registerSoundFile(filename);
                            event.getMessage().getChannel()
                                    .flatMap(channel -> channel.createMessage("Successfully saved your file, to play it use /play " + playname))
                                    .block();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).then();
    }

    public String getToken() {
        return this.token;
    }
}