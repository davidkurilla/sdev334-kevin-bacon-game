// PACKAGE INFORMATION
package driver;

// IMPORTS
import graphs.MyGraph;
import helpers.DataPoint;
import io.MoviesIO;
import java.util.*;

// CLASS: MAIN
/**
 *
 * This class is my Kevin Bacon Game.
 * @author David Kurilla
 * @version 1.0
 */
public class Main
{

    // FIELDS
    private static MyGraph<DataPoint> graph = new MyGraph<>();
    private static String file;

    // METHOD: main()
    /**
     * Main Method
     * @param args | NOT USED
     */
    public static void main(String[] args)
    {
        welcomeMessage();
        printFileMenu();
        menu:
        while(true) {
            Console.println("Make a choice:");
            Console.println("1. List Actors");
            Console.println("2. List Movies");
            Console.println("3. Calculate Actors Numbers");
            Console.println("4. Exit");
            Console.print("> ");
            int input = Console.getInt();
            switch (input) {
                case 1:
                    listActors(file);
                    break;
                case 2:
                    listMovies(file);
                    break;
                case 3:
                    calculateActorNumbers();
                    break;
                case 4:
                    break menu;
                default:
                    Console.println("ERROR: Invalid selection!");
                    break;
            }
        }
    }

    // METHOD: calculateActorNumbers()
    private static void calculateActorNumbers() {
        Console.println();
        String actor = Console.getString("Enter an actor/actress name").toLowerCase();
        if (foundActor(actor)) {
            Console.println("Found actor!");
            Console.println();
            DataPoint actorVertex = new DataPoint(actor, true);
            Map<String, Integer> actorNums = calculate(actorVertex);
            for (String actorName : actorNums.keySet()) {
                Console.println(actorName + " -> " + actorNums.get(actorName));
            }
            Console.println();
            double averageActorNumber = calcAverage(actorNums);
            Console.println("Average actor number: " + averageActorNumber);
        } else {
            Console.println("Actor not Found!");
        }
    }

    // METHOD: calcAverage
    private static double calcAverage(Map<String, Integer> actorNums) {
        double total = actorNums.keySet().size();
        double actorNumbers = 0;
        for (String actor : actorNums.keySet()) {
            actorNumbers += actorNums.get(actor);
        }
        return actorNumbers / total;
    }

    // METHOD: calculate()
    private static Map<String, Integer> calculate(DataPoint actorVertex) {
        Map<String, Integer> actorNums = new HashMap<>();
        actorNums.put(actorVertex.getName(), 0);
        Set<DataPoint> visitedVertices = new HashSet<>();

        Queue<DataPoint> bfsQueue = new LinkedList<>();
        bfsQueue.add(new DataPoint(actorVertex.getName(), true));
        int distance = 0;
        DataPoint lastVisitedVertex = null;
        while (!bfsQueue.isEmpty()) {
            DataPoint nextVertex = bfsQueue.poll();
            if (!visitedVertices.contains(nextVertex)) {
                visitedVertices.add(nextVertex);
                if (lastVisitedVertex != null && !lastVisitedVertex.isActor() && nextVertex.isActor()) {
                    distance++;
                }
                if (nextVertex.isActor()) {
                    actorNums.put(nextVertex.getName(), distance);
                }
                List<DataPoint> adjacentVertices = graph.getAdjacentVertices(nextVertex);
                for (DataPoint vertex : adjacentVertices) {
                    if(!visitedVertices.contains(vertex)) {
                        bfsQueue.offer(vertex);
                    }
                }
                lastVisitedVertex = nextVertex;
            }
        }
        return actorNums;
    }

    // METHOD: foundActor()
    private static boolean foundActor(String actor) {
        Map<String, List<String>> data = MoviesIO.getData(file);
        return data.containsKey(actor);
    }

    // METHOD: listActors()
    private static void listActors(String file) {
        Map<String, List<String>> data = MoviesIO.getData(file);
        List<String> actorList = new ArrayList<>();
        for (String actor : data.keySet()) {
            String actorListItem = "Actor: " + actor;
            actorList.add(actorListItem);
        }
        actorList.sort(null);
        for (String actorItem : actorList) {
            Console.println(actorItem);
        }
    }

    // METHOD: listMovies()
    private static void listMovies(String file) {
        Map<String, List<String>> data = MoviesIO.getData(file);
        HashSet<String> movieSet = new HashSet<>();
        for (String actor : data.keySet()) {
            for (String movie : data.get(actor)) {
                String movieItem = "Movie: " + movie;
                movieSet.add(movieItem);
            }
        }
        List<String> movieList = new ArrayList<>(movieSet);
        movieList.sort(null);
        for (String movie : movieList) {
            Console.println(movie);
        }
    }

    // METHOD: welcomeMessage()
    private static void welcomeMessage() {
        Console.println("Welcome to my Kevin Bacon Game!");
        Console.println("******************************");
        Console.println();
    }

    // METHOD: printFileMenu()
    private static void printFileMenu() {
        Console.println("Please choose one of the following files:");
        Console.println("0: 400_actors.txt");
        Console.println("1: 200_actors.txt");
        Console.println("2: 100_actors.txt");
        Console.println("3: 50_actors.txt");
        Console.print("> ");
        int input = Console.getInt();
        processFileInput(input);
    }

    // METHOD: processFileInput()
    private static void processFileInput(int input) {
        switch (input) {
            case 0:
                file = "400_actors";
                break;
            case 1:
                file = "200_actors";
                break;
            case 2:
                file = "100_actors";
                break;
            case 3:
                file = "50_actors";
                break;
            default:
                Console.println("ERROR: Invalid selection!");
                System.exit(0);
                return;
        }
        buildGraph(graph, file);
    }

    // METHOD: buildGraph()
    private static void buildGraph(MyGraph<DataPoint> graph, String file) {
        Map<String, List<String>> data = MoviesIO.getData(file);
        int movieCount = 0;
        for (String actor : data.keySet()) {
            DataPoint actorVertex = new DataPoint(actor, true);
            graph.addVertex(actorVertex);

            for (String movie : data.get(actor)) {
                DataPoint movieVertex = new DataPoint(movie, false);
                graph.addVertex(movieVertex);
                graph.addEdge(actorVertex, movieVertex);
                movieCount++;
            }
        }
        Console.println();
        System.out.printf("Loaded %d actors and %d movies", data.keySet().size(), movieCount);
        Console.println();
    }
}