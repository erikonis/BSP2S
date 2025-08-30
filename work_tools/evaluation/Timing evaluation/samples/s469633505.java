import java.util.Scanner;

class Main {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt()-1;
        int b = sc.nextInt()-1;
        int[] f ={
            1,3,1,2,1,2,1,1,2,1,2,1
        };
        System.out.println(f[a]==f[b]?"Yes":"No");
    }
}
