import java.util.Scanner;

public class Multiplicative{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice = "";

        while (true) {
            System.out.println("\n========== MULTIPLICATIVE CIPHER MENU ==========");
            System.out.println("1. Encryption");
            System.out.println("2. Decryption");
            System.out.println("3. Brute force");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");
            
            choice = scanner.nextLine();

            if (choice.equals("1")) {
                encrypt(scanner);
            } else if (choice.equals("2")) {
                decrypt(scanner);
            } else if (choice.equals("3")) {
                bruteForce(scanner);
            } else if (choice.equals("4")) {
                System.out.println("Exiting the program. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
        
        scanner.close();
    }

    public static void encrypt(Scanner scanner) {
        String plainText = "";
        
        while (true) {
            System.out.print("Enter plaintext (lowercase letters only): ");
            plainText = scanner.nextLine();
            
            if (!plainText.equals(plainText.toLowerCase())) {
                System.out.println("WARNING: Uppercase letters detected! Plaintext must be lowercase.");
            } else {
                break;
            }
        }

        System.out.println("Enter the key (Odd numbers except 13. e.g., 3, 5, 7, 9, 11, 15...): ");
        int key = getValidMultiplicativeKey(scanner);

        String cipherText = "";

        for (int i = 0; i < plainText.length(); i++) {
            char ch = plainText.charAt(i);
            
            if (ch >= 'a' && ch <= 'z') {
                char shifted = (char) ((((ch - 'a') * key) % 26) + 'A');
                cipherText += shifted;
            } else {
                cipherText += ch;
            }
        }
        System.out.println("Resulting Ciphertext: " + cipherText);
    }

    public static void decrypt(Scanner scanner) {
        String cipherText = "";
        
        while (true) {
            System.out.print("Enter ciphertext (uppercase letters only): ");
            cipherText = scanner.nextLine();
            
            if (!cipherText.equals(cipherText.toUpperCase())) {
                System.out.println("WARNING: Lowercase letters detected! Ciphertext must be uppercase.");
            } else {
                break;
            }
        }

        System.out.println("Enter the key (Odd numbers except 13. e.g., 3, 5, 7, 9, 11, 15...): ");
        int key = getValidMultiplicativeKey(scanner);
        
        int inverseKey = findInverse(key);

        String plainText = "";

        for (int i = 0; i < cipherText.length(); i++) {
            char ch = cipherText.charAt(i);
            
            if (ch >= 'A' && ch <= 'Z') {
                char shifted = (char) ((((ch - 'A') * inverseKey) % 26) + 'a');
                plainText += shifted;
            } else {
                plainText += ch;
            }
        }
        System.out.println("Resulting Plaintext: " + plainText);
    }

    public static void bruteForce(Scanner scanner) {
        String cipherText = "";
        
        while (true) {
            System.out.print("Enter ciphertext to brute force (uppercase letters only): ");
            cipherText = scanner.nextLine();
            
            if (!cipherText.equals(cipherText.toUpperCase())) {
                System.out.println("WARNING: Lowercase letters detected! Ciphertext must be uppercase.");
            } else {
                break;
            }
        }

        System.out.println("\n--- Brute Force Results ---");
        
        for (int key = 1; key < 26; key++) {
            if (key % 2 == 0 || key == 13) {
                continue; 
            }
            
            int inverseKey = findInverse(key);
            String plainText = "";
            
            for (int i = 0; i < cipherText.length(); i++) {
                char ch = cipherText.charAt(i);
                
                if (ch >= 'A' && ch <= 'Z') {
                    char shifted = (char) ((((ch - 'A') * inverseKey) % 26) + 'a');
                    plainText += shifted;
                } else {
                    plainText += ch;
                }
            }
            System.out.println("Key " + key + ": " + plainText);
        }
    }
    
    public static int getValidMultiplicativeKey(Scanner scanner) {
        while (true) {
            try {
                int key = Integer.parseInt(scanner.nextLine());
                key = Math.abs(key) % 26;
                
                if (key % 2 != 0 && key != 13 && key > 0) {
                    return key;
                } else {
                    System.out.print("Invalid! Key must be odd and not 13. Try again: ");
                }
            } catch (Exception e) {
                System.out.print("That wasn't a valid number! Try again: ");
            }
        }
    }
    
    public static int findInverse(int key) {
        for (int i = 1; i < 26; i++) {
            if ((key * i) % 26 == 1) {
                return i;
            }
        }
        return 1;
    }
}