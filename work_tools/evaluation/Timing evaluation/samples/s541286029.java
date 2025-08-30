import java.util.*;

class Main{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        while(true){
            int n=sc.nextInt();
            if(n==-1){
                break;
            }
            int cnt=0;
            if(n<=10){
                cnt=1150;
            }else if(n<=20){
                cnt=1150+125*(n-10);
            }else if(n<=30){
                cnt=1150+1250+140*(n-20);
            }else{
                cnt=1150+1250+1400+160*(n-30);
            }
            System.out.println(4280-cnt);
        }
    }
}
