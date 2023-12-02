package game;

import java.util.Scanner;

public class WumpusWorld {

    private static int size;
    private static char[][] world;
    private static int heroX;
    private static int heroY;
    private static int spawnX;
    private static int spawnY;
    private static int goldX;
    private static int goldY;
    private static int pitX;
    private static int pitY;
    private static int numWumpus;
    private static int arrows;
    private static char direction = 'W'; // Starting direction
    private static boolean hasGold = false;
    private static String name;


    public WumpusWorld() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write your name: ");
        name = scanner.nextLine();
        // Map design between 6-20
        do {
            System.out.println("Please give a number between 6 and 20.): ");
            size = scanner.nextInt();
        } while (size < 6 || size > 20);
        // Wumpus spawn rate
        if (size <= 8) {
            numWumpus = 1;
        } else if (size <= 14) {
            numWumpus = 2;
        } else {
            numWumpus = 3;
        }
        // Állítsa be a nyilak számát a Wumpusok számával
        arrows = numWumpus;
        initializeWorld();
        printWorld();
        while (true) {
            System.out.println("Select a movement | Q- quit | r--l turn | E to shoot arrow): ");
            char move = scanner.next().charAt(0);
            if (move == 'Q' || move == 'q') {
                System.out.println("Game over. Thanks for playing!");
                break;
            } else if (move == 'R' || move == 'r') {
                // Right turn
                turnRight();
                printWorld(); // New orientation mapdraw
            } else if (move == 'L' || move == 'l') {
                // Left turn
                turnLeft();
                printWorld(); // New orientation mapdraw
            } else if (move == 'E' || move == 'e') {
                // Shooting the arrow the right direction
                shootArrow();
                printWorld(); // New orientation mapdraw
            } else {
                performMove(move);
                printWorld();
            }
        }
    }

    private static void initializeWorld() {
        // Inside mapsize
        int worldSize = size + 2;
        world = new char[worldSize][worldSize];

        // Initialize the world with walls and empty cells
        for (int i = 0; i < worldSize; i++) {
            for (int j = 0; j < worldSize; j++) {
                if (i == 0 || i == worldSize - 1 || j == 0 || j == worldSize - 1) {
                    world[i][j] = 'W'; // Walls
                } else {
                    world[i][j] = ' ';
                }
            }
        }

        // Place the hero in an empty random position
        do {
            heroX = (int) (Math.random() * (size)) + 1;
            heroY = (int) (Math.random() * (size)) + 1;
        } while (world[heroX][heroY] == 'W');

        world[heroX][heroY] = 'H';
        spawnX = heroX;
        spawnY = heroY;

        // Placing the Wumpus to the right position
        for (int k = 0; k < numWumpus; k++) {
            int wumpusX;
            int wumpusY;
            do {
                wumpusX = (int) (Math.random() * (size)) + 1;
                wumpusY = (int) (Math.random() * (size)) + 1;
            } while (world[wumpusX][wumpusY] == 'W' || (wumpusX == heroX && wumpusY == heroY));

            world[wumpusX][wumpusY] = 'U'; // Wumpus 'U'-val jelölve
        }

        // Place the gold in an empty random position
        do {
            goldX = (int) (Math.random() * (size)) + 1;
            goldY = (int) (Math.random() * (size)) + 1;
        } while (world[goldX][goldY] == 'W' || (goldX == heroX && goldY == heroY));

        world[goldX][goldY] = 'G';

        // Place the pits at random positions
        for (int p = 0; p < numWumpus; p++) {
            do {
                pitX = (int) (Math.random() * (size)) + 1;
                pitY = (int) (Math.random() * (size)) + 1;
            } while (world[pitX][pitY] == 'W' || world[pitX][pitY] == 'P' || world[pitX][pitY] == 'H' || world[pitX][pitY] == 'G');

            world[pitX][pitY] = 'P';
        }
    }

    private static void printWorld() {
        // Print the current state of the world
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world.length; j++) {
                System.out.print(world[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Direction: " + direction); // Display the current direction
        System.out.println("Arrows: " + arrows); // Display the currecnt arrow used
        System.out.println();
    }

    private static void performMove(char move) {
        // Move the hero based on user input
        world[heroX][heroY] = ' '; // Clear the current position

        int prevHeroX = heroX;
        int prevHeroY = heroY;

        // Movement
        switch (move) {
            case 'W':
            case 'w':
                moveForward();
                break;
            case 'A':
            case 'a':
                turnLeft();
                break;
            case 'S':
            case 's':
                turnAround();
                break;
            case 'D':
            case 'd':
                turnRight();
                break;
            case 'Q':
            case 'q':
                System.out.println("Game over. Thanks for playing!");
                System.exit(0);
            default:
                performMove(move);
                printWorld();
        }

        // Check for collisions
        if (world[heroX][heroY] == 'U') {
            System.out.println("Wumpus clown got you, you dead.");
            System.exit(0);
        } else if (world[heroX][heroY] == 'G') {
            System.out.println("You are rich , " + name + "! You found all the gold(s)!");

            // Gold check
            hasGold = true;
        } else if (world[heroX][heroY] == 'P') {
            System.out.println("You stuck in a backroom. The darkside got you. There is no escape from there.");
            arrows--;

            // Check if the hero is out of arrows
            if (arrows <= 0) {
                System.out.println("Out of arrows! Game over.");
                System.exit(0);
            }

            // Respawn the pit at the hero's previous position
            world[prevHeroX][prevHeroY] = 'P';

            // Check if the hero is out of arrows after falling into a pit
            if (arrows <= 0) {
                System.out.println("Out of arrows! Game over.");
                System.exit(0);
            }
        }

        // Hero new location
        world[heroX][heroY] = 'H';

        // Check if the hero is back to the starting position with the gold
        if (hasGold && heroX == spawnX && heroY == spawnY) {
            System.out.println("You win, " + name + "!");
            System.exit(0);
        }
    }


    private static void moveForward() {
        // Movement forward
        switch (direction) {
            case 'N':
                if (heroX > 1 && world[heroX - 1][heroY] != 'W') {
                    heroX--;
                }
                break;
            case 'E':
                if (heroY < size && world[heroX][heroY + 1] != 'W') {
                    heroY++;
                }
                break;
            case 'S':
                if (heroX < size && world[heroX + 1][heroY] != 'W') {
                    heroX++;
                }
                break;
            case 'W':
                if (heroY > 1 && world[heroX][heroY - 1] != 'W') {
                    heroY--;
                }
                break;
            default:
                break;
        }
    }

    private static void turnLeft() {
        // Movement w/ left turn
        switch (direction) {
            case 'N':
                direction = 'W';
                break;
            case 'E':
                direction = 'N';
                break;
            case 'S':
                direction = 'E';
                break;
            case 'W':
                direction = 'S';
                break;
            default:
                break;
        }
        System.out.println("Direction: " + direction);
    }

    private static void turnRight() {
        // Movement w/ right turn
        switch (direction) {
            case 'N':
                direction = 'E';
                break;
            case 'E':
                direction = 'S';
                break;
            case 'S':
                direction = 'W';
                break;
            case 'W':
                direction = 'N';
                break;
            default:
                break;
        }
        System.out.println("Direction: " + direction);
    }

    private static void turnAround() {
        // 2x left turn
        turnLeft();
        turnLeft();
    }

    private static void shootArrow() {
        // Arrow minus if you shoot
        if (arrows > 0) {
            arrows--;

            System.out.println("You shot an arrow! Arrows left: " + arrows);

            // Hit check
            int arrowX = heroX;
            int arrowY = heroY;

            while (true) {
                switch (direction) {
                    case 'N':
                        arrowX--;
                        break;
                    case 'E':
                        arrowY++;
                        break;
                    case 'S':
                        arrowX++;
                        break;
                    case 'W':
                        arrowY--;
                        break;
                    default:
                        break;
                }

                // Arrow disappear
                if (arrowX <= 0 || arrowX >= size || arrowY <= 0 || arrowY >= size) {
                    System.out.println("You missed the wumpus and shoot across the wall. Nice aim bro.");
                    break;
                }

                // Wumpus got a shoot

                if (world[arrowX][arrowY] == 'U') {
                    System.out.println("Nice aim, You killed the Wumpus.");
                    world[arrowX][arrowY] = 'X';
                    break;
                }
            }
        } else {
            System.out.println("Out of arrows! You can no longer shoot.");
        }
    }
}