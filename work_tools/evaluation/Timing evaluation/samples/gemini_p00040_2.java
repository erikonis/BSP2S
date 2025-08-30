import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine(); // Consume newline

        for (int i = 0; i < n; i++) {
            String encryptedText = sc.nextLine();
            String decryptedText = decrypt(encryptedText);
            System.out.println(decryptedText);
        }
        sc.close();
    }

    private static String decrypt(String encryptedText) {
        // Iterate through all possible alpha and beta values
        // alpha must be coprime with 26.
        // Coprime alpha values with 26 are: 1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25
        int[] validAlphas = {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};

        for (int alpha : validAlphas) {
            for (int beta = 0; beta < 26; beta++) {
                String currentDecryption = applyDecryption(encryptedText, alpha, beta);
                if (currentDecryption.contains("that") || currentDecryption.contains("this")) {
                    return currentDecryption;
                }
            }
        }
        return "Decryption failed"; // Should not happen based on problem description
    }

    private static String applyDecryption(String encryptedText, int alpha, int beta) {
        StringBuilder decrypted = new StringBuilder();
        int alphaInverse = -1;

        // Calculate modular multiplicative inverse of alpha mod 26
        // ax = 1 (mod m)
        for (int x = 0; x < 26; x++) {
            if ((alpha * x) % 26 == 1) {
                alphaInverse = x;
                break;
            }
        }

        for (char c : encryptedText.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                int y = c - 'a'; // Encrypted character as number
                // x = (alpha_inv * (y - beta)) mod 26
                int x = (alphaInverse * (y - beta + 26)) % 26;
                decrypted.append((char) ('a' + x));
            } else {
                decrypted.append(c);
            }
        }
        return decrypted.toString();
    }
}