import java.util.Scanner;

public class AffineCipher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice = "";
        
        while (true) {
            System.out.println("\n========== AFFINE CIPHER MENU ==========");
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

        System.out.print("Enter Key A (Multiplicative - Odd numbers except 13): ");
        int keyA = getValidMultiplicativeKey(scanner);
        
        System.out.print("Enter Key B (Additive - Any number 0-25): ");
        int keyB = getValidAdditiveKey(scanner);

        String cipherText = "";

        for (int i = 0; i < plainText.length(); i++) {
            char ch = plainText.charAt(i);
            
            if (ch >= 'a' && ch <= 'z') {
                int val = ch - 'a';
                int encryptedVal = (val * keyA + keyB) % 26;
                char shifted = (char) (encryptedVal + 'A');
                
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

        System.out.print("Enter Key A (Multiplicative - Odd numbers except 13): ");
        int keyA = getValidMultiplicativeKey(scanner);
        
        System.out.print("Enter Key B (Additive - Any number 0-25): ");
        int keyB = getValidAdditiveKey(scanner);
        
        int inverseA = findInverse(keyA);

        String plainText = "";

        for (int i = 0; i < cipherText.length(); i++) {
            char ch = cipherText.charAt(i);
            
            if (ch >= 'A' && ch <= 'Z') {
                int val = ch - 'A';
                
                int decryptedVal = (inverseA * ((val - keyB + 26) % 26)) % 26;
                char shifted = (char) (decryptedVal + 'a');
                
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
        
        for (int keyA = 1; keyA < 26; keyA++) {
            
            if (keyA % 2 == 0 || keyA == 13) {
                continue; 
            }
            
            int inverseA = findInverse(keyA);
            
            for (int keyB = 0; keyB < 26; keyB++) {
                String plainText = "";
                
                for (int i = 0; i < cipherText.length(); i++) {
                    char ch = cipherText.charAt(i);
                    
                    if (ch >= 'A' && ch <= 'Z') {
                        int val = ch - 'A';
                        int decryptedVal = (inverseA * ((val - keyB + 26) % 26)) % 26;
                        char shifted = (char) (decryptedVal + 'a');
                        plainText += shifted;
                    } else {
                        plainText += ch;
                    }
                }
                System.out.println("Key A: " + keyA + ", Key B: " + keyB + " -> " + plainText);
            }
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
                    System.out.print("Invalid! Key A must be odd and not 13. Try again: ");
                }
            } catch (Exception e) {
                System.out.print("That wasn't a valid number! Try again: ");
            }
        }
    }

    public static int getValidAdditiveKey(Scanner scanner) {
        try {
            int key = Integer.parseInt(scanner.nextLine());
            return Math.abs(key) % 26;
        } catch (Exception e) {
            System.out.println("That wasn't a valid number! Defaulting Key B to 0.");
            return 0;
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