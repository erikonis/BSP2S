import java.util.Scanner;

class Main {
	public static void main(String[] args) {

		try (Scanner scanner = new Scanner(System.in)) {
			while (scanner.hasNextDouble()) {
				double weight = scanner.nextDouble();
				System.out.println(getWeightClass(weight));
			}
		}
	}

	private static String getWeightClass(double weight) {
		String weghitClass = null;

		if (weight <= 48.00) {
			weghitClass = "light fly";
		} else if (weight <= 51.00) {
			weghitClass = "fly";
		} else if (weight <= 54.00) {
			weghitClass = "bantam";
		} else if (weight <= 57.00) {
			weghitClass = "feather";
		} else if (weight <= 60.00) {
			weghitClass = "light";
		} else if (weight <= 64.00) {
			weghitClass = "light welter";
		} else if (weight <= 69.00) {
			weghitClass = "welter";
		} else if (weight <= 75.00) {
			weghitClass = "light middle";
		} else if (weight <= 81.00) {
			weghitClass = "middle";
		} else if (weight <= 91.00) {
			weghitClass = "light heavy";
		} else {
			weghitClass = "heavy";
		}
		return weghitClass;
	}
}