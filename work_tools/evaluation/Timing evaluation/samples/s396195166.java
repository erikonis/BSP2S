import java.util.*;

public class Main {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		while(sc.hasNext()){
			String s = sc.next();
			if(s.compareTo("0")==0)break;
			
			String t = "";
			int[] c = new int[6];
			for(int i=1;i<7;i++){
				t = s.substring(i,i+1);
				if(t.compareTo("a")==0){
					c[i-1] = 10;
				}else if(t.compareTo("b")==0){
					c[i-1] = 11;
				}else if(t.compareTo("c")==0){
					c[i-1] = 12;
				}else if(t.compareTo("d")==0){
					c[i-1] = 13;
				}else if(t.compareTo("e")==0){
					c[i-1] = 14;
				}else if(t.compareTo("f")==0){
					c[i-1] = 15;
				}else{
					c[i-1] = Integer.valueOf(t);
				}
			}
			int r = c[0]*16 + c[1];
			int g = c[2]*16 + c[3];
			int b = c[4]*16 + c[5];
			
			int count=0;
			int sum=0;
			double[] d = new double[8];
			double[] e = new double[8];
			for(int i=0;i<2;i++){
				for(int j=0;j<2;j++){
					for(int k=0;k<2;k++){
						sum = (r-i*255)*(r-i*255)+(g-j*255)*(g-j*255)+(b-k*255)*(b-k*255);
						d[count] = Math.sqrt(sum);
						e[count] = d[count];
						count++;
					}
				}
			}
			Arrays.sort(e);
			for(int i=0;i<8;i++){
				if(d[i]==e[0]){
					sum = i;
					break;
				}
			}
			
			if(sum==0){
				System.out.println("black");
			}else if(sum==1){
				System.out.println("blue");
			}else if(sum==2){
				System.out.println("lime");
			}else if(sum==3){
				System.out.println("aqua");
			}else if(sum==4){
				System.out.println("red");
			}else if(sum==5){
				System.out.println("fuchsia");
			}else if(sum==6){
				System.out.println("yellow");
			}else if(sum==7){
				System.out.println("white");
			}		
			
		}
	}	
}