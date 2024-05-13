package MoviePack;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.query.*;
import org.apache.jena.util.FileManager;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class MainApp extends JFrame implements ActionListener {
    private JButton searchButton;
    private JTextArea resultArea;
    private List<JCheckBox> genreCheckBoxes;
    private List<JCheckBox> directorCheckBoxes;
    private List<JCheckBox> actorCheckBoxes;
    private ImageIcon movieIcon;
    private JLabel mylabel;
    
    

    public MainApp() {
//    	movieIcon= new ImageIcon(this.getClass().getResource("/cinema.png"));
//    	mylabel= new JLabel(movieIcon);
//    	mylabel.setSize(700,500);
//    	
    	setTitle("Movie Finder");
    	//add(mylabel);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    
        
      
     
    
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10));
//        // Use GridLayout for organizing components with spacing
        

       
//     // Genre input
//        JLabel genreLabel = new JLabel("Genre:");
//        Font labelFont = genreLabel.getFont();
//        genreLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25)); // Adjust the size here
//        panel.add(genreLabel);
//
//        genreField = new JTextField(15); // Reduced columns
//        panel.add(genreField);
        
      //Genre input as checkboxes
        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setFont(new Font(genreLabel.getFont().getName(), Font.BOLD, 25));
        panel.add(genreLabel);

        // Create genre checkboxes
        JPanel genreCheckBoxPanel = new JPanel(new GridLayout(0, 1));
        String[] genres = {"Action", "Comedy", "Drama", "Thriller", "Crime"};
        genreCheckBoxes = new ArrayList<>();
        for (String genre : genres) {
            JCheckBox checkBox = new JCheckBox(genre);
            genreCheckBoxes.add(checkBox);
            genreCheckBoxPanel.add(checkBox);
        }
        panel.add(genreCheckBoxPanel);
        
        
     



//     // Director input
//        JLabel directorLabel = new JLabel("Director:");
//        Font label_Font = directorLabel.getFont();
//        directorLabel.setFont(new Font(label_Font.getName(), Font.BOLD, 25)); // Adjust the size here
//        panel.add(directorLabel);
//
//        directorField = new JTextField(15); // Reduced columns
//        panel.add(directorField);
        
     // Director input as checkboxes
        JLabel directorLabel = new JLabel("Director:");
        directorLabel.setFont(new Font(directorLabel.getFont().getName(), Font.BOLD, 25));
        panel.add(directorLabel);

        JPanel directorCheckBoxPanel = new JPanel(new GridLayout(0, 1));
        String[] directors = {"Christopher Nolan", "Edgar Wright", "Paul Thomas Anderson","Quentin Tarantino","Sandra Nashaat","Taika Waititi","Todd Philips"};
        directorCheckBoxes = new ArrayList<>();
        for (String director : directors) {
            JCheckBox checkBox = new JCheckBox(director);
            directorCheckBoxes.add(checkBox);
            directorCheckBoxPanel.add(checkBox);
        }
        panel.add(directorCheckBoxPanel);
        
     

