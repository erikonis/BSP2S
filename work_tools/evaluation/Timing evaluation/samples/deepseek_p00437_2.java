import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();
            if (a == 0 && b == 0 && c == 0) break;
            
            int n = sc.nextInt();
            List<int[]> tests = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                int z = sc.nextInt();
                int r = sc.nextInt();
                tests.add(new int[]{x, y, z, r});
            }
            
            int totalParts = a + b + c;
            int[] status = new int[totalParts + 1]; // 0: unknown, 1: good, -1: bad
            boolean changed;
            
            // First pass: mark definitely good parts from passing tests
            do {
                changed = false;
                for (int[] test : tests) {
                    if (test[3] == 1) { // pass means all parts are good
                        int x = test[0];
                        int y = test[1];
                        int z = test[2];
                        if (status[x] != 1) {
                            status[x] = 1;
                            changed = true;
                        }
                        if (status[y] != 1) {
                            status[y] = 1;
                            changed = true;
                        }
                        if (status[z] != 1) {
                            status[z] = 1;
                            changed = true;
                        }
                    }
                }
            } while (changed);
            
            // Second pass: mark definitely bad parts from failing tests
            for (int[] test : tests) {
                if (test[3] == 0) { // fail means at least one is bad
                    int x = test[0];
                    int y = test[1];
                    int z = test[2];
                    // If two are known good, the third must be bad
                    int goodCount = 0;
                    int unknownPart = 0;
                    if (status[x] == 1) goodCount++;
                    else if (status[x] == 0) unknownPart = x;
                    if (status[y] == 1) goodCount++;
                    else if (status[y] == 0) unknownPart = y;
                    if (status[z] == 1) goodCount++;
                    else if (status[z] == 0) unknownPart = z;
                    
                    if (goodCount == 2 && unknownPart != 0) {
                        status[unknownPart] = -1;
                    }
                }
            }
            
            // Output results
            for (int i = 1; i <= totalParts; i++) {
                if (status[i] == 1) {
                    System.out.println(1);
                } else if (status[i] == -1) {
                    System.out.println(0);
                } else {
                    System.out.println(2);
                }
            }
        }
        sc.close();
    }
}