import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        int q = sc.nextInt();
        long[] d = new long[k];
        for (int i = 0; i < k; i++) {
            d[i] = sc.nextLong();
        }

        StringBuilder output = new StringBuilder();
        for (int query = 0; query < q; query++) {
            long n = sc.nextLong();
            long x = sc.nextLong();
            long m = sc.nextLong();

            long[] modD = new long[k];
            long sumZero = 0;
            long sumMod = 0;
            for (int i = 0; i < k; i++) {
                modD[i] = d[i] % m;
                sumMod += modD[i];
                if (modD[i] == 0) {
                    sumZero++;
                }
            }

            long fullCycles = (n - 1) / k;
            long remainder = (n - 1) % k;

            long totalZero = sumZero * fullCycles;
            long totalSum = sumMod * fullCycles;
            for (int i = 0; i < remainder; i++) {
                totalSum += modD[i];
                if (modD[i] == 0) {
                    totalZero++;
                }
            }

            long a0Mod = x % m;
            long lastMod = (a0Mod + totalSum) % m;
            long count = (n - 1) - totalZero - (lastMod < (a0Mod + totalSum) / m ? 1 : 0);
            output.append(count).append("\n");
        }
        System.out.print(output);
    }
}