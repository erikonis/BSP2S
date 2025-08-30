import java.util.*; import java.io.*; import java.math.*;
public class Main{
	static void solve(){//Here is the main function
		ArrayList<Integer> one = nextIntArray();
		int N = one.get(0);
		int K = one.get(1);
		int mod = 998244353;
		long[] dp = new long[N];
		long[] ruiseki = new long[N];
		dp[0] = 1;
		ruiseki[0] = 1;
		ArrayList<Node> S = new ArrayList<>();
		for(int i = 0; i < K; i++){
			ArrayList<Integer> tmp = nextIntArray();
			S.add(new Node(tmp.get(0), tmp.get(1)));
		}
		for(int i = 1; i < N; i++){
			for(int j = 0; j < S.size(); j++){
				Node check = S.get(j);
				if(i - check.L < 0){
					continue;
				}
				if(check.L == check.R){
					dp[i] += dp[i - check.L];
				}else if(i - check.R - 1 < 0){
					dp[i] += ruiseki[i - check.L];
				}else{
					dp[i] += ruiseki[i - check.L] - ruiseki[i - check.R - 1];
				}
				if(dp[i] < 0){
					dp[i] += mod;
				}
				dp[i] %= mod;
			}
			ruiseki[i] = dp[i] + ruiseki[i - 1];
			ruiseki[i] %= mod;
		}
		
		myout(dp[N - 1]);

	}
	//Method addition frame start

static class Node{
	int L;
	int R;
	Node(int L, int R){
		this.L = L;
		this.R = R;
	}
}

	//Method addition frame end

	//Don't have to see. start------------------------------------------
	static class InputIterator{
		ArrayList<String> inputLine = new ArrayList<String>(1024);
		int index = 0; int max; String read;
		InputIterator(){
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try{
				while((read = br.readLine()) != null){
					inputLine.add(read);
				}
			}catch(IOException e){}
			max = inputLine.size();
		}
		boolean hasNext(){return (index < max);}
		String next(){
			if(hasNext()){
				return inputLine.get(index++);
			}else{
				throw new IndexOutOfBoundsException("There is no more input");
			}
		}
	}
	static HashMap<Integer, String> CONVSTR = new HashMap<Integer, String>();
	static InputIterator ii = new InputIterator();//This class cannot be used in reactive problem.
	static PrintWriter out = new PrintWriter(System.out);
	static void flush(){out.flush();}
	static void myout(Object t){out.println(t);}
	static void myerr(Object t){System.err.print("debug:");System.err.println(t);}
	static String next(){return ii.next();}
	static boolean hasNext(){return ii.hasNext();}
	static int nextInt(){return Integer.parseInt(next());}
	static long nextLong(){return Long.parseLong(next());}
	static double nextDouble(){return Double.parseDouble(next());}
	static ArrayList<String> nextStrArray(){return myconv(next(), 8);}
	static ArrayList<String> nextCharArray(){return myconv(next(), 0);}
	static ArrayList<Integer> nextIntArray(){
		ArrayList<String> input = nextStrArray(); ArrayList<Integer> ret = new ArrayList<Integer>(input.size());
		for(int i = 0; i < input.size(); i++){
			ret.add(Integer.parseInt(input.get(i)));
		}
		return ret;
	}
	static ArrayList<Long> nextLongArray(){
		ArrayList<String> input = nextStrArray(); ArrayList<Long> ret = new ArrayList<Long>(input.size());
		for(int i = 0; i < input.size(); i++){
			ret.add(Long.parseLong(input.get(i)));
		}
		return ret;
	}
	@SuppressWarnings("unchecked")
	static String myconv(Object list, int no){//only join
		String joinString = CONVSTR.get(no);
		if(list instanceof String[]){
			return String.join(joinString, (String[])list);
		}else if(list instanceof ArrayList){
			return String.join(joinString, (ArrayList)list);
		}else{
			throw new ClassCastException("Don't join");
		}
	}
	static ArrayList<String> myconv(String str, int no){//only split
		String splitString = CONVSTR.get(no);
		return new ArrayList<String>(Arrays.asList(str.split(splitString)));
	}
	public static void main(String[] args){
		CONVSTR.put(8, " "); CONVSTR.put(9, "\n"); CONVSTR.put(0, "");
		solve();flush();
	}
	//Don't have to see. end------------------------------------------
}
