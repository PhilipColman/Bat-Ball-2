/**
 * BatBall2
 * Created by Philip on 09/09/2016 at 01:23.
 */
package gameEngine.logging;

import gameEngine.GameEngine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static gameEngine.logging.Level.*;

public class Log {

    private final String name;

    private static BufferedWriter log;

    public Log(String name) {
        this.name = name;
        if (log == null) {
            init();
        }
        info("Log Loaded");
    }

    private static void init() {
        try {
            String date = String.format("%1$td.%1$tm.%1$tY", System.currentTimeMillis());
            File logFile = createFile(date);
            log = Files.newBufferedWriter(Paths.get(logFile.getPath()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(Object message, Level level) {
        String out = String.format("[%1$tH:%1$tM:%1$tS] [%2$s] [%3$s]: %4$s", System.currentTimeMillis(), level.toString(), name, message);
        try {
            log.write(out);
            log.newLine();
            log.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (GameEngine.isDebugEnabled()) {
            if (level == INFO || level == TRACE || level == DEBUG || level == WARNING) {
                System.out.println(out);
            } else if (level == ERROR || level == FATAL) {
                System.err.println(out);
            }
        }

    }

    public void info(Object message) {
        log(message, INFO);
    }

    public void debug(Object message) {
        log(message, DEBUG);
    }

    public void error(Object message) {
        log(message, ERROR);
    }

    public void fatal(Object message) {
        log(message, FATAL);
    }

    public void trace(Object message) {
        log(message, TRACE);
    }

    public void warn(Object message) {
        log(message, WARNING);
    }

    private static File createFile(String name) throws IOException {
        File folder = new File("logs");
        if (!folder.exists())
            folder.mkdir();
        Integer i = 0;
        File file;
        do {
            file = new File(String.format("logs/Log - %s%s.log", name, i == 0 ? "" : " - " + i.toString()));
            i++;
        } while (file.exists());
        file.createNewFile();
        return file;
    }

    public static void closeLog() {
        try {
            log.close();
            log = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

enum Level {
    INFO,
    TRACE,
    DEBUG,
    WARNING,
    ERROR,
    FATAL
}