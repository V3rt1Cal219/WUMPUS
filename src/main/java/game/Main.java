package game;

import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Wumpus main menu");
            System.out.println("1. Start Game");
            System.out.println("2. Load from database");
            System.out.println("3. Quit");

            System.out.print("Enter your choice (1 or 3.     2. is under maintenance): ");
            int choice = scanner.nextInt();


            switch (choice) {
                case 1:
                    startGame();
                    break;
                case 2:
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 or 3.");
            }
        }
    }

    private static void startGame() {
        System.out.println("Starting Wumpus Game..");
        new WumpusWorld();
    }
}
