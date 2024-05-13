package MoviePack;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class MainAppTest {

    @Test
    public void testSearchMovies() {
        MainApp mainApp = new MainApp();
        // Define sample data for test case
        List<String> selectedGenres = Arrays.asList("Action", "Comedy");
        List<String> selectedDirectors = Arrays.asList("Christopher Nolan", "Quentin Tarantino");
        List<String> selectedActors = Arrays.asList("Leonardo DiCaprio", "John Travolta");
        
        // Call the method to be tested
        mainApp.searchMovies(selectedGenres, selectedDirectors, selectedActors);
        String resultText = mainApp.getResultArea().getText();

        // Add assertions to verify the correctness of the results
        // For example, check if the result area contains expected movie titles
        //String resultText = mainApp.resultArea.getText();
        assertTrue(resultText.contains("Inception"));
        assertTrue(resultText.contains("Pulp Fiction"));
    }

    @Test
    public void testGetSelectedItems() {
        MainApp mainApp = new MainApp();

        // Simulate user selection of genres, directors, and actors
        mainApp.getGenreCheckBoxes().get(0).setSelected(true); // Select the first genre
        mainApp.getGenreCheckBoxes().get(2).setSelected(true); // Select the third genre

        mainApp.getDirectorCheckBoxes().get(1).setSelected(true); // Select the second director
        mainApp.getDirectorCheckBoxes().get(3).setSelected(true); // Select the fourth director


        // Call the methods to extract selected items
        List<String> selectedGenres = mainApp.getSelectedGenres();
        List<String> selectedDirectors = mainApp.getSelectedDirectors();
        
        // Assert that the correct items are extracted
        assertEquals(2, selectedGenres.size());
        assertTrue(selectedGenres.contains("Action"));
        assertTrue(selectedGenres.contains("Drama"));

        assertEquals(2, selectedDirectors.size());
        assertTrue(selectedDirectors.contains("Edgar Wright"));
        assertTrue(selectedDirectors.contains("Quentin Tarantino"));


    }
    @Test
    public void testSearchMoviesNoSelection() {
        MainApp mainApp = new MainApp();

        // Simulate no user selection
        List<String> selectedGenres = new ArrayList<>();
        List<String> selectedDirectors = new ArrayList<>();
        List<String> selectedActors = new ArrayList<>();

        // Call the method to search movies with no selection
        mainApp.searchMovies(selectedGenres, selectedDirectors, selectedActors);
        String resultText = mainApp.getResultArea().getText();

        // Assert that the result area displays a message indicating no movies found
        assertEquals("No movies found.", resultText.trim());
    }

    
}

