import java.util.Scanner;

public class Multiplicative {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0; 

        System.out.println("Welcome to the Multiplicative Cipher Program!");

        while (choice != 4) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Encryption");
            System.out.println("2. Decryption");
            System.out.println("3. Brute force");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 1) {
                String plaintext = "";
                
                while (true) {
                    System.out.print("Enter plaintext (lowercase only, no spaces): ");
                    plaintext = scanner.nextLine();

                    if (isLowercaseOnly(plaintext)) {
                        break;
                    } else {
                        System.out.println("WARNING: You must use lowercase letters only!");
                    }
                }

                int key = 0;
                while (true) {
                    System.out.print("Enter key (must be valid, e.g., 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25): ");
                    key = scanner.nextInt();
                    scanner.nextLine();
                    
                    key = key % 26;
                    
                    if (isValidKey(key)) {
                        break;
                    } else {
                        System.out.println("WARNING: Invalid key! In a multiplicative cipher, the key cannot be an even number or 13.");
                    }
                }

                String ciphertext = "";
                
                for (int i = 0; i < plaintext.length(); i++) {
                    char letter = plaintext.charAt(i);
                    
                    int p = letter - 'a';
                    int shifted = (p * key) % 26;
                    
                    char encryptedLetter = (char) (shifted + 'A');
                    ciphertext = ciphertext + encryptedLetter;
                }

                System.out.println("Encrypted Result: " + ciphertext);

            } else if (choice == 2) {
                String ciphertext = "";
                
                while (true) {
                    System.out.print("Enter ciphertext (UPPERCASE only, no spaces): ");
                    ciphertext = scanner.nextLine();

                    if (isUppercaseOnly(ciphertext)) {
                        break;
                    } else {
                        System.out.println("WARNING: You must use UPPERCASE letters only!");
                    }
                }

                int key = 0;
                while (true) {
                    System.out.print("Enter key: ");
                    key = scanner.nextInt();
                    scanner.nextLine();
                    
                    key = key % 26; 
                    
                    if (isValidKey(key)) {
                        break;
                    } else {
                        System.out.println("WARNING: Invalid key! Must not be even or 13.");
                    }
                }

                int inverseKey = findInverseKey(key);
                String plaintext = "";
                
                for (int i = 0; i < ciphertext.length(); i++) {
                    char letter = ciphertext.charAt(i);
                    
                    int c = letter - 'A';
                    int shifted = (c * inverseKey) % 26;
                    
                    char decryptedLetter = (char) (shifted + 'a');
                    plaintext = plaintext + decryptedLetter;
                }

                System.out.println("Decrypted Result: " + plaintext);

            } else if (choice == 3) {
                String ciphertext = "";
                
                while (true) {
                    System.out.print("Enter ciphertext to brute force (UPPERCASE only): ");
                    ciphertext = scanner.nextLine();

                    if (isUppercaseOnly(ciphertext)) {
                        break;
                    } else {
                        System.out.println("WARNING: You must use UPPERCASE letters only!");
                    }
                }

                System.out.println("\n--- Brute Force Results ---");
                
                int[] possibleKeys = {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
                
                for (int j = 0; j < possibleKeys.length; j++) {
                    int currentKey = possibleKeys[j];
                    int inverseKey = findInverseKey(currentKey);
                    String possiblePlaintext = "";
                    
                    for (int i = 0; i < ciphertext.length(); i++) {
                        char letter = ciphertext.charAt(i);
                        int c = letter - 'A';
                        int shifted = (c * inverseKey) % 26;
                        char decryptedLetter = (char) (shifted + 'a');
                        possiblePlaintext = possiblePlaintext + decryptedLetter;
                    }
                    
                    System.out.println("Key " + currentKey + " gives: " + possiblePlaintext);
                }

            } else if (choice == 4) {
                System.out.println("Exiting program... Goodbye!");
            } else {
                System.out.println("Invalid choice! Please choose 1, 2, 3, or 4.");
            }
        }
        
        scanner.close(); 
    }
    public static boolean isLowercaseOnly(String text) {
        if (text.length() == 0) return false; 
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'a' || c > 'z') return false; 
        }
        return true; 
    }

    public static boolean isUppercaseOnly(String text) {
        if (text.length() == 0) return false; 
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') return false;
        }
        return true;
    }

    public static boolean isValidKey(int key) {
        if (key == 0 || key % 2 == 0 || key % 13 == 0) {
            return false;
        }
        return true;
    }

    public static int findInverseKey(int key) {
        int inverse = 1;
        for (int i = 1; i < 26; i++) {
            if ((key * i) % 26 == 1) {
                inverse = i;
                break;
            }
        }
        return inverse;
    }
}
