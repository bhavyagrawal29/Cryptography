import java.util.Scanner;

public class AutoKey {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0; 

        System.out.println("Welcome to the Autokey Cipher Program!");

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

                System.out.print("Enter initial key (number): ");
                int initialKey = scanner.nextInt();
                scanner.nextLine();
                initialKey = initialKey % 26;

                String ciphertext = "";
                int currentKey = initialKey;
                
                for (int i = 0; i < plaintext.length(); i++) {
                    char letter = plaintext.charAt(i);
                    int p = letter - 'a';
                    
                    int shifted = (p + currentKey) % 26;
                    
                    char encryptedLetter = (char) (shifted + 'A');
                    ciphertext = ciphertext + encryptedLetter;
                    
                    currentKey = p; 
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

                System.out.print("Enter initial key (number): ");
                int initialKey = scanner.nextInt();
                scanner.nextLine();
                initialKey = initialKey % 26;

                String plaintext = "";
                int currentKey = initialKey;
                
                for (int i = 0; i < ciphertext.length(); i++) {
                    char letter = ciphertext.charAt(i);
                    int c = letter - 'A';
                    
                    int shifted = (c - currentKey) % 26;
                    
                    if (shifted < 0) {
                        shifted = shifted + 26; 
                    }
                    
                    char decryptedLetter = (char) (shifted + 'a');
                    plaintext = plaintext + decryptedLetter;
                    
                    currentKey = shifted; 
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
                System.out.println("Trying all 26 possible initial keys:");
                
                for (int k = 0; k <= 25; k++) {
                    String possiblePlaintext = "";
                    int currentKey = k;
                    
                    for (int i = 0; i < ciphertext.length(); i++) {
                        char letter = ciphertext.charAt(i);
                        int c = letter - 'A';
                        
                        int shifted = (c - currentKey) % 26;
                        if (shifted < 0) {
                            shifted = shifted + 26;
                        }
                        
                        char decryptedLetter = (char) (shifted + 'a');
                        possiblePlaintext = possiblePlaintext + decryptedLetter;
                        
                        currentKey = shifted;
                    }
                    
                    System.out.println("Initial Key " + k + " gives: " + possiblePlaintext);
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
}
