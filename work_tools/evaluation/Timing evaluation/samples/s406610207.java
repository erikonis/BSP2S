import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        //緑のカードに書かれている整数は、赤のカードに書かれている整数より真に大きい。
        //青のカードに書かれている整数は、緑のカードに書かれている整数より真に大きい。
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt(); //赤
        int B = sc.nextInt(); //緑
        int C = sc.nextInt(); //青
        int K = sc.nextInt();
        boolean flag = false;
        for(int i = 0; i < K; i ++){
                if (B <= A){
                    B = B * 2;
                } else if (C <= B){
                    C = C * 2;
                }
                if(A < B && B < C){
                    flag = true;
                    break;
                }
        }
        if (flag) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}