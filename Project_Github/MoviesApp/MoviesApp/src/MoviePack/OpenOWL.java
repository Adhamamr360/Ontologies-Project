// Package 
package MoviePack;

//Imports
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;


class OpenOWL {
   static  String  s;
   
   //Connexion
     static  OntModel OpenConnectOWL(){
        
        OntModel mode = null;
    mode = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_RULE_INF );
    java.io.InputStream in = FileManager.get().open( "movie.owl" ); // be sure file into c:\
    if (in == null) {
        throw new IllegalArgumentException("ontology file not found");
    }
        return  (OntModel) mode.read(in, "");
    }
     // Connecté au OWL File et retourner le Jena ResultSet
     static  org.apache.jena.query.ResultSet ExecSparQl(String Query){
         
          org.apache.jena.query.Query query = QueryFactory.create(Query);
                QueryExecution qe = QueryExecutionFactory.create(query, OpenConnectOWL());
                org.apache.jena.query.ResultSet results = qe.execSelect();
           //    System.out.println("test " + ResultSetFormatter.asText(results, (Prologue) qe));
                //();
                
                return results;
         
     }
     
     // Connecté au OWL File et retourner le String
      static  String GetResultAsString(String Query){
        try {
            org.apache.jena.query.Query query = QueryFactory.create(Query);
                  QueryExecution qe = QueryExecutionFactory.create(query, OpenConnectOWL());
                  org.apache.jena.query.ResultSet results = qe.execSelect();
                  if(results.hasNext()){
                     ByteArrayOutputStream go = new ByteArrayOutputStream ();
                   ResultSetFormatter.out((OutputStream)go ,results, query);
                  //  String s = go.toString();
                   // retouner les resultat de recherche (String ) ;)
                       s = new String(go.toByteArray(), "UTF-8");
                   }
                  else{
                      // si rien trouvé => pour le test 
                      s = "rien";
                  }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OpenOWL.class.getName()).log(Level.SEVERE, null, ex);
        }
         return s;
      }
    
}
//End