import java.io.PrintWriter;
import java.util.*;

public class Main {
    static PrintWriter out = new PrintWriter(System.out);
    final long mod = 1000000000+7;
    long[] fac, inv;

    public static void main(String[] args) {
        Main main = new Main();
//        main.solve();
//        out.flush();
        System.out.println(main.solve());
    }
    // segment tree to record minimum value
    // Each point has a infinite distance from 0 (except itself)
    long solve() {
        Scanner sc = new Scanner(System.in);
        int r1 = sc.nextInt(), c1 = sc.nextInt(), r2 =sc.nextInt(), c2 = sc.nextInt();
        initial(r2+c2+5);
        long ans = helper(r2,c2);
        ans -= helper(r2, c1-1);
        ans -= helper(r1-1,c2);
        ans += helper(r1-1,c1-1);
        ans = (ans+10*mod)%mod;
        return ans;
    }
    long helper(int r, int c){
        if(r==0) return c+1;
        if(c==0) return r+1;
        if(r>c){
            int mid = r;
            r = c; c = mid;
        }
        // let r <= c
        long ans = 0;
        for(int row = r; row >=0; row--){
            long cur = fac[row+c+1]*inv[c]%mod*inv[row+1]%mod;
            ans = (ans+cur)%mod;
        }
        return ans;
    }
    long power(long base, long p){
        long ans = 1;
        while(p>0){
            if(p%2==1) ans = ans*base%mod;
            base = base*base%mod;
            p /= 2;
        }
        return ans;
    }
    void initial(int maxN){
        fac = new long[maxN+1];
        inv = new long[maxN+1];
        fac[0] = 1;
        for(int i=1;i<=maxN; i++) fac[i] = i*fac[i-1]%mod;
        inv[maxN] = power(fac[maxN], mod-2);
        inv[0] = 1;
        for(int i=maxN-1;i>0;i--){
            inv[i] = inv[i+1]*(i+1)%mod;
        }
    }
    // compute nCm

}

