import java.util.Scanner;

public class AdditiveCipher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        System.out.println("Welcome to the Additive Cipher Program!");

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
                        System.out.println("WARNING: You must use lowercase letters only! No uppercase, numbers, spaces, or symbols.");
                    }
                }

                System.out.print("Enter key (number): ");
                int key = scanner.nextInt();
                scanner.nextLine();
                
                key = key % 26; 

                String ciphertext = "";
                
                for (int i = 0; i < plaintext.length(); i++) {
                    char letter = plaintext.charAt(i);
                    
                    int shifted = (letter - 'a' + key) % 26;
                    
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
                        System.out.println("WARNING: You must use UPPERCASE letters only! No lowercase, numbers, spaces, or symbols.");
                    }
                }

                System.out.print("Enter key (number): ");
                int key = scanner.nextInt();
                scanner.nextLine();
                
                key = key % 26; 

                String plaintext = "";
                
                for (int i = 0; i < ciphertext.length(); i++) {
                    char letter = ciphertext.charAt(i);
                    
                    int shifted = (letter - 'A' - key) % 26;
                    
                    if (shifted < 0) {
                        shifted = shifted + 26; 
                    }
                    
                    char decryptedLetter = (char) (shifted + 'a');
                    plaintext = plaintext + decryptedLetter;
                }

                System.out.println("Decrypted Result: " + plaintext);

            } else if (choice == 3) {
                String ciphertext = "";
                
                while (true) {
                    System.out.print("Enter ciphertext to brute force (UPPERCASE only, no spaces): ");
                    ciphertext = scanner.nextLine();

                    if (isUppercaseOnly(ciphertext)) {
                        break;
                    } else {
                        System.out.println("WARNING: You must use UPPERCASE letters only! No lowercase, numbers, spaces, or symbols.");
                    }
                }

                System.out.println("\n--- Brute Force Results ---");
                
                for (int k = 1; k <= 25; k++) {
                    String possiblePlaintext = "";
                    
                    for (int i = 0; i < ciphertext.length(); i++) {
                        char letter = ciphertext.charAt(i);
                        int shifted = (letter - 'A' - k) % 26;
                        if (shifted < 0) {
                            shifted = shifted + 26;
                        }
                        char decryptedLetter = (char) (shifted + 'a');
                        possiblePlaintext = possiblePlaintext + decryptedLetter;
                    }
                    
                    System.out.println("Key " + k + " gives: " + possiblePlaintext);
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
            if (c < 'a' || c > 'z') {
                return false; 
            }
        }
        return true; 
    }

    public static boolean isUppercaseOnly(String text) {
        if (text.length() == 0) return false; 
        
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') {
                return false;
            }
        }
        return true;
    }
}
