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
                System.out.println("Type 'help' for information");
        }

        // Start the game
        game.run();
    }

    public void run() {
        intro();
        while (true) {
            nextTurn();

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
        System.out.println(String.format("Round Number: %s", roundsCompleted));
        System.out.println("");
    }

    private void printMinesweeperGrid() {
        for (int i = 0; i < gameAreaArray.length; i++) {
            System.out.print(String.format("%s | ", i));

            for (int j = 0; j < gameAreaArray[i].length; j++) {
                System.out.print(gameAreaArray[i][j]);

                if (j < gameAreaArray[i].length - 1) {
                    System.out.print("| ");
                }
            }

            System.out.println("|");
        }

        System.out.print("    ");

        for (int i = 0; i < gameAreaArray[0].length; i++) {
            System.out.print(String.format("%s   ", i));
        }

        System.out.println("");
    }

    private void nextTurn() {
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

    private void help() {
        System.out.println("");
        System.out.println("Command List: ");
        System.out.println("help: help");
        System.out.println("Quit: quit");
        System.out.println("Guess: guess [ROW] [COL]");
        System.out.println("");
    }

    // Return true if the input was in bounds, otherwise, return false
    private boolean isInBounds(int row, int col) {
        return (row >= 0 && row < doesHaveMineArray.length && col >= 0 && col < doesHaveMineArray[0].length);
    }

    private boolean guess(Scanner keyboard) {
        boolean wasGuessSuccessful = false;
        int guessedRow = 0;
        int guessedCol = 0;

        // If the first input was an integer
        if (keyboard.hasNextInt()) {
            guessedRow = keyboard.nextInt();

            // If the second input was an integer
            if (keyboard.hasNextInt()) {
                guessedCol = keyboard.nextInt();

                // Guess was successful if it was in bounds and was a an integer input
                if (!keyboard.hasNextInt() && isInBounds(guessedRow, guessedCol)) {
                    wasGuessSuccessful = true;
                    roundsCompleted++;
                    gameAreaArray[guessedRow][guessedCol] = "? ";
                    System.out.println("");
                }
            }
        }

        return wasGuessSuccessful;
    }

    private void invalidCommand() {
        System.out.println("Command not recognised, try typing 'help'.");
    }

    private void quit() {
        System.exit(0);
    }

    private void command() {
        // Create a new scanner which points to the input stream
        Scanner kb = new Scanner(System.in);
        String input = kb.nextLine().trim();

        // Put stream into input
        Scanner epic = new Scanner(input);
        String command = epic.next().trim();

        switch (command) {
            case "help":
                help();

                break;
            case "guess":
                if (!guess(epic)) {
                    System.out.println("Round completed");
                }
                break;
            case "quit":
                quit();
                break;
            default:
                invalidCommand();
        }
    }
}
