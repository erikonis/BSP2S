import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		int l = sc.nextInt();
		int o = sc.nextInt();
		int[] a = new int[n];
		for(int i = 0; i < n; i++){
			a[i] = sc.nextInt();
			}
		Arrays.sort(a);
		int p1 = o;
		int p2 = m;
		for(int i = n-1; i >= 0; i--){
			if(p1 / p2 <= (p1 + a[i]) / (p2 + l)){
				p1 = p1 + a[i];
				p2 = p2 + l;
				}
			}
		System.out.println(p1/p2);
		}
	}