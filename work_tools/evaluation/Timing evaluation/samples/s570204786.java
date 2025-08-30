import java.io.*;
import java.util.*;

class Main{
        static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        public static void main(String[] args) throws IOException{
                Stack<Integer> s1 = new Stack<>();
                Stack<int[]> s2 = new Stack<>();
                String line = br.readLine();
                int amount = 0;
                for(int i=0; i<line.length(); i++){
                        char c = line.charAt(i);
                        if(c=='\\')
                                s1.push(i);
                        else if(c=='/' && !s1.empty()){
                                int f = s1.pop();
                                int len = i-f;
                                amount += len;
                                while(!s2.empty() && s2.peek()[0] > f){
                                        len += s2.peek()[1];
                                        s2.pop();
                                }
                                s2.push(new int[] {f, len});
                        }
                }
                System.out.println(amount);
                System.out.print(s2.size());
                if(s2.size()>0)
                        System.out.print(" ");
                Object[] o = s2.toArray();
                for(int i=0; i<o.length; i++){
                        System.out.print(((int[])o[i])[1]);
                        if(i!=o.length-1)
                                System.out.print(" ");
                }
                System.out.println();
        }
}