package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

/**
 * A data layer for reading from input files in the
 * Kevin Bacon game.
 * @author David Kurilla
 * @version 1.0
 */
public class MoviesIO
{
    /**
     * Reads from the filePath provided and parses the input
     * file.
     *
     * @param filePath STRING | Path to file
     * @return a map of actors (keys) to a list of movies they star in (values)
     */
    public static Map<String, List<String>> getData(String filePath)
    {
        Map<String, List<String>> data = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File("files/" + filePath + ".txt"));
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(":", 2);
                String actor = split[0];
                String[] movies = split[1].split("\\|");
                List<String> movieList = new LinkedList<>(Arrays.asList(movies));
                data.put(actor, movieList);
            }
        } catch (FileNotFoundException exception) {
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, exception.toString());
        }
        return data;
    }
}
