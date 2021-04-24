package service;
import java.sql.Timestamp;
import java.util.Date;
import java.io.*;
import java.nio.file.*;
public class LoggingService {
    private static final String DIRECTORY_PATH = "resources/logging";
    private static final String FILE_PATH = DIRECTORY_PATH + "/log.csv";

    public void logEvent(String actionName) {
        if(!Files.exists(Paths.get(DIRECTORY_PATH))) {
            try {
                Files.createDirectories(Paths.get(DIRECTORY_PATH));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        if(!Files.exists(Paths.get(FILE_PATH))) {
            try {
                Files.createFile(Paths.get(FILE_PATH));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH),
                        StandardOpenOption.APPEND);
                writer.write("nume_actiune,timestamp\n");
                writer.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH),
                    StandardOpenOption.APPEND);
            Date date = new Date();
            long time = date.getTime();
            Timestamp timestamp = new Timestamp(time);
            writer.write(actionName + "," + timestamp + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
