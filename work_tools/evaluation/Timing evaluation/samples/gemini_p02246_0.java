import java.util.*;

public class Main {
    static final int N = 4;
    static final int[][] GOAL = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };
    static final int[] DR = {-1, 1, 0, 0};
    static final int[] DC = {0, 0, -1, 1};

    static class State {
        int[][] board;
        int zeroR, zeroC;
        int cost;

        public State(int[][] board, int zeroR, int zeroC, int cost) {
            this.board = new int[N][N];
            for (int i = 0; i < N; i++) {
                System.arraycopy(board[i], 0, this.board[i], 0, N);
            }
            this.zeroR = zeroR;
            this.zeroC = zeroC;
            this.cost = cost;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return Arrays.deepEquals(board, state.board);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(board);
        }
    }

    public static int manhattanDistance(int[][] board) {
        int dist = 0;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int val = board[r][c];
                if (val == 0) continue;
                int targetR = (val - 1) / N;
                int targetC = (val - 1) % N;
                dist += Math.abs(r - targetR) + Math.abs(c - targetC);
            }
        }
        return dist;
    }

    public static int solve(int[][] initialBoard) {
        int initialZeroR = -1;
        int initialZeroC = -1;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (initialBoard[r][c] == 0) {
                    initialZeroR = r;
                    initialZeroC = c;
                    break;
                }
            }
            if (initialZeroR != -1) break;
        }

        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.cost + manhattanDistance(s.board)));
        Set<State> visited = new HashSet<>();

        State initialState = new State(initialBoard, initialZeroR, initialZeroC, 0);
        pq.add(initialState);
        visited.add(initialState);

        while (!pq.isEmpty()) {
            State current = pq.poll();

            if (Arrays.deepEquals(current.board, GOAL)) {
                return current.cost;
            }

            for (int i = 0; i < 4; i++) {
                int newZeroR = current.zeroR + DR[i];
                int newZeroC = current.zeroC + DC[i];

                if (newZeroR >= 0 && newZeroR < N && newZeroC >= 0 && newZeroC < N) {
                    int[][] newBoard = new int[N][N];
                    for (int r = 0; r < N; r++) {
                        System.arraycopy(current.board[r], 0, newBoard[r], 0, N);
                    }

                    // Swap
                    newBoard[current.zeroR][current.zeroC] = newBoard[newZeroR][newZeroC];
                    newBoard[newZeroR][newZeroC] = 0;

                    State newState = new State(newBoard, newZeroR, newZeroC, current.cost + 1);

                    if (!visited.contains(newState)) {
                        visited.add(newState);
                        pq.add(newState);
                    }
                }
            }
        }
        return -1; // Should not happen for solvable puzzles
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] initialBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                initialBoard[i][j] = sc.nextInt();
            }
        }
        System.out.println(solve(initialBoard));
        sc.close();
    }
}