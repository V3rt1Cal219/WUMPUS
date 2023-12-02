package menu;

import java.util.Scanner;

public class GameController {
    private GameEngine world;
    private Player player;

    public GameController() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your name?");
        String name = scanner.nextLine();

        // Kérje be a pálya méretét 6 és 20 között
        int size;
        do {
            System.out.println("Please set the map's size (N x N, excluding walls, between 6 and 20): ");
            size = scanner.nextInt();
        } while (size < 6 || size > 20);

        world = new GameEngine(size);
        player = new Player(name, world);
        // Állítsa be a Wumpusok számát a pálya mérete alapján
        if (size <= 8) {
            GameEngine.setNumWumpus(1);
        } else if (size <= 14) {
            GameEngine.setNumWumpus(2);
        } else {
            GameEngine.setNumWumpus(3);
        }
        // Állítsa be a nyilak számát a Wumpusok számával
        Player.setArrows(GameEngine.getNumWumpus());
        world.initializeWorld(player);
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;

        while (!gameOver) {
            world.printWorld(); // Print the map once

            System.out.println("Enter your move (W to move, Q to quit, R/L to turn, E to shoot arrow): ");
            char move = scanner.next().charAt(0);

            switch (move) {
                case 'Q':
                case 'q':
                    System.out.println("Game over. Thanks for playing!");
                    gameOver = true;
                    break;
                case 'R':
                case 'r':
                    player.turnRight();
                    break;
                case 'L':
                case 'l':
                    player.turnLeft();
                    break;
                case 'E':
                case 'e':
                    player.shootArrow();
                    break;
                case 'W':
                case 'w':
                    player.performMove(move);
                    break;
                default:
                    System.out.println("Invalid move. Try again.");
            }

            // Check for game over conditions here if needed
        }
    }

}