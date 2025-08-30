import java.util.Scanner;
public class Main{
public static void main(String[] args){
try (Scanner sc = new Scanner(System.in)){
while(true){
int n = sc.nextInt();
if(n == 0){
break;
}
int[] score = new int[n];
for(int i = 0; i< score.length; i++){
score[i] = sc.nextInt();
}
double avg = 0, var = 0, tot = 0;
int sum = 0;
for(int i = 0; i<score.length; i++){
sum = sum + score[i];
}
avg = (double) sum / n;
for(int i = 0; i<score.length; i++){
tot = tot + (score[i] - avg) * (score[i] - avg);
}
var = tot / n;
System.out.println(Math.sqrt(var));
}
}
}
}

