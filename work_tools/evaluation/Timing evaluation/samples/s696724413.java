import java.util.Scanner;
class Main{
    public static void main(String[] args) {
        Scanner scn=new Scanner(System.in);
       String a=scn.next();
        String b=scn.next();
        char[] ac=a.toCharArray();
        char[] bc=b.toCharArray();
        scn.close();
        String answer="YES";
        for(int i=0;i<3;i++){
            if(ac[i]!=bc[2-i]){
                answer="NO";
                break;
            }
        }
        System.out.println(answer);
    }
}
