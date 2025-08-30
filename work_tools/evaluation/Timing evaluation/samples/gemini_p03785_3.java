import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long C = sc.nextLong();
        long K = sc.nextLong();
        long[] T = new long[N];
        for (int i = 0; i < N; i++) {
            T[i] = sc.nextLong();
        }
        sc.close();

        Arrays.sort(T);

        int buses = 0;
        int i = 0;
        while (i < N) {
            buses++;
            long departureTimeWindowEnd = T[i] + K;
            int passengersOnBus = 0;
            while (i < N && T[i] <= departureTimeWindowEnd && passengersOnBus < C) {
                passengersOnBus++;
                i++;
            }
        }
        System.out.println(buses);
    }
}