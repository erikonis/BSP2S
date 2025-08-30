import java.io.*;
import java.util.*;

public class Main {
    public static PrintWriter out;
    public static FastScanner in;
    Map<Points,Boolean> map;
    int H,W;

    private void solve(){
        H = in.nextInt();
        W = in.nextInt();

        List<Points> list = new ArrayList<>();
        map = new HashMap<>();
        int N = in.nextInt();
        for(int i=0;i<N;i++){
            Points p = new Points(in.nextInt(),in.nextInt());
            list.add(p);
            map.put(p,true);
        }

        long[] cnt = new long[10];
        int[] dx = new int[]{-2,-2,-2,-1,-1,-1,0,0,0};
        int[] dy = new int[]{-2,-1,0,-2,-1,0,-2,-1,0};
        Map<Points,Boolean> done = new HashMap<>();
        for(Points p:list){
            int x = p.x, y =p.y;
            for(int i=0;i<9;i++){
                int nx = x+dx[i];
                int ny = y+dy[i];
                if((nx > 0 && nx<=H) && (ny >0 && ny<=W)){
                    Points p1 = new Points(nx,ny);
                    if(!done.containsKey(p1)){
                        int count = cntBlack(nx,ny);
                        if(count > 0){
                            //out.println(nx+" "+ny+ " "+count);
                            cnt[count]++;
                        }
                        done.put(p1,true);
                    }
                }
            }
        }

        long sum = 0L;
        for(int i=0;i<10;i++){
            sum+=cnt[i];
        }
        cnt[0] = ((long)(H-2)*(W-2))-sum;
        for(int i=0;i<10;i++){
            out.println(cnt[i]);
        }
    }

    private int cntBlack(int x,int y){
        int[] dx = new int[]{0,0,0,1,1,1,2,2,2};
        int[] dy = new int[]{0,1,2,0,1,2,0,1,2};
        int count = 0;
        for(int i=0;i<9;i++){
            int nx = x+dx[i];
            int ny = y+dy[i];

            if((nx > 0 && nx<=H) && (ny >0 && ny<=W)){
                if(map.containsKey(new Points(nx,ny))){
                    count++;
                }
            }else{
                count = -1;
                break;
            }
        }
        return count;
    }

    private class Points{
        int x;
        int y;

        Points(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Points points = (Points) o;

            if (x != points.x) return false;
            return y == points.y;

        }

        @Override
        public int hashCode() {
            int result = x;
            result = 37 * result + y;
            return result;
        }
    }
    private void runIO() {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        solve();
        out.close();
    }
    private static class FastScanner {
        BufferedReader bufferedReader;
        StringTokenizer stringTokenizer;

        private FastScanner() {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        }

        private String next() {
            if (stringTokenizer == null || !stringTokenizer.hasMoreElements()) {
                try {
                    stringTokenizer = new StringTokenizer(bufferedReader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return stringTokenizer.nextToken();
        }

        private int nextInt() {
            return Integer.parseInt(next());
        }

        private long nextLong() {
            return Long.parseLong(next());
        }

        private String nextLine() {
            String ret = "";
            try {
                ret = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ret;
        }
    }

    public static void main(String[] args) {
        new Main().runIO();
    }
}
