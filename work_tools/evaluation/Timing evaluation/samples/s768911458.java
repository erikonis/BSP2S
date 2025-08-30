//package com.company;

import java.io.*;
import java.util.*;

public class Main {
    public static class Task {
        int mod = 1_000_000_007;
        int[] factorial;
        int[] invfactorial;
        int add(int a, int b) {
            int c = a +b;
            if (c >= mod) return c - mod;
            return c;
        }
        int mult (int a, int b) {
            return (int)((long) a * b % mod);
        }
        int pow(int a, int b) {
            int r = 1;
            while (b != 0) {
                if (b % 2 == 1) r = mult(r, a);
                a = mult(a, a);
                b >>= 1;
            }
            return r;
        }

        int cnk(int n, int k) {
            if (n < 0 || k > n || k < 0) return 0;
            return mult(mult(factorial[n], invfactorial[k]), invfactorial[n - k]);
        }

        public void solve(Scanner sc, PrintWriter pw) throws IOException {
            int N = 200000;
            factorial = new int[N];
            invfactorial = new int[N];
            factorial[0] = 1;
            for (int i = 1; i < N; i++) {
                factorial[i] = mult(factorial[i - 1], i);
            }
            for (int i = 0; i < N; i++) {
                invfactorial[i] = pow(factorial[i], mod - 2);
            }
            int r = 0;
            int H = sc.nextInt(), W = sc.nextInt(), A = sc.nextInt(), B = sc.nextInt();
            for (int i = B; i <= W - 1; i++) {
                r = add(r, mult(cnk(H - A + B - 1, i), cnk(W + A - B - 1, W - 1 - i)));
            }
            pw.println(r);
        }
    }

    static long TIME_START, TIME_END;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
//        Scanner sc = new Scanner(new FileInputStream("input"));
        PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
//        PrintWriter pw = new PrintWriter(new FileOutputStream("input"));

        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        TIME_START = System.currentTimeMillis();
        Task t = new Task();
        t.solve(sc, pw);
        TIME_END = System.currentTimeMillis();
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        pw.close();
        System.err.println("Memory increased: " + (usedMemoryAfter - usedMemoryBefore) / 1000000);
        System.err.println("Time used: " + (TIME_END - TIME_START) + ".");
    }

    static class Scanner {
        StringTokenizer st;
        BufferedReader br;

        public Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public Scanner(FileReader s) throws FileNotFoundException {
            br = new BufferedReader(s);
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        public boolean ready() throws IOException {
            return br.ready();
        }
    }
}