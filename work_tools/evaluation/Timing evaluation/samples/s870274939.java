import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder bd = new StringBuilder();

        int N = sc.nextInt();
        String[] C = new String[N];
        String[] D = new String[N];
        String[][] I = new String[9][4];

        for (int i=0; i<N; i++) {
            C[i] = sc.next();
            D[i] = C[i];
            int j=0;
            while (I[Integer.valueOf(C[i].substring(1, 2))-1][j] != null) {
                j++;
            }
            I[Integer.valueOf(C[i].substring(1, 2))-1][j] = C[i].substring(0, 1);
        }

        BubbleSort(C, N);

        for (int i=0; i<N-1; i++) {
            System.out.print(C[i] + " ");
        }
        System.out.println(C[N-1]);

        boolean flag = true;

        for (int i=0; i<N-1; i++) {
            if (C[i].substring(1, 2).equals(C[i+1].substring(1, 2))) {
                int r0 = 0;
                int r1 = 0;
                int j=0;
                while (j != 4 && I[Integer.valueOf(C[i].substring(1, 2))-1][j] != null) {
                    if (I[Integer.valueOf(C[i].substring(1, 2))-1][j].equals(C[i].substring(0,1)))
                        r0 = j;
                    else if (I[Integer.valueOf(C[i+1].substring(1, 2))-1][j].equals(C[i+1].substring(0,1)))
                        r1 = j;
                    j++;
                }
                if (r0 >r1) {
                    flag = false;
                    break;
                }
            }
        }
        if (flag == true) System.out.println("Stable");
        else System.out.println("Not stable");

        SelectionSort(D, N);

        for (int i=0; i<N-1; i++) {
            System.out.print(D[i] + " ");
        }
        System.out.println(D[N-1]);

        flag = true;

        for (int i=0; i<N-1; i++) {
            if (D[i].substring(1, 2).equals(D[i+1].substring(1, 2))) {
                int r0 = 0;
                int r1 = 0;
                int j=0;
                while (j != 4 && I[Integer.valueOf(D[i].substring(1, 2))-1][j] != null) {
                    if (I[Integer.valueOf(D[i].substring(1, 2))-1][j].equals(D[i].substring(0,1)))
                        r0 = j;
                    else if (I[Integer.valueOf(D[i+1].substring(1, 2))-1][j].equals(D[i+1].substring(0,1)))
                        r1 = j;
                    j++;
                }
                if (r0 >r1) {
                    flag = false;
                    break;
                }
            }
        }
        if (flag == true) System.out.println("Stable");
        else System.out.println("Not stable");

    }

    public static void BubbleSort(String[] C, int N) {
        for (int i=0; i<N; i++) {
            for (int j=N-1; j>i; j--) {
                if (C[j].substring(1, 2).compareTo(C[j-1].substring(1, 2)) < 0) {
                    String t = C[j];
                    C[j] = C[j-1];
                    C[j-1] = t;
                }
            }
        }
    }

    public static void SelectionSort(String[] C, int N) {
        for (int i=0; i<N; i++) {
            int minj = i;
            for (int j=i; j<N; j++) {
                if (C[j].substring(1, 2).compareTo(C[minj].substring(1, 2)) < 0) {
                    minj = j;
                }
            }
            if (i != minj) {
                String t = C[i];
                C[i] = C[minj];
                C[minj] = t;
            }
        }
    }
}
