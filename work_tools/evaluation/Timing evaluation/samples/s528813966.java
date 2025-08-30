import java.util.*;
public class Main {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		// 整数の入力
		String a = sc.next();
        String b[] = a.split("");
        int c[] = new int[4];
        for(int i = 0;i < 4; i++){
          c[i] =Integer.parseInt(b[i]);
        }
        if(c[0] + c[1] + c[2] + c[3] == 7){
          System.out.println(c[0] + "+" + c[1] + "+" + c[2] + "+" + c[3] + "=7");
        }else if(c[0] + c[1] + c[2] - c[3] == 7){
          System.out.println(c[0] + "+" + c[1] + "+" + c[2] + "-" + c[3] + "=7");
        }else if(c[0] + c[1] - c[2] + c[3] == 7){
          System.out.println(c[0] + "+" + c[1] + "-" + c[2] + "+" + c[3] + "=7");
        }else if(c[0] + c[1] - c[2] - c[3] == 7){
          System.out.println(c[0] + "+" + c[1] + "-" + c[2] + "-" + c[3] + "=7");
        }else if(c[0] - c[1] + c[2] + c[3] == 7){
          System.out.println(c[0] + "-" + c[1] + "+" + c[2] + "+" + c[3] + "=7");
        }else if(c[0] - c[1] + c[2] - c[3] == 7){
          System.out.println(c[0] + "-" + c[1] + "+" + c[2] + "-" + c[3] + "=7");
        }else if(c[0] - c[1] - c[2] + c[3] == 7){
          System.out.println(c[0] + "-" + c[1] + "-" + c[2] + "+" + c[3] + "=7");
        }else if(c[0] - c[1] - c[2] - c[3] == 7){
          System.out.println(c[0] + "-" + c[1] + "-" + c[2] + "-" + c[3] + "=7");
        }
    }
}