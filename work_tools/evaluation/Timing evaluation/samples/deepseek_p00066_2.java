import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String board = scanner.nextLine();
            char result = checkWinner(board);
            System.out.println(result);
        }
    }

    private static char checkWinner(String board) {
        char[][] grid = new char[3][3];
        for (int i = 0; i < 9; i++) {
            grid[i / 3][i % 3] = board.charAt(i);
        }

        // Check rows, columns, and diagonals for winner
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (grid[i][0] != 's' && grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2]) {
                return grid[i][0];
            }
            // Check columns
            if (grid[0][i] != 's' && grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i]) {
                return grid[0][i];
            }
        }
        // Check diagonals
        if (grid[0][0] != 's' && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]) {
            return grid[0][0];
        }
        if (grid[0][2] != 's' && grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0]) {
            return grid[0][2];
        }
        return 'd'; // Draw if no winner
    }
}