import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    private String[][] gameAreaArray;
    private boolean[][] doesHaveMineArray;
    private int roundsCompleted = 0;
    private int totalMines = 0;

    public Minesweeper(int rows, int cols) {
        Random xGen = new Random();
        Random yGen = new Random();

        if (rows < 10 && cols < 10) {
            System.out.println("Cannot create a mine field with that many rows and columns");
            System.exit(0);
        } else {
            doesHaveMineArray = new boolean[rows][cols];
            gameAreaArray = new String[rows][cols];

            // Initialise 2d array
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    gameAreaArray[i][j] = "  ";
                    doesHaveMineArray[i][j] = false;
                }
            }
        }

        this.totalMines = (int) Math.ceil(rows * cols * 0.1); // Total mines is 10% of the entire game space
        int oldRow = 0;
        int oldCol = 0;

        // Put mines in random places
        for (int i = 0; i < this.totalMines; i++) {
            int row = xGen.nextInt(rows);
            int col = yGen.nextInt(cols);

            if (i > 0) {
                if (row == oldRow && col == oldCol) {
                    i--;
                    continue;
                }
            }

            oldRow = row;
            oldCol = col;
            doesHaveMineArray[row][col] = true;
        }
    }

    public static void main(String[] args) throws Exception {
        Minesweeper game = null;

        switch (args.length) {
            case 2:

                int rows, cols;

                try {
                    rows = Integer.parseInt(args[0]);
                    cols = Integer.parseInt(args[1]);
                    game = new Minesweeper(rows, cols);
                } catch (Exception e) {
                    System.out.println(e);
                }
            default:
                System.out.println("Usage: java Minesweeper [ROWS] [COLS]");
        }

        // Start the game
        game.run();
    }

    public void run() {
        intro();
        while (true) {
            newTurn();

            if (hasWon()) {
                break;
            }
        }
    }

    private void intro() {
        System.out.println("        _");
        System.out.println("  /\\/\\ (_)_ __   ___  _____      _____  ___ _ __   ___ _ __");
        System.out.println(" /    \\| | '_ \\ / _ \\/ __\\ \\ /\\ / / _ \\/ _ \\ '_ \\ / _ \\ '__|");
        System.out.println("/ /\\/\\ \\ | | | |  __/\\__ \\\\ V  V /  __/  __/ |_) |  __/ |");
        System.out.println("\\/    \\/_|_| |_|\\___||___/ \\_/\\_/ \\___|\\___| .__/ \\___|_|");
        System.out.println("                                           |_|");
        System.out.println("");
    }

    private int score() {
        return gameAreaArray.length * doesHaveMineArray[0].length - totalMines - roundsCompleted;
    }

    private void printRoundsCompleted() {
        System.out.println(String.format("Number of rounds completed: %s", roundsCompleted));
        System.out.println("");
    }

    private void printMinesweeperGrid() {
        for (int i = 0; i < gameAreaArray.length; i++) {
            System.out.print(String.format("%s |", i));

            for (int j = 0; j < gameAreaArray[i].length; j++) {
                System.out.print(gameAreaArray[i][j]);

                if (j < gameAreaArray[i].length - 1) {
                    System.out.print("|");
                }
            }

            System.out.println("|");
        }

        System.out.print("   ");

        for (int i = 0; i < gameAreaArray[0].length; i++) {
            System.out.print(String.format("%s  ", i));
        }

        System.out.println("");
    }

    private void newTurn() {
        printRoundsCompleted();
        printMinesweeperGrid();
        command();
    }

    private boolean hasWon() {
        boolean allMinesRevealed = true;
        boolean allSquaresRevealed = true;

        for (int i = 0; i < gameAreaArray.length; i++) {
            for (int j = 0; j < gameAreaArray[0].length; j++) {
                if (doesHaveMineArray[i][j]) {
                    if (gameAreaArray[i][j] != " F ") {
                        allMinesRevealed = false;
                    }
                } else {
                    if (gameAreaArray[i][j] == " F " || gameAreaArray[i][j] == " ? " || gameAreaArray[i][j] == "  ") {
                        allMinesRevealed = false;
                    }
                }
            }
        }
        return allMinesRevealed && allSquaresRevealed;
    }

    private void command() {
        Scanner kb = new Scanner(System.in);
        String input = kb.nextLine().trim();

    }
}
