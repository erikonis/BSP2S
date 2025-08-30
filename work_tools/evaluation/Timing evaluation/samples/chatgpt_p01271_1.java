import java.util.*;
public class Main {
    static int W, H;
    static char[][] left, right;
    static int[] dr = {-1, 1, 0, 0}; // N, S, W, E
    static int[] dc = {0, 0, -1, 1};

    static class State {
        int lx, ly, rx, ry;
        State(int lx, int ly, int rx, int ry) {
            this.lx = lx; this.ly = ly; this.rx = rx; this.ry = ry;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true){
            W = sc.nextInt();
            H = sc.nextInt();
            if(W==0 && H==0) break;
            left = new char[H][W];
            right = new char[H][W];
            int lx = -1, ly = -1, rx = -1, ry = -1;
            int gx = -1, gy = -1, gx2 = -1, gy2 = -1;
            for(int i=0;i<H;i++){
                String l = sc.next();
                String r = sc.next();
                for(int j=0;j<W;j++){
                    left[i][j] = l.charAt(j);
                    right[i][j] = r.charAt(j);
                    if(left[i][j]=='L'){ lx=i; ly=j; }
                    if(right[i][j]=='R'){ rx=i; ry=j; }
                    if(left[i][j]=='%'){ gx=i; gy=j; }
                    if(right[i][j]=='%'){ gx2=i; gy2=j; }
                }
            }
            System.out.println(bfs(lx,ly,rx,ry,gx,gy,gx2,gy2) ? "Yes" : "No");
        }
    }

    static boolean bfs(int lx, int ly, int rx, int ry, int gx, int gy, int gx2, int gy2){
        boolean[][][][] vis = new boolean[H][W][H][W];
        Queue<State> q = new LinkedList<>();
        q.add(new State(lx,ly,rx,ry));
        vis[lx][ly][rx][ry]=true;
        while(!q.isEmpty()){
            State s = q.poll();
            if(s.lx==gx && s.ly==gy && s.rx==gx2 && s.ry==gy2) return true;
            for(int d=0;d<4;d++){
                int nlx = s.lx + dr[d];
                int nly = s.ly + dc[d];
                int nd = symDir(d);
                int nrx = s.rx + dr[nd];
                int nry = s.ry + dc[nd];

                boolean lCan = inRoom(nlx,nly) && left[nlx][nly]!='#';
                boolean rCan = inRoom(nrx,nry) && right[nrx][nry]!='#';

                int flx = s.lx, fly = s.ly, frx = s.rx, fry = s.ry;
                if(lCan) { flx = nlx; fly = nly; }
                if(rCan) { frx = nrx; fry = nry; }

                if(!vis[flx][fly][frx][fry]){
                    vis[flx][fly][frx][fry]=true;
                    q.add(new State(flx,fly,frx,fry));
                }
            }
        }
        return false;
    }

    static int symDir(int d){
        // N->N (0), S->S (1), W->E (2->3), E->W (3->2)
        if(d==2) return 3;
        if(d==3) return 2;
        return d;
    }

    static boolean inRoom(int x, int y){
        return 0<=x && x<H && 0<=y && y<W;
    }
}