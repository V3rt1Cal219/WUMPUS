package menu;

import java.util.Scanner;

public class GameController {
    private GameEngine world;
    private Player player;

    public GameController() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write your name: ");
        String name = scanner.nextLine();

        // Mapsize 6-20
        int size;
        do {
            System.out.println("Please creater the map write between 6-20");
            size = scanner.nextInt();
        } while (size < 6 || size > 20);

        world = new GameEngine(size);
        player = new Player(name, world);
        // Wumpus config to the mapsize
        if (size <= 8) {
            GameEngine.setNumWumpus(1);
        } else if (size <= 14) {
            GameEngine.setNumWumpus(2);
        } else {
            GameEngine.setNumWumpus(3);
        }
        // Arrow config to the wumpus
        Player.setArrows(GameEngine.getNumWumpus());
        world.initializeWorld(player);
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;

        while (!gameOver) {
            world.printWorld(); // Print the map once

            System.out.println("Select a movement | Q- quit | r--l turn | E to shoot arrow): ");
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

        }
    }

}