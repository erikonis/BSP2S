import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        MyScanner sc = new MyScanner();
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        int n = sc.nextInt();
        Node sentinel = new Node(' ');
        Node [] a = new Node[n];
        Set<Node> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            String s = sc.next();
            Node cur = sentinel;
            for (int j = s.length() - 1; j >= 0; j--) {
                boolean found = false;
                for (Node node: cur.children) {
                    if (s.charAt(j) == node.c) {
                        cur = node;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Node add = new Node(s.charAt(j));
                    add.parent = cur;
                    cur.children.add(add);
                    cur = add;
                }
            }
            a[i] = cur;
            set.add(cur);
        }
        long ans = 0;
        for (int i = 0; i < n; i++) {
            Node cur = a[i]; Set<Character> seen = new HashSet<>();
            while (cur != null) {
                for (Node child: cur.children) {
                    if (seen.contains(child.c) && set.contains(child) && child != a[i]) {
                        ans++;
                    }
                }
                seen.add(cur.c);
                cur = cur.parent;
            }
        }
        out.println(ans);
        out.close();
    }

    static class Node {
        char c; Node parent;
        ArrayList<Node> children;
        Node(char c) {
            this.c = c;
            children = new ArrayList<>();
        }
    }


    //-----------MyScanner class for faster input----------
    public static class MyScanner {
        BufferedReader br;
        StringTokenizer st;

        public MyScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }


    }

}