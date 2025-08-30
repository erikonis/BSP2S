public class Main{
  public void run(java.io.InputStream in, java.io.PrintStream out){
    java.util.Scanner sc = new java.util.Scanner(in);
    int n, count;
    for(;;out.println(count)){
      n = sc.nextInt(); if(n == 0)break;
      for(count = 0;n != 1;count++){
        if(n % 2 == 0)n /= 2;
        else n = n * 3 + 1;
      }
    }
    sc.close();
  }
  public static void main(String[] args){
    (new Main()).run(System.in, System.out);
  }
}