import java.util.*;
  
public class Main{
  
    int n;
    String[] a,b;
    String from,to;
  
    class E{
        String s;
        int cost;
        E(String s, int cost){
            this.s = s;
            this.cost = cost;
        }
    }
  
    void solve(){
        Scanner sc = new Scanner(System.in);
  
        while(true){
            n = sc.nextInt();
            if(n==0) break;
  
            a = new String[n];
            b = new String[n];
            for(int i=0; i<n; i++){
                a[i] = sc.next();
                b[i] = sc.next();
            }
            from = sc.next();
            to = sc.next();
  
            System.out.println(getMin());
        }
    }
  
    int getMin(){
        PriorityQueue<E> 
            q = new PriorityQueue<E>(n, new Comparator<E>(){
                    public int compare(E e1, E e2){
                        return e1.cost - e2.cost;
                    }
                });
        q.add(new E(from,0));
        HashSet<String> set = new HashSet<String>();
  
        while(q.size()>0){
            E e = q.poll();
            String ss = e.s;
            int cost = e.cost;
  
            if(ss.equals(to)) return cost;
            if(set.contains(ss)) continue;
            set.add(ss);
  
            for(int i=0; i<a.length; i++){
                String s = ss;
                for(int j=0; j<=s.length()-a[i].length(); j++){
                    if(a[i].equals(s.substring(j,j+a[i].length()))){
                        s = s.substring(0,j) + b[i] + 
                            s.substring(j+a[i].length(),s.length());
                        j += b[i].length()-1;
                    }
                }
                if(ss.equals(s)) continue;
                if(s.length()>to.length()) continue;
                q.add(new E(s,cost+1));
            }
        }
  
        return -1;
    }
  
    public static void main(String[] args){
        new Main().solve();
    }
}