import java.util.Scanner;

public class Main {
    // Format time as "hh:mm:ss"
    static String formatTime(int totalSeconds) {
        int h = totalSeconds / 3600;
        int m = (totalSeconds % 3600) / 60;
        int s = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final int TAPE_SECONDS = 120 * 60;
        while (true) {
            int t = sc.nextInt();
            int h = sc.nextInt();
            int s = sc.nextInt();
            if (t == -1 && h == -1 && s == -1) break;
            int usedSeconds = t * 3600 + h * 60 + s;
            int remainStd = TAPE_SECONDS - usedSeconds;
            if (remainStd < 0) remainStd = 0;
            int remain3x = remainStd * 3;
            System.out.println(formatTime(remainStd));
            System.out.println(formatTime(remain3x));
        }
    }
}