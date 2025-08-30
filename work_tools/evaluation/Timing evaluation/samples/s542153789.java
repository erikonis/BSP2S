import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final int iBoundNum = Integer.parseInt(sc.next());
        final int iCoordinate = Integer.parseInt(sc.next());

        int iBoundCnt = 1;
        int iBoundTotal = 0;
        for (int i = 0; i < iBoundNum; i++) {
            int iBoundLength = Integer.parseInt(sc.next());
            iBoundTotal += iBoundLength;
            if (iBoundTotal > iCoordinate) {
                break;
            }
            iBoundCnt++;
        }
        System.out.print(iBoundCnt);
    }
}
