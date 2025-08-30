import java.util.*;

public class Main{

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        // input
        int num = scan.nextInt();
        int direction = 0;
        boolean wait = true;
        int count = 1;
        int memory = Integer.parseInt(scan.next());
        for(int i = 1; i < num; i++){
            int temp = Integer.parseInt(scan.next());
            if(wait){
                if(temp > memory){
                    direction = 1;
                    wait = false;
                } else if (temp < memory){
                    direction = -1;
                    wait = false;
                } else {
                    direction = 0;
                }
            } else {
                if(direction == 1 && temp < memory){
                    count++;
                    direction = 0;
                    wait = true;
                } else if(direction == -1 && temp > memory){
                    count++;
                    direction = 0;
                    wait = true;
                }
            }
            memory = temp;
        }

        // answer
        System.out.println(count);
    }
}