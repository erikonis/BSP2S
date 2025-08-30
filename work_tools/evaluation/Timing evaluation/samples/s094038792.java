import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner s=new Scanner(System.in);
        int n=s.nextInt();
        ArrayList<Integer>l=new ArrayList();
        ArrayDeque<Integer>r=new ArrayDeque();
        for(int i=0;i<n;++i)
            l.add(s.nextInt());
        
        while(!l.isEmpty()){
            for(int i=l.size();i>=0;--i){
                if(i==0){
                    System.out.println(-1);
                    return;
                }
                if(i==l.get(i-1)){
                    l.remove(i-1);
                    r.addFirst(i);
                    break;
                }
            }
        }
        r.stream().forEach(System.out::println);
    }
}