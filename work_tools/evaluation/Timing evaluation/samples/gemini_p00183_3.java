import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            String[] board = new String[3];
            board[0] = sc.next();
            if (board[0].equals("0")) {
                break;
            }
            board[1] = sc.next();
            board[2] = sc.next();

            char winner = checkWinner(board);
            System.out.println(winner);
        }

        sc.close();
    }

    private static char checkWinner(String[] board) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i].charAt(0) == board[i].charAt(1) && board[i].charAt(1) == board[i].charAt(2) && board[i].charAt(0) != '+') {
                return board[i].charAt(0);
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0].charAt(j) == board[1].charAt(j) && board[1].charAt(j) == board[2].charAt(j) && board[0].charAt(j) != '+') {
                return board[0].charAt(j);
            }
        }

        // Check diagonals
        if (board[0].charAt(0) == board[1].charAt(1) && board[1].charAt(1) == board[2].charAt(2) && board[0].charAt(0) != '+') {
            return board[0].charAt(0);
        }
        if (board[0].charAt(2) == board[1].charAt(1) && board[1].charAt(1) == board[2].charAt(0) && board[0].charAt(2) != '+') {
            return board[0].charAt(2);
        }

        return 'N'; // No winner, represented as 'NA' in output
    }
}