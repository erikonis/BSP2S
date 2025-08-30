import java.util.Scanner;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    long result = 0;
    char[] cs;
    String s;
    Map<String, Integer> map = new HashMap<String, Integer>();
    for(int i = 0, n = sc.nextInt(); i < n; i++) {
      cs = sc.next().toCharArray();
      Arrays.sort(cs);
      s = new String(cs);
      if (map.containsKey(s)) {
        result += map.get(s);
        map.put(s, map.get(s) + 1);
      } else
        map.put(s, 1);
    }
    System.out.println(result);
  }
}
