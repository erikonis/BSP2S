import java.util.*;

public class Main {

    static long T1;
    static long T2;
    static long A1;
    static long A2;
    static long B1;
    static long B2;
    static void swap(long a, long b) {
        long tmp = a;
        a = b;
        b = tmp;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        T1 = Long.parseLong(sc.next());
        T2 = Long.parseLong(sc.next());
        A1 = Long.parseLong(sc.next());
        A2 = Long.parseLong(sc.next());
        B1 = Long.parseLong(sc.next());
        B2 = Long.parseLong(sc.next());

        long ans = 0;
        long diff = 0;
        if ((A1*T1+A2*T2)-(B1*T1+B2*T2)==0) {
            if (A1==B1) System.out.println(0);
            else System.out.println("infinity");
            return;
        }
        if ((A1*T1+A2*T2)-(B1*T1+B2*T2)<0) {
            long tmp_aa = A1;
            A1 = B1;
            B1 = tmp_aa;

            long tmp_bb = A2;
            A2 = B2;
            B2 = tmp_bb;
            // swap(A1, B1);
            // swap(A2, B2);
        }
        // if ((A1*T1+A2*T2)-(B1*T1+B2*T2)>0) {
            ans = 0L;
            diff = (A1*T1+A2*T2)-(B1*T1+B2*T2);

            if (A1>=B1) {
                ans = 0;
            } else {
                ans = ((B1*T1)-(A1*T1))/diff*2+1;
                if (((B1*T1)-(A1*T1))%diff==0) ans--;
                // System.out.println(((A1*T1+A2*T2)-(B1*T1))/diff*2);
                // ans = Math.min(((A1*T1+A2*T2)-(B1*T1))/diff*2+1, ans);
            }
            // System.out.println("A1, A2 "+A1+" "+A2);
        // }
        System.out.println(ans);
        // System.out.println(diff);
    }
}