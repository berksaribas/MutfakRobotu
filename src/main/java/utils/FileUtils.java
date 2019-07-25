package utils;

import java.util.List;

public class FileUtils {
    private static String[] allowedFileTypes = {"mp3", "wav", "ogg"};

    public static boolean isPlayableAudioFile(String str) {
        for (String allowedFileType : allowedFileTypes) {
            if(str.endsWith(allowedFileType)) {
                return true;
            }
        }

        return false;
    }

    public static String findMostSimilar(List<String> database, String key) {
        String currentlySelected = null;
        double currentlyHighscore = -10000d;

        for (String s : database) {
             double score = LevenshteinDistance.score(s, key);
             if(score > currentlyHighscore) {
                 currentlyHighscore = score;
                 currentlySelected = s;
             }
        }

        return currentlySelected;
    }
}
