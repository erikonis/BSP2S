import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        Map<String, Integer> productMap = new HashMap<>();
        
        for (int i = 0; i < n; i++) {
            String[] line = br.readLine().split(" ");
            String product = line[0];
            int quantity = Integer.parseInt(line[1]);
            
            productMap.put(product, productMap.getOrDefault(product, 0) + quantity);
        }
        
        List<String> products = new ArrayList<>(productMap.keySet());
        
        products.sort((a, b) -> {
            if (a.length() != b.length()) {
                return a.length() - b.length();
            }
            return a.compareTo(b);
        });
        
        for (String product : products) {
            System.out.println(product + " " + productMap.get(product));
        }
    }
}