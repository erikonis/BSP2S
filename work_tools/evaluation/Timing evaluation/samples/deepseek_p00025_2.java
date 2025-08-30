import java.util.Scanner;

public class HitAndBlow {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNext()) {
            int[] a = new int[4];
            int[] b = new int[4];
            
            for (int i = 0; i < 4; i++) {
                a[i] = scanner.nextInt();
            }
            for (int i = 0; i < 4; i++) {
                b[i] = scanner.nextInt();
            }
            
            int hit = 0, blow = 0;
            for (int i = 0; i < 4; i++) {
                if (a[i] == b[i]) {
                    hit++;
                }
            }
            
            boolean[] aMarked = new boolean[4];
            boolean[] bMarked = new boolean[4];
            for (int i = 0; i < 4; i++) {
                if (a[i] == b[i]) {
                    aMarked[i] = true;
                    bMarked[i] = true;
                }
            }
            
            for (int i = 0; i < 4; i++) {
                if (!aMarked[i]) {
                    for (int j = 0; j < 4; j++) {
                        if (!bMarked[j] && a[i] == b[j]) {
                            blow++;
                            bMarked[j] = true;
                            break;
                        }
                    }
                }
            }
            
            System.out.println(hit + " " + blow);
        }
        scanner.close();
    }
}