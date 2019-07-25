package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
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

    public static void downloadFile(String link, String fileName) throws IOException {
        URL url = new URL(link);
        URLConnection openConnection = url.openConnection();
        openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

        ReadableByteChannel readableByteChannel = Channels.newChannel(openConnection.getInputStream());
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileOutputStream.getChannel()
                .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
    }
}
