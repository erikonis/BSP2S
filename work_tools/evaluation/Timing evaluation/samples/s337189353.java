import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import  java.io.*;
import  java.math.*;
import java.nio.Buffer;
import java.sql.SQLSyntaxErrorException;
import  java.util.*;
import  java.text.*;
import java.util.stream.Collectors;


public class Main {

    public static int gcd(int n,int m){
        return (m==0)?n:gcd(m,n%m);
    }
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);

        long n=cin.nextInt();
        long m=cin.nextInt();
        long k=cin.nextInt();
        if(k-n-m<0){
            System.out.println("No");
            return;
        }
        if(4*n*m<(k-n-m)*(k-n-m)){
            System.out.println("Yes");
        }
        else{
            System.out.println("No");
        }
    }

}

