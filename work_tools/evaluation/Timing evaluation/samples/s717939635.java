import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

class MyComparator implements Comparator<Object>{
	
	public int compare (Object obj1,Object obj2) {
		
		int num1 = (int)obj1;
        int num2 = (int)obj2;
		
		if(num1 < num2) {
            return 1;
        } else if(num1 > num2) {
            return -1;
        } else{
            return 0;
        }
	}

}

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ArrayList<Queue<Integer>> L = new ArrayList<>();
		
		int n = sc.nextInt();
		
			//Queue <Integer> pq = ;
		
			for(int i=0;i<n;i++) {
				L.add(new PriorityQueue<Integer>(new MyComparator()));
			}
		
		int q = sc.nextInt();
		
		for(int i=0;i<q;i++) {
			
			int d = sc.nextInt();
			int t = 0;
			
			switch (d){
			
				case 0:
					t = sc.nextInt();
					int x = sc.nextInt();
				
					L.get(t).add(x);
				
					break;
				
				case 1:
					t = sc.nextInt();
					if(! L.get(t).isEmpty())
						System.out.println(L.get(t).peek());
				
					break;
				
				case 2:
					t = sc.nextInt();
					if(! L.get(t).isEmpty())
						L.get(t).poll();
				
					break;
			}
		}
	}

}
