import java.util.*;;

class Main{
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);

        int A = sc.nextInt();
        int B = sc.nextInt();
        int C = sc.nextInt();
        int X = sc.nextInt();
        int Y = sc.nextInt();
        int ans = A * X + B * Y;

        for(int i=1; i<=Math.max(X, Y); i++){
            int sum = C * 2 * i + Math.max(0, X - i) * A + Math.max(0, Y - i) * B;
            ans = Math.min(ans, sum);
        }
        System.out.println(ans);
    }
}