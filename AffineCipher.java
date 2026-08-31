import java.util.Scanner;

public class AffineCipher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0; 

        System.out.println("Welcome to the Affine Cipher Program!");

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

                int key1 = 0;
                while (true) {
                    System.out.print("Enter Key 1 (Multiplicative - e.g., 1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25): ");
                    key1 = scanner.nextInt();
                    scanner.nextLine();
                    key1 = key1 % 26; 
                    
                    if (isValidKey1(key1)) {
                        break;
                    } else {
                        System.out.println("WARNING: Invalid Key 1! Cannot be even or 13.");
                    }
                }

                System.out.print("Enter Key 2 (Additive - any number): ");
                int key2 = scanner.nextInt();
                scanner.nextLine();
                key2 = key2 % 26;

                String ciphertext = "";
                
                for (int i = 0; i < plaintext.length(); i++) {
                    char letter = plaintext.charAt(i);
                    int p = letter - 'a';
                    
                    int shifted = (p * key1 + key2) % 26;
                    
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

                int key1 = 0;
                while (true) {
                    System.out.print("Enter Key 1 (Multiplicative): ");
                    key1 = scanner.nextInt();
                    scanner.nextLine();
                    key1 = key1 % 26; 
                    
                    if (isValidKey1(key1)) {
                        break;
                    } else {
                        System.out.println("WARNING: Invalid Key 1! Cannot be even or 13.");
                    }
                }

                System.out.print("Enter Key 2 (Additive): ");
                int key2 = scanner.nextInt();
                scanner.nextLine();
                key2 = key2 % 26;

                int inverseKey1 = findInverseKey(key1);
                String plaintext = "";
                
                for (int i = 0; i < ciphertext.length(); i++) {
                    char letter = ciphertext.charAt(i);
                    int c = letter - 'A';
                    
                    int step1 = c - key2;
                    
                    if (step1 < 0) {
                        step1 = step1 + 26;
                    }
                    
                    int shifted = (step1 * inverseKey1) % 26;
                    
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
                System.out.println("Note: There are 312 possible combinations (12 valid Key 1s * 26 Key 2s)");
                
                int[] possibleKey1s = {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
                
                for (int j = 0; j < possibleKey1s.length; j++) {
                    int k1 = possibleKey1s[j];
                    int inverseK1 = findInverseKey(k1);
                    
                    for (int k2 = 0; k2 <= 25; k2++) {
                        String possiblePlaintext = "";
                        
                        for (int i = 0; i < ciphertext.length(); i++) {
                            char letter = ciphertext.charAt(i);
                            int c = letter - 'A';
                            
                            int step1 = c - k2;
                            if (step1 < 0) {
                                step1 = step1 + 26;
                            }
                            
                            int shifted = (step1 * inverseK1) % 26;
                            char decryptedLetter = (char) (shifted + 'a');
                            possiblePlaintext = possiblePlaintext + decryptedLetter;
                        }
                        
                        System.out.println("Key1: " + k1 + ", Key2: " + k2 + " -> " + possiblePlaintext);
                    }
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

    public static boolean isValidKey1(int key) {
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
