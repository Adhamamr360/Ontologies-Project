package moviePack;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.*;
import org.apache.jena.vocabulary.RDF;

public class jena6 {
    public static void main(String[] args) {
        // Load the ontology
        Model model = ModelFactory.createDefaultModel();
        FileManager fileManager = FileManager.get();
        String owlFile = "Data/movie.owl"; // Replace with the actual filename
        model.read(fileManager.open(owlFile), null);

        // Define rules
        String rule1 = "[ruleHasTwoRoles: (?movie <http://www.semanticweb.org/mariam/ontologies/2024/3/movie#HasActor> ?person) (?movie <http://www.semanticweb.org/mariam/ontologies/2024/3/movie#HasDirector> ?person) -> (?person <http://www.semanticweb.org/mariam/ontologies/2024/3/movie#HasTwoRoles> ?movie)]";
        String rule2 = "[ruleMoviesDirectedByQT: (?movie <http://www.semanticweb.org/mariam/ontologies/2024/3/movie#HasDirector> <http://www.semanticweb.org/mariam/ontologies/2024/3/movie#QuentinTarantino>) -> (?movie <http://www.semanticweb.org/mariam/ontologies/2024/3/movie#DirectedByQT> ?movie)]";
        String rule3 = "[ruleMoviesInUSA: (?movie <http://www.semanticweb.org/mariam/ontologies/2024/3/movie#country> \"USA\"^^xsd:string) (?movie <http://www.semanticweb.org/mariam/ontologies/2024/3/movie#HasDirector> ?director) -> (?movie <http://www.semanticweb.org/mariam/ontologies/2024/3/movie#DirectorName> ?director)]";

        // Create a reasoner with the rules
        Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rule1 + " " + rule2 + " " + rule3));

        // Apply the reasoner to the model
        InfModel infModel = ModelFactory.createInfModel(reasoner, model);

        // Execute queries

        askForTwoRoles(infModel);
        askMoviesDirectedByQuentinTarantino(infModel);
        askMoviesInCountry(infModel, "USA");
    }

 // Rule 1: Persons Who Are Actors And Directors
    private static void askForTwoRoles(Model model) {
        System.out.println("Persons who are actors and directors:");
        String queryString = "SELECT ?person WHERE {?person <http://www.semanticweb.org/mariam/ontologies/2024/3/movie#HasTwoRoles> ?movie}";
        executeAndPrintQuery(model, queryString);
    }


    // Rule 2: Movies Directed by Quentin Tarantino
    private static void askMoviesDirectedByQuentinTarantino(Model model) {
        System.out.println("Movies directed by Quentin Tarantino:");
        String queryString = "SELECT ?movieTitle WHERE {?movie <http://www.semanticweb.org/mariam/ontologies/2024/3/movie#DirectedByQT> ?movie . ?movie <http://www.semanticweb.org/mariam/ontologies/2024/3/movie#title> ?movieTitle}";
        executeAndPrintQuery(model, queryString);
    }

    // Rule 3: Movies Released in a Specific Country with Directors' Names
    private static void askMoviesInCountry(Model model, String country) {
        System.out.println("Movies released in " + country + " along with their directors:");
        String queryString = "SELECT ?movieTitle ?director WHERE {?movie <http://www.semanticweb.org/mariam/ontologies/2024/3/movie#DirectorName> ?director . ?movie <http://www.semanticweb.org/mariam/ontologies/2024/3/movie#title> ?movieTitle}";
        executeAndPrintQuery(model, queryString);
    }

    // Helper method to execute and print query results
    private static void executeAndPrintQuery(Model model, String queryString) {
        try (QueryExecution qexec = QueryExecutionFactory.create(queryString, model)) {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(System.out, results);
        }
    }
}
