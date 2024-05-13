package moviePack;

import java.io.InputStream;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

public class jena6 {
    public static void main(String[] args) {
        // Load the ontology
        Model model = ModelFactory.createDefaultModel();
        FileManager fileManager = FileManager.get();
        String owlFile = "Data/movie.owl"; // Replace with the actual filename
        model.read(fileManager.open(owlFile), null);

        // Rule 1: Movies directed by Quentin Tarantino
        askMoviesDirectedByQuentinTarantino(model);

        // Rule 2: Actors who appeared in movies released before 2010
        askActorsInMoviesBefore2010(model);

        // Rule 3: Movies released in a specific country along with their directors
        askMoviesInCountry(model, "USA");
    }

    // Rule 1: Movies directed by Quentin Tarantino
    private static void askMoviesDirectedByQuentinTarantino(Model model) {
        System.out.println("Movies directed by Quentin Tarantino:");
        String queryFile = "Data/ask_director.txt";
        String queryString = readQueryFromFile(queryFile);
        executeAndPrintQuery(model, queryString);
    }

    // Rule 2: Actors who appeared in movies released before 2010
    private static void askActorsInMoviesBefore2010(Model model) {
        System.out.println("Actors who appeared in movies released before 2010:");
        String queryFile = "Data/ask_before_2010.txt";
        String queryString = readQueryFromFile(queryFile);
        executeAndPrintQuery(model, queryString);
    }

    // Rule 3: Movies released in a specific country along with their directors
    private static void askMoviesInCountry(Model model, String country) {
        System.out.println("Movies released in " + country + " along with their directors:");
        String queryFile = "Data/ask_movies_in_country.txt";
        String queryString = readQueryFromFile(queryFile);
        executeAndPrintQuery(model, queryString);
    }

    // Helper method to read the query from file
    private static String readQueryFromFile(String queryFile) {
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = FileManager.get().open(queryFile)) {
            if (inputStream != null) {
                String line;
                java.io.BufferedReader reader = new java.io.BufferedReader(
                        new java.io.InputStreamReader(inputStream, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    // Helper method to execute and print query results
    private static void executeAndPrintQuery(Model model, String queryString) {
        try (QueryExecution qexec = QueryExecutionFactory.create(QueryFactory.create(queryString), model)) {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(System.out, results);
        }
    }
}
