import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] t = new int[N];
        int[] v = new int[N];
        
        for (int i = 0; i < N; i++) t[i] = sc.nextInt();
        for (int i = 0; i < N; i++) v[i] = sc.nextInt();
        
        int totalTime = 0;
        for (int time : t) totalTime += time;
        
        double[] maxSpeed = new double[totalTime * 2 + 1];
        Arrays.fill(maxSpeed, Double.MAX_VALUE);
        maxSpeed[0] = 0;
        maxSpeed[maxSpeed.length - 1] = 0;
        
        int currentTime = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < t[i] * 2; j++) {
                maxSpeed[currentTime + j + 1] = Math.min(maxSpeed[currentTime + j + 1], v[i]);
            }
            currentTime += t[i] * 2;
        }
        
        for (int i = 1; i < maxSpeed.length; i++) {
            maxSpeed[i] = Math.min(maxSpeed[i], maxSpeed[i - 1] + 0.5);
        }
        
        for (int i = maxSpeed.length - 2; i >= 0; i--) {
            maxSpeed[i] = Math.min(maxSpeed[i], maxSpeed[i + 1] + 0.5);
        }
        
        double distance = 0.0;
        for (int i = 0; i < maxSpeed.length - 1; i++) {
            distance += (maxSpeed[i] + maxSpeed[i + 1]) * 0.5 / 2.0;
        }
        
        System.out.printf("%.15f\n", distance);
    }
}