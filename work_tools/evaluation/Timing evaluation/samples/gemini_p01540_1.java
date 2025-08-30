import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    static class Treasure {
        int x, y;

        Treasure(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Query implements Comparable<Query> {
        int x, y1, y2, type, id;

        Query(int x, int y1, int y2, int type, int id) {
            this.x = x;
            this.y1 = y1;
            this.y2 = y2;
            this.type = type; // 0 for treasure, 1 for start of query, -1 for end of query
            this.id = id;     // For query rectangles
        }

        @Override
        public int compareTo(Query other) {
            if (this.x != other.x) {
                return Integer.compare(this.x, other.x);
            }
            return Integer.compare(this.type, other.type);
        }
    }

    static class SegmentTree {
        int[] tree;
        int minCoord, maxCoord;
        int offset;

        SegmentTree(List<Integer> yCoords) {
            Collections.sort(yCoords);
            // Remove duplicates
            List<Integer> uniqueYCoords = new ArrayList<>();
            if (!yCoords.isEmpty()) {
                uniqueYCoords.add(yCoords.get(0));
                for (int i = 1; i < yCoords.size(); i++) {
                    if (!yCoords.get(i).equals(yCoords.get(i - 1))) {
                        uniqueYCoords.add(yCoords.get(i));
                    }
                }
            }

            if (uniqueYCoords.isEmpty()) {
                minCoord = 0;
                maxCoord = 0;
            } else {
                minCoord = uniqueYCoords.get(0);
                maxCoord = uniqueYCoords.get(uniqueYCoords.size() - 1);
            }

            offset = -minCoord;
            int range = maxCoord - minCoord + 1;
            int size = 1;
            while (size < range) {
                size *= 2;
            }
            tree = new int[2 * size];
        }

        private int getIndex(int y) {
            return y + offset;
        }

        void update(int y, int value) {
            int idx = getIndex(y);
            if (idx < 0 || idx >= tree.length / 2) return; // Should not happen with correct coordinate mapping
            idx += tree.length / 2; // Leaf node index
            while (idx > 0) {
                tree[idx] += value;
                idx /= 2;
            }
        }

        int query(int y1, int y2) {
            int sum = 0;
            int left = getIndex(y1);
            int right = getIndex(y2);

            if (left < 0) left = 0; // Clamp to valid range
            if (right >= tree.length / 2) right = tree.length / 2 - 1; // Clamp to valid range

            if (left > right) return 0; // Invalid range

            left += tree.length / 2;
            right += tree.length / 2;

            while (left <= right) {
                if (left % 2 == 1) {
                    sum += tree[left];
                    left++;
                }
                if (right % 2 == 0) {
                    sum += tree[right];
                    right--;
                }
                left /= 2;
                right /= 2;
            }
            return sum;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nm = br.readLine().split(" ");
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);

        List<Treasure> treasures = new ArrayList<>();
        List<Integer> allYCoords = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String[] xy = br.readLine().split(" ");
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            treasures.add(new Treasure(x, y));
            allYCoords.add(y);
        }

        List<Query> events = new ArrayList<>();
        for (Treasure t : treasures) {
            events.add(new Query(t.x, t.y, t.y, 0, -1)); // Type 0 for treasure
        }

        int[] results = new int[m];
        for (int i = 0; i < m; i++) {
            String[] rect = br.readLine().split(" ");
            int x1 = Integer.parseInt(rect[0]);
            int y1 = Integer.parseInt(rect[1]);
            int x2 = Integer.parseInt(rect[2]);
            int y2 = Integer.parseInt(rect[3]);

            events.add(new Query(x1, y1, y2, 1, i));  // Type 1 for start of query rectangle
            events.add(new Query(x2 + 1, y1, y2, -1, i)); // Type -1 for end of query rectangle (x2+1 exclusive)
            allYCoords.add(y1);
            allYCoords.add(y2);
        }

        Collections.sort(events);

        SegmentTree segTree = new SegmentTree(allYCoords);

        for (Query event : events) {
            if (event.type == 0) { // Treasure
                segTree.update(event.y1, 1);
            } else if (event.type == 1) { // Start of query rectangle
                results[event.id] -= segTree.query(event.y1, event.y2);
            } else { // End of query rectangle
                results[event.id] += segTree.query(event.y1, event.y2);
            }
        }

        for (int i = 0; i < m; i++) {
            System.out.println(results[i]);
        }
    }
}