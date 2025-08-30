import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        int q = scanner.nextInt();
        int[] d = new int[k];
        for (int i = 0; i < k; i++) {
            d[i] = scanner.nextInt();
        }

        while (q-- > 0) {
            long n = scanner.nextLong();
            long x = scanner.nextLong();
            long m = scanner.nextLong();

            long[] modD = new long[k];
            long zeroCount = 0;
            long sumMod = 0;

            for (int i = 0; i < k; i++) {
                modD[i] = d[i] % m;
                if (modD[i] == 0) zeroCount++;
                sumMod += modD[i];
            }

            long fullCycles = (n - 1) / k;
            int rem = (int)((n - 1) % k);

            long aMod = x % m;
            long totalZero = fullCycles * zeroCount;
            long totalSum = fullCycles * sumMod;

            for (int i = 0; i < rem; i++) {
                if (modD[i] == 0) totalZero++;
                totalSum += modD[i];
            }

            long lastMod = (aMod + totalSum) % m;
            long res = (n - 1) - (totalZero + (aMod + totalSum) / m - x / m);
            System.out.println(res);
        }
    }
}