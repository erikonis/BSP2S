import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String line;
		try {
			while ((line = br.readLine()) != null) {
				String[] v = line.split(" ");

				int size = 0;
				for (String s : v) {
					if ("+-*/".indexOf(s) == -1) {
						size++;
					}
				}

				DoubleStack stack = new DoubleStack(size);
				for (String s : v) {
					switch (s) {
					case "+": {
						double a = stack.pop();
						double b = stack.pop();
						stack.push(b + a);
					}
						break;

					case "-": {
						double a = stack.pop();
						double b = stack.pop();
						stack.push(b - a);
					}
						break;

					case "*": {
						double a = stack.pop();
						double b = stack.pop();
						stack.push(b * a);
					}
						break;

					case "/": {
						double a = stack.pop();
						double b = stack.pop();
						stack.push(b / a);
					}
						break;

					default:
						stack.push(Double.parseDouble(s));
						break;
					}
				}

				System.out.println(String.format("%.6f", stack.pop()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class DoubleStack {
		private double[] data;
		private int index = -1;

		public DoubleStack(int maxSize) {
			data = new double[maxSize];
		}

		public void push(double d) {
			data[++index] = d;
		}

		public double pop() {
			return data[index--];
		}
	}
}