import java.io.IOException;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) throws Exception {
		// TODO ?????????????????????????????????????????????
			Scanner sc = new Scanner(System.in);
			int a = sc.nextInt();
			double b=0,c=0,d=0,e=0;
			int x[]=new int[a];
			for(int z=0;z<a;++z){
				x[z] = sc.nextInt();
			}
			for(int z=0;z<a;++z){
				x[z] -= sc.nextInt();
				if(x[z]<0){
					x[z]=x[z]*-1;
				}
				if(e<x[z]){
					e=x[z];
				}
			}
			for(int z=0;z<a;++z){
				b = b + x[z];
			}
			
			for(int z=0;z<a;++z){
				c = c + Math.pow(x[z], 2);
			}
			c = Math.sqrt(c);
			for(int z=0;z<a;++z){
				d = d + Math.pow(x[z], 3);
			}
			d = Math.pow(d,(double)1/3);
			System.out.println(b);
			System.out.println(c);
			System.out.println(d);
			System.out.println(e);
	}

}