import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int h = sc.nextInt();
        int w = sc.nextInt();
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        for(int i = 0;i < n;i++){
            a.add(sc.nextInt());
            b.add(sc.nextInt());
        }

        int ans = 0;
        for(int i = 0;i < n;i++){
            if(a.get(i) >= h && b.get(i) >= w){
                ans++;
            }
        }
        System.out.println(ans);
    }
}
