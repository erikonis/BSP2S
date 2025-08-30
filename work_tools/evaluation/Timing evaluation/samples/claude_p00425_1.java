import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int n = sc.nextInt();
            if (n == 0) break;
            
            // Initial dice state: top=1, north=5, east=3, south=2, west=4, bottom=6
            int top = 1, north = 5, east = 3, south = 2, west = 4, bottom = 6;
            int sum = top; // Start with initial top value
            
            for (int i = 0; i < n; i++) {
                String operation = sc.next();
                
                int temp;
                switch (operation) {
                    case "North":
                        temp = top;
                        top = south;
                        south = bottom;
                        bottom = north;
                        north = temp;
                        break;
                    case "South":
                        temp = top;
                        top = north;
                        north = bottom;
                        bottom = south;
                        south = temp;
                        break;
                    case "East":
                        temp = top;
                        top = west;
                        west = bottom;
                        bottom = east;
                        east = temp;
                        break;
                    case "West":
                        temp = top;
                        top = east;
                        east = bottom;
                        bottom = west;
                        west = temp;
                        break;
                    case "Right":
                        temp = north;
                        north = west;
                        west = south;
                        south = east;
                        east = temp;
                        break;
                    case "Left":
                        temp = north;
                        north = east;
                        east = south;
                        south = west;
                        west = temp;
                        break;
                }
                sum += top;
            }
            
            System.out.println(sum);
        }
        
        sc.close();
    }
}