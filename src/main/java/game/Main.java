package game;

import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Wumpus World Menu:");
            System.out.println("1. Start Game");
            System.out.println("2. Quit");

            System.out.print("Enter your choice (1 or 2): ");
            int choice = scanner.nextInt();


            switch (choice) {
                case 1:
                    startGame();
                    break;
                case 2:
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 2.");
            }
        }
    }

    private static void startGame() {
        System.out.println("Starting Wumpus World Game...");
        new WumpusWorld();
    }
}
