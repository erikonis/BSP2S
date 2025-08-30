import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        Student[] students = new Student[N];
        for (int i = 0; i < N; i++) {
            students[i] = new Student(i + 1, sc.nextInt());
        }

        Arrays.sort(students, Comparator.comparingInt(s -> s.arrivalTime));

        for (int i = 0; i < N; i++) {
            System.out.print(students[i].id);
            if (i < N - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        sc.close();
    }

    static class Student {
        int id;
        int arrivalTime;

        public Student(int id, int arrivalTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
        }
    }
}