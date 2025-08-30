import java.util.*;

class Main{
    Scanner sc = new Scanner(System.in);
    
    Main(){
    double r = sc.nextDouble();
    System.out.printf("%.10f %.10f\n",r*r*Math.PI,(2*r)*Math.PI);
    
    }
    public static void main(String[] args){
    new Main();
    }    
}
