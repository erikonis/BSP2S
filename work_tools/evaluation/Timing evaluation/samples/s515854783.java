import java.util.Arrays;
import java.util.Scanner;


public class Main {

	public static void main(String[] args){
	Scanner s = new Scanner(System.in);
	while(s.hasNext()){
		int cnt = s.nextInt();
		if(cnt==0)
			break;
		int[] times = new int[cnt];
		for(int i=0 ; i<cnt ; i++) {
			times[i] = s.nextInt();
		}
		Arrays.sort(times);
		System.out.println(solve(times));
	}
	}

	public static long solve(int[] times) {
		long sum = 0;
		long tmp = 0;
		for(int i=0 ; i<times.length ; i++) {
			tmp += times[i];
			sum += tmp;
		}
		return sum;
	}
}