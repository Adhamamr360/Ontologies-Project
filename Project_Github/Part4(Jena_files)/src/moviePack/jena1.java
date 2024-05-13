package moviePack;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.RDF;

/*
 *  loads the ontology and displays all the Persons
	(without using queries, without inference).
 * */

public class jena1 {
    public static void main(String[] args) {
        // Load the ontology
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        FileManager fileManager = FileManager.get();
        String owlFile = "Data/movie.owl";
        model.read(fileManager.open(owlFile), null);

        // List Persons
        OntClass personClass = model.getOntClass("http://www.semanticweb.org/mariam/ontologies/2024/3/movie#Person");
        ExtendedIterator<? extends OntResource> personIterator = personClass.listInstances();
        while (personIterator.hasNext()) {
            OntResource personResource = personIterator.next();
            
            // Get the name of the person
            Property nameProperty = model.getProperty("http://www.semanticweb.org/mariam/ontologies/2024/3/movie#name");
            StmtIterator personNameIterator = personResource.listProperties(nameProperty);
            while (personNameIterator.hasNext()) {
                Statement personNameStatement = personNameIterator.next();
                RDFNode personNameNode = personNameStatement.getObject();
                if (personNameNode != null && personNameNode.isLiteral()) {
                    String personName = personNameNode.asLiteral().getString();
                    System.out.println("Person: " + personName);
                }
            }

        }
    }
}
