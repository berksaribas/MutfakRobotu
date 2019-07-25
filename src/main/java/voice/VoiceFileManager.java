package voice;

import utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class VoiceFileManager {
    private HashMap<String, String> audioMap;
    private List<String> audioDatabase;

    public VoiceFileManager() {
        audioMap = new HashMap<>();
        audioDatabase = new ArrayList<>();
        skimAllSoundFiles();
    }

    public void skimAllSoundFiles() {
        try (Stream<Path> paths = Files.walk(Paths.get("./"))) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> FileUtils.isPlayableAudioFile(p.getFileName().toString()))
                    .forEach(a -> {
                        String fileName = a.getFileName().toString();
                        String playName = fileName.substring(0, fileName.lastIndexOf("."));
                        audioMap.put(playName, fileName);
                        audioDatabase.add(playName);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String retrieveSound(String soundName) {
        return audioMap.getOrDefault(soundName,"");
    }

    public String findMostSimilarSoundName(String soundName) {
        return FileUtils.findMostSimilar(audioDatabase, soundName);
    }
}
