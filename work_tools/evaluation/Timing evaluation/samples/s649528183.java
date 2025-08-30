import java.util.Scanner;

public class Main {

	static char[] s;
	static int idx;
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		int N = cin.nextInt();
		while(N--!=0){
			String str = cin.next();
			idx=0;
			char[] a = str.toCharArray();
			s = new char[150];
			for(int i = 0; i < a.length;i++){
				s[i]=a[i];
			}

			System.out.println(expression());
		}

	}
	static int expression(){
		int res =term();
		while(s[idx]=='+'||s[idx]=='-'){
			if(s[idx]=='+'){
				idx++;
				res+=term();
			}
			else if(s[idx]=='-'){
				idx++;
				res-=term();
			}
		}
		return res;
	}
	static int term(){
		int res = fact();
		while(s[idx]=='*'||s[idx]=='/'){
			if(s[idx]=='*'){
				idx++;
				res*=fact();
			}
			else{
				idx++;
				res/=fact();
			}
		}
		return res;
	}
	static int fact(){
		int res=0;
		if(s[idx]=='('){
			idx++;
			res = expression();
			idx++;
		}
		else{
			while(true){
				res += s[idx]-'0';
				idx++;
				if(!Character.isDigit(s[idx])){
					break;
				}
				res*=10;
			}

		}
		return res;
	}
}