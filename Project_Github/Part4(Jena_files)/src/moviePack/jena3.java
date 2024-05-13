package moviePack;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.RDF;

/*
 *  loads the ontology and displays all the Actors
	(without using queries, using inference).
 * */

public class jena3 {
    public static void main(String[] args) {
        // Load the ontology
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        FileManager fileManager = FileManager.get();
        String owlFile = "Data/movie.owl"; // Replace with the actual filename
        model.read(fileManager.open(owlFile), null);

        // Create a reasoner with OWL rules
        Reasoner reasoner = GenericRuleReasonerFactory.theInstance().create(null);
        InfModel infModel = ModelFactory.createInfModel(reasoner, model);

        // List all actors
        OntClass actorClass = model.getOntClass("http://www.semanticweb.org/mariam/ontologies/2024/3/movie#Actor");
        ExtendedIterator<? extends OntResource> actorIterator = actorClass.listInstances();
        while (actorIterator.hasNext()) {
            OntResource actorResource = actorIterator.next();
            Property nameProperty = model.getProperty("http://www.semanticweb.org/mariam/ontologies/2024/3/movie#name");
            StmtIterator actorNameIterator = actorResource.listProperties(nameProperty);
            while (actorNameIterator.hasNext()) {
                Statement actorNameStatement = actorNameIterator.next();
                RDFNode actorNameNode = actorNameStatement.getObject();
                if (actorNameNode != null && actorNameNode.isLiteral()) {
                    String actorName = actorNameNode.asLiteral().getString();
                    System.out.println("Actor: " + actorName);
                }
            }
        }
    }
}
