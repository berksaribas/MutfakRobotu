package voice;

import utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VoiceFileManager {
    private HashMap<String, String> audioMap;
    private List<String> audioDatabase;

    public VoiceFileManager() {
        audioMap = new HashMap<>();
        audioDatabase = new ArrayList<>();
        skimAllSoundFiles();
    }

    public void skimAllSoundFiles() {
        File folder = new File("./");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && FileUtils.isPlayableAudioFile(file.getName())) {
                String fileName = file.getName();
                String playName = fileName.substring(0, fileName.lastIndexOf("."));
                audioMap.put(playName, fileName);
                audioDatabase.add(playName);
            }
        }
    }

    public String retrieveSound(String soundName) {
        return audioMap.getOrDefault(soundName,"");
    }

    public String findMostSimilarSoundName(String soundName) {
        return FileUtils.findMostSimilar(audioDatabase, soundName);
    }
}
