import java.util.Scanner;

public class CaesarCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String encrypted = scanner.nextLine();
        StringBuilder decrypted = new StringBuilder();
        
        for (char c : encrypted.toCharArray()) {
            char shifted = (char)(c - 3);
            if (shifted < 'A') {
                shifted += 26;
            }
            decrypted.append(shifted);
        }
        
        System.out.println(decrypted.toString());
    }
}