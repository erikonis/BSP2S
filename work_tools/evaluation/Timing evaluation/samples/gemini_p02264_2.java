import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Process {
    String name;
    int time;

    public Process(String name, int time) {
        this.name = name;
        this.time = time;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int q = sc.nextInt();
        Queue<Process> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            String name = sc.next();
            int time = sc.nextInt();
            queue.offer(new Process(name, time));
        }

        long currentTime = 0;
        while (!queue.isEmpty()) {
            Process currentProcess = queue.poll();
            if (currentProcess.time <= q) {
                currentTime += currentProcess.time;
                System.out.println(currentProcess.name + " " + currentTime);
            } else {
                currentTime += q;
                currentProcess.time -= q;
                queue.offer(currentProcess);
            }
        }
        sc.close();
    }
}