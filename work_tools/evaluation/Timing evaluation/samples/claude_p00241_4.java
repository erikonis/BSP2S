import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int n = sc.nextInt();
            if (n == 0) break;
            
            for (int i = 0; i < n; i++) {
                int x1 = sc.nextInt();
                int y1 = sc.nextInt();
                int z1 = sc.nextInt();
                int w1 = sc.nextInt();
                int x2 = sc.nextInt();
                int y2 = sc.nextInt();
                int z2 = sc.nextInt();
                int w2 = sc.nextInt();
                
                // Quaternion multiplication: (x1 + y1*i + z1*j + w1*k) * (x2 + y2*i + z2*j + w2*k)
                // Using multiplication table: 1*1=1, i*i=-1, j*j=-1, k*k=-1
                // i*j=k, j*i=-k, j*k=i, k*j=-i, k*i=j, i*k=-j
                
                int x3 = x1*x2 - y1*y2 - z1*z2 - w1*w2;
                int y3 = x1*y2 + y1*x2 + z1*w2 - w1*z2;
                int z3 = x1*z2 - y1*w2 + z1*x2 + w1*y2;
                int w3 = x1*w2 + y1*z2 - z1*y2 + w1*x2;
                
                System.out.println(x3 + " " + y3 + " " + z3 + " " + w3);
            }
        }
        
        sc.close();
    }
}