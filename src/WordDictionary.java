import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

public class WordDictionary {

    HashMap<String, Integer> wordMap = new HashMap<>();
    public WordDictionary() throws IOException {
        addWordToMap();
    }

    private void addWordToMap() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resourceURL = classLoader.getResource("words");
        assert resourceURL != null;

        InputStream inputStream = resourceURL.openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line =reader.readLine()) != null) {
            if (line.length() >= 3 && line.length() <= 16)
                this.wordMap.put(line.toLowerCase(), line.length());
        }
    }

    public HashMap<String, Integer> getWordMap() {
        return this.wordMap;
    }

}
