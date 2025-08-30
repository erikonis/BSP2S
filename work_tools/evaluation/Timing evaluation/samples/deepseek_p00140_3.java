import java.util.Scanner;

public class BusRoute {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        
        for (int i = 0; i < n; i++) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            
            StringBuilder path = new StringBuilder();
            
            if (start == end) {
                System.out.println(start);
                continue;
            }
            
            int clockwiseDistance = getClockwiseDistance(start, end);
            int counterClockwiseDistance = getCounterClockwiseDistance(start, end);
            
            if (clockwiseDistance <= counterClockwiseDistance) {
                // Clockwise
                int current = start;
                while (current != end) {
                    path.append(current).append(" ");
                    current = (current + 1) % 10;
                    if (current == 5 && end != 5) {
                        current = 5;
                        break;
                    }
                }
                if (current == 5 && end != 5) {
                    // Handle loop
                    path.append(current).append(" ");
                    current = 6;
                    while (current != end) {
                        path.append(current).append(" ");
                        current = (current + 1);
                        if (current > 9) current = 5;
                    }
                }
                path.append(end);
            } else {
                // Counter-clockwise
                int current = start;
                while (current != end) {
                    path.append(current).append(" ");
                    current = (current - 1 + 10) % 10;
                    if (current == 5 && end != 5) {
                        current = 9;
                        path.append(5).append(" ");
                    }
                }
                path.append(end);
            }
            
            System.out.println(path.toString().trim());
        }
    }
    
    private static int getClockwiseDistance(int start, int end) {
        if (start <= end && !(start <= 5 && end > 5)) {
            return end - start;
        } else {
            if (start <= 5) {
                return (5 - start) + 1 + (end <= 5 ? 0 : (end - 5));
            } else {
                if (end > start) {
                    return end - start;
                } else {
                    return (10 - start) + end;
                }
            }
        }
    }
    
    private static int getCounterClockwiseDistance(int start, int end) {
        if (start >= end && !(start >= 5 && end < 5)) {
            return start - end;
        } else {
            if (start >= 5) {
                return (start - 5) + 1 + (end >= 5 ? 0 : (5 - end));
            } else {
                if (end < start) {
                    return start - end;
                } else {
                    return start + (10 - end);
                }
            }
        }
    }
}