//     // Actor input
//        JLabel actorLabel = new JLabel("Actor:");
//        Font labelFontt = actorLabel.getFont();
//        actorLabel.setFont(new Font(labelFontt.getName(), Font.BOLD, 25)); // Adjust the size here
//        panel.add(actorLabel);
//
//        actorField = new JTextField(15); // Reduced columns
//        panel.add(actorField);
        
     // Actor input as checkboxes
        JLabel actorLabel = new JLabel("Actor:");
        actorLabel.setFont(new Font(actorLabel.getFont().getName(), Font.BOLD, 25));
        panel.add(actorLabel);

        JPanel actorCheckBoxPanel = new JPanel(new GridLayout(0, 1));
        String[] actors = {"Edgar Wright", "John Travolta", "Leonardo DiCaprio", "Paul Thomas Anderson", "Quentin Tarantino","Uma Thurman","Taika Waitit"}; 
        actorCheckBoxes = new ArrayList<>();
        for (String actor : actors) {
            JCheckBox checkBox = new JCheckBox(actor);
            actorCheckBoxes.add(checkBox);
            actorCheckBoxPanel.add(checkBox);
        }
        panel.add(actorCheckBoxPanel);
        
        
     // Add icon after the search button
        movieIcon = new ImageIcon(this.getClass().getResource("/cinema.png"));
        Image image = movieIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Resize the icon
        movieIcon = new ImageIcon(image);
        JLabel iconLabel = new JLabel(movieIcon);

        // Create a panel for the icon with BorderLayout
        JPanel iconPanel = new JPanel(new BorderLayout());
        iconPanel.add(iconLabel, BorderLayout.CENTER);

        // Add the icon panel to the EAST position of the main panel
        panel.add(iconPanel, BorderLayout.EAST);
        
     // Empty label for spacing
        panel.add(new JLabel());
        


        
     // Search button
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel()); // Empty label for spacing
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        searchButton.setBackground(Color.BLACK); 
        searchButton.setForeground(Color.WHITE);
        searchButton.setPreferredSize(new Dimension(120, 40));
        searchPanel.add(searchButton);
        
        panel.add(searchPanel);

        // Empty label for spacing
        panel.add(new JLabel());
        
     
     
 

        
     // Result area
        JLabel resultsLabel = new JLabel("Search Results:");
        Font labellFont = resultsLabel.getFont();
        resultsLabel.setFont(new Font(labellFont.getName(), Font.BOLD, 25)); // Adjust the size here
        panel.add(resultsLabel);

        resultArea = new JTextArea(30, 50); // Reduced rows and columns
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane);

        add(panel);
        setVisible(true);
    }
    
    public JTextArea getResultArea() {
        return resultArea;
    }
    public List<JCheckBox> getGenreCheckBoxes() {
        return genreCheckBoxes;
    }
    public List<JCheckBox> getDirectorCheckBoxes() {
        return directorCheckBoxes;
    }
    public List<JCheckBox> getActorCheckBoxes() {
        return actorCheckBoxes;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
           // String genre = genreField.getText();
            //String directorName = directorField.getText();
            //String directorName = (String) directorComboBox.getSelectedItem();
            //String actorName = actorField.getText();
            List<String> selectedGenres = getSelectedGenres();
            //String selectedGenres = getSelectedGenres();
            List<String> selectedDirectors = getSelectedDirectors();
            List<String> selectedActors = getSelectedActors();

            // Call the existing functionality with the provided inputs
            //searchMovies(selectedGenres);

            // Call the existing functionality with the provided inputs
            searchMovies(selectedGenres, selectedDirectors, selectedActors);
        }
    }
    public List<String> getSelectedGenres() {
        List<String> selectedGenres = new ArrayList<>();
        for (JCheckBox checkBox : genreCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedGenres.add(checkBox.getText());
            }
        }
        return selectedGenres;
    }
    
    public List<String> getSelectedDirectors() {
        List<String> selectedDirectors = new ArrayList<>();
        for (JCheckBox checkBox : directorCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedDirectors.add(checkBox.getText());
            }
        }
        return selectedDirectors;
    }
    
    public List<String> getSelectedActors() {
        List<String> selectedActors = new ArrayList<>();
        for (JCheckBox checkBox : actorCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedActors.add(checkBox.getText());
            }
        }
        return selectedActors;
    }


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(MainApp::new);
//    }
    
    


    public void searchMovies(List<String> genres, List<String> directors,List<String> actors) {
    	if (genres.isEmpty() && directors.isEmpty() && actors.isEmpty()) {
            // If no selection is made, display "No movies found"
            resultArea.setText("No movies found.");
            return;
        }
        // Load the ontology
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        FileManager fileManager = FileManager.get();
        String owlFile = "movie.owl";
        model.read(fileManager.open(owlFile), null);

     // Construct the SPARQL query
        StringBuilder queryString = new StringBuilder();
        queryString.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n");
        queryString.append("PREFIX ex: <http://www.semanticweb.org/mariam/ontologies/2024/3/movie#>\n");
        queryString.append("SELECT ?movieTitle ?releaseYear\n");
        queryString.append("WHERE {\n");
        queryString.append("  ?movie rdf:type ex:Movie .\n");
        queryString.append("  ?movie ex:title ?movieTitle .\n");
        queryString.append("  ?movie ex:year ?releaseYear .\n");

        // Add triple patterns for each condition with UNION
        boolean isFirstCondition = true;

        for (String director : directors) {
            queryString.append(isFirstCondition ? "" : " UNION ");
            queryString.append("{ ?movie ex:HasDirector ex:").append(director.replace(" ", "")).append(" . } \n");
            isFirstCondition = false;
        }

        for (String genre : genres) {
            queryString.append(isFirstCondition ? "" : " UNION ");
            queryString.append("{ ?movie ex:HasGenre ex:").append(genre).append(" . } \n");
            isFirstCondition = false;
        }

        for (String actor : actors) {
            queryString.append(isFirstCondition ? "" : " UNION ");
            queryString.append("{ ?movie ex:HasActor ex:").append(actor.replace(" ", "")).append(" . } \n");
            isFirstCondition = false;
        }

        queryString.append("}");

        // Execute the query
        Query query = QueryFactory.create(queryString.toString());
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            StringBuilder output = new StringBuilder();
            while (results.hasNext()) {
                QuerySolution solution = results.next();
                RDFNode movieTitleNode = solution.get("movieTitle");
                RDFNode releaseYearNode = solution.get("releaseYear");

                String movieTitle = movieTitleNode == null ? "" : movieTitleNode.toString();
                int releaseYear = releaseYearNode == null ? 0 : releaseYearNode.asLiteral().getInt();

                output.append("Movie Title: ").append(movieTitle).append("\n");
                output.append("Release Year: ").append(releaseYear).append("\n\n");
            }

            if (output.length() > 0) {
                // Display movies in the result area
                resultArea.setText(output.toString());
            } else {
                // Clear the previous search results
                resultArea.setText("No movies found.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::new);
    }
}