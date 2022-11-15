import java.util.Random;

import javax.xml.catalog.Catalog;

public class Minesweeper {
    private String[][] gameAreaArray;
    private boolean[][] doesHaveMineArray;
    private int roundsCompleted = 0;
    private int totalMines = 0;

    public Minesweeper(int rows, int cols) {
        Random xGen = new Random();
        Random yGen = new Random();

        if (rows <= 10 && cols <= 10) {
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

        game.run();
    }

    public void run() {
        // TODO: perform game loop
    }
}
