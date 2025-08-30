import java.util.*;

class Main{
	public static void main(String[] args){
		Solve s = new Solve();
		s.solve();
	}	
}

class Solve{
	Solve(){}
	
	Scanner in = new Scanner(System.in);

	void solve(){
		while(in.hasNext()){
			String s = in.next();
			int cnt1 = 0, cnt2 = 0;
			for(int i = 0; i < s.length() - 2; i++){
				if(s.charAt(i) == 'J' && s.charAt(i + 1) == 'O' && s.charAt( i + 2) == 'I') cnt1++;
				else if(s.charAt(i) == 'I' && s.charAt(i + 1) == 'O' && s.charAt( i + 2) == 'I') cnt2++;
			}
			System.out.println(cnt1);
			System.out.println(cnt2);
		}
	}

	
}