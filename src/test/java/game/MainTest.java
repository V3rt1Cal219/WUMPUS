package game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class MainTest {

    @Test
    public void testStartGame() {
        // Redirect System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Call startGame method
        Main.startGame();

        // Reset System.out
        System.setOut(System.out);

        // Check if the output contains the expected message
        assertTrue(outputStream.toString().contains("Starting Wumpus Game.."));
    }

    // Example of a test for the WumpusWorld class (assuming it has a default constructor)
    @Test
    public void testWumpusWorldCreation() {
        WumpusWorld wumpusWorld = new WumpusWorld();
        assertNotNull(wumpusWorld);
        // Add more assertions or tests for the WumpusWorld class as needed
    }

    // Example of a test for invalid user input in the main menu
    @Test
    public void testInvalidMainMenuChoice() {
        // Redirect System.in to provide input during the test
        String input = "4\n"; // Invalid choice
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Call the main method
        Main.main(null);

        // Reset System.in
        System.setIn(System.in);
    }
}