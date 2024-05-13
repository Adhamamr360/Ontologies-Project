package moviePack;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.query.*;
import org.apache.jena.util.FileManager;

import java.io.InputStream;

public class jena7 {
    public static void main(String[] args) {
        // Load the ontology
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        FileManager fileManager = FileManager.get();
        String owlFile = "Data/movie.owl";
        model.read(fileManager.open(owlFile), null);

        // SPARQL query to retrieve movies with a specific genre
        String queryString = 
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX ex: <http://www.semanticweb.org/mariam/ontologies/2024/3/movie#>\n" +
            "SELECT ?movie\n" +
            "WHERE {\n" +
            "  ?movie rdf:type ex:Movie .\n" +
            "  ?movie ex:title ?movieTitle .\n" +
            "  ?movie ex:year ?releaseYear .\n" +
            "  ?movie ex:HasGenre ex:Action .\n" + // Adjust "ex:Action" to your desired genre
            "}";

        // Execute the query
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.next();
                RDFNode movieNode = solution.get("movie");
                if (movieNode.isResource()) {
                    Resource movieResource = movieNode.asResource();
                    String movieURI = movieResource.getURI();
                    System.out.println("Movie URI: " + movieURI);
                }
            }
        }
    }
}
