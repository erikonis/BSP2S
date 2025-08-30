import java.util.Scanner;


public class Main {
	static Scanner sc = new Scanner(System.in);
	Squ in;
	Squ out = new Squ(150, 150);

	public static void main(String[] args) {
		while(true) {
			int h = sc.nextInt();
			int w = sc.nextInt();
			if( (h|w) == 0 ) break;
			new Main(new Squ(h, w)).find().print();
		}
	}

	Main(Squ in) {
		this.in=in;
	}

	Main find() {
		for(int y=1; y<150; y++) {
			for(int x=y+1; x<150; x++) {
				Squ exam = new Squ(y, x);
				if( exam.compareTo(in) <= 0 ) continue;
				if( exam.compareTo(out) < 0 ) out = exam;
			}
		}

		return this;
	}

	void print() {
		System.out.println(out);
	}
}


class Squ implements Comparable<Squ> {
	int h, w, tk;

	Squ(int h, int w) {
		this.h = h; this.w = w;
		tk = h*h + w*w;
	}

	@Override
	public int compareTo(Squ op) {
		int ret = tk - op.tk;
		if(ret == 0) ret = h - op.h;
		return ret;
	}

	@Override
	public String toString() {
		return h+" "+w;
	}
}