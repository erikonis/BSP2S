import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(true){
			int n =sc.nextInt();
			if(n==0)break;
			int teams[][] = new int[n][4];
			for (int i = 0; i < n; i++) {
				teams[i][0] = sc.nextInt();
				teams[i][1] = sc.nextInt();
				teams[i][2] = sc.nextInt();
				teams[i][3] = sc.nextInt();
			}
			Arrays.sort(teams,new Comparator<int[]>() {
				public int compare(int a[], int b[]) {
					return a[0] - b[0];
				}
			});
			
			Arrays.sort(teams,new Comparator<int[]>() {
				public int compare(int a[], int b[]) {
					return a[3] - b[3];
				}
			});
			
			Arrays.sort(teams,new Comparator<int[]>() {
				public int compare(int a[], int b[]) {
					return b[2] - a[2];
				}
			});
			HashMap<Integer, Integer> map = new HashMap<>();
			int c=0;
			for (int i = 0; i < n; i++) {
				if(map.get(teams[i][1])!=null){
					if(map.get(teams[i][1])>=3&&c<10){
						continue;
					}else if(map.get(teams[i][1])>=2&&10<=c&&c<20){
						continue;
					}else if(map.get(teams[i][1])>=1&&20<=c&&c<26){
						continue;
					}
					map.put(teams[i][1],map.get(teams[i][1])+1);
				}else{
					map.put(teams[i][1], 1);
				}
				
				System.out.println(teams[i][0]);
				c++;
				if(c==26)break;
			}
		}
	}
}