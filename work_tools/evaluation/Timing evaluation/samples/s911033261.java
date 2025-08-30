import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while(sc.hasNext()){
			int[] a = new int[12];
			String s = sc.next();
			int sum = 0;
			int count = 0;
			int start = 0;
			for(int j=0;j<11;j++){
				start = count;
				while(s.substring(count,count+1).compareTo(",")!=0){
					count++;
				}
				a[j] = Integer.valueOf(s.substring(start,count));
				sum += a[j];
				count++;
			}
			sum -= a[10];
			a[11] = Integer.valueOf(s.substring(count,s.length()));
			double d = (double)a[10]/(a[10]+a[11]);
			d *= sum;
			
			sum = 0;
			for(int j=0;j<10;j++){
				sum += a[j];
				if(sum>=d){
					System.out.println(j+1);
					break;
				}
			}
		}
	
	}
}