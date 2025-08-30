import java.io.*;

public class Main {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String line;
			while(true){
				/* input */
				line = br.readLine();

				int n = Integer.parseInt(line.split(" ")[0]), k = Integer.parseInt(line.split(" ")[1]);
				
				if(n==0&&k==0) break;
				
				int[] s = new int[k];
				int[][] b = new int[n][k];
				
				line = br.readLine();
				String[] str = line.split(" ");
				for(int i=0;i<k;i++){
					s[i] = Integer.parseInt(str[i]);
				}
				
				for(int i=0;i<n;i++){
					line = br.readLine();
					str = line.split(" "); 
					for(int j=0;j<k;j++){
						b[i][j] = Integer.parseInt(str[j]);
					}
				}

				/* processing */
				boolean f = true;
				for(int i=0;i<k;i++){
					int sum = 0;
					for(int j=0;j<n;j++){
						sum += b[j][i];
					}
					if(sum>s[i]){
						f = false;
						break;
					}
					if(!f) break;
				}
				
				/* output */
				if(f){
					System.out.println("Yes");
				} else {
					System.out.println("No");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}