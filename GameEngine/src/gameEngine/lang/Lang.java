/**
 * BatBall2
 * Created by Philip on 10/09/2016 at 22:01.
 */
package gameEngine.lang;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class Lang {

    private static Dictionary<String, String> lang;

    public static boolean init(String lang) {
        try {
            Path langFile = Paths.get("lang/" + lang + ".lang");
            if (!Files.exists(langFile))
                langFile = Paths.get("lang/EN_GB.lang");

            List<String> strings = Files.readAllLines(langFile);
            Lang.lang = new Hashtable<>();
            for (String string : strings) {
                Lang.lang.put(string.substring(0, string.indexOf("=")), string.substring(string.indexOf("=") + 1));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String get(String key) {
        if (langContains(key))
            return lang.get(key);
        return key;
    }

    private static boolean langContains(String key) {
        for (Enumeration<String> e = lang.keys(); e.hasMoreElements(); ) {
            if (e.nextElement().equals(key))
                return true;
        }
        return false;
    }
}

