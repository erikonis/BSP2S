import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        String S = br.readLine();
        String T = br.readLine();
        
        int[] sA = new int[S.length() + 1];
        int[] sB = new int[S.length() + 1];
        int[] tA = new int[T.length() + 1];
        int[] tB = new int[T.length() + 1];
        
        for (int i = 0; i < S.length(); i++) {
            sA[i + 1] = sA[i] + (S.charAt(i) == 'A' ? 1 : 0);
            sB[i + 1] = sB[i] + (S.charAt(i) == 'B' ? 1 : 0);
        }
        
        for (int i = 0; i < T.length(); i++) {
            tA[i + 1] = tA[i] + (T.charAt(i) == 'A' ? 1 : 0);
            tB[i + 1] = tB[i] + (T.charAt(i) == 'B' ? 1 : 0);
        }
        
        int q = Integer.parseInt(br.readLine());
        for (int i = 0; i < q; i++) {
            String[] parts = br.readLine().split(" ");
            int a = Integer.parseInt(parts[0]);
            int b = Integer.parseInt(parts[1]);
            int c = Integer.parseInt(parts[2]);
            int d = Integer.parseInt(parts[3]);
            
            int sCntA = sA[b] - sA[a - 1];
            int sCntB = sB[b] - sB[a - 1];
            int tCntA = tA[d] - tA[c - 1];
            int tCntB = tB[d] - tB[c - 1];
            
            int diffS = (sCntA - sCntB) % 3;
            int diffT = (tCntA - tCntB) % 3;
            
            if (diffS < 0) diffS += 3;
            if (diffT < 0) diffT += 3;
            
            if (diffS == diffT) {
                bw.write("YES\n");
            } else {
                bw.write("NO\n");
            }
        }
        
        bw.flush();
        bw.close();
    }
}