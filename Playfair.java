import java.util.Scanner;

public class Playfair {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0; 

        System.out.println("Welcome to the Playfair Cipher Program!");

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

                String keyword = "";
                while (true) {
                    System.out.print("Enter keyword (lowercase only, no spaces): ");
                    keyword = scanner.nextLine();

                    if (isLowercaseOnly(keyword)) {
                        break;
                    } else {
                        System.out.println("WARNING: You must use lowercase letters only!");
                    }
                }

                char[][] matrix = generateMatrix(keyword);
                
                String preparedText = "";
                for (int i = 0; i < plaintext.length(); ) {
                    char first = plaintext.charAt(i);
                    if (first == 'j') first = 'i';
                    
                    char second = 'x';
                    if (i + 1 < plaintext.length()) {
                        second = plaintext.charAt(i + 1);
                        if (second == 'j') second = 'i';
                    }
                    
                    if (first == second) {
                        preparedText = preparedText + first + 'x';
                        i = i + 1;
                    } else {
                        
                        if (i + 1 < plaintext.length()) {
                            preparedText = preparedText + first + second;
                            i = i + 2;
                        } else {
                           
                            preparedText = preparedText + first + 'x';
                            i = i + 1;
                        }
                    }
                }

                String ciphertext = "";
                for (int i = 0; i < preparedText.length(); i += 2) {
                    char char1 = preparedText.charAt(i);
                    char char2 = preparedText.charAt(i + 1);
                    
                    int[] pos1 = findPosition(matrix, char1);
                    int[] pos2 = findPosition(matrix, char2);
                    
                    int row1 = pos1[0], col1 = pos1[1];
                    int row2 = pos2[0], col2 = pos2[1];
                    
                    char enc1, enc2;
                    
                    if (row1 == row2) {
                        enc1 = matrix[row1][(col1 + 1) % 5];
                        enc2 = matrix[row2][(col2 + 1) % 5];
                    } 
                    else if (col1 == col2) {
                        enc1 = matrix[(row1 + 1) % 5][col1];
                        enc2 = matrix[(row2 + 1) % 5][col2];
                    } 
                    else {
                        enc1 = matrix[row1][col2];
                        enc2 = matrix[row2][col1];
                    }
                    
                    ciphertext = ciphertext + (char)(enc1 - 'a' + 'A') + (char)(enc2 - 'a' + 'A');
                }

                System.out.println("Encrypted Result: " + ciphertext);

            } else if (choice == 2) {
                String ciphertext = "";
                
                while (true) {
                    System.out.print("Enter ciphertext (UPPERCASE only, even number of letters): ");
                    ciphertext = scanner.nextLine();

                    if (!isUppercaseOnly(ciphertext)) {
                        System.out.println("WARNING: You must use UPPERCASE letters only!");
                    } else if (ciphertext.length() % 2 != 0) {
                        System.out.println("WARNING: Playfair ciphertext must have an EVEN number of letters!");
                    } else {
                        break;
                    }
                }

                String keyword = "";
                while (true) {
                    System.out.print("Enter keyword (lowercase only, no spaces): ");
                    keyword = scanner.nextLine();

                    if (isLowercaseOnly(keyword)) {
                        break;
                    } else {
                        System.out.println("WARNING: You must use lowercase letters only!");
                    }
                }

                char[][] matrix = generateMatrix(keyword);
                
                String plaintext = "";
                for (int i = 0; i < ciphertext.length(); i += 2) {
                    char char1 = (char)(ciphertext.charAt(i) - 'A' + 'a');
                    char char2 = (char)(ciphertext.charAt(i + 1) - 'A' + 'a');
                    
                    int[] pos1 = findPosition(matrix, char1);
                    int[] pos2 = findPosition(matrix, char2);
                    
                    int row1 = pos1[0], col1 = pos1[1];
                    int row2 = pos2[0], col2 = pos2[1];
                    
                    char dec1, dec2;
                    
                    if (row1 == row2) {
                        dec1 = matrix[row1][(col1 + 4) % 5];
                        dec2 = matrix[row2][(col2 + 4) % 5];
                    } 
                    else if (col1 == col2) {
                        dec1 = matrix[(row1 + 4) % 5][col1];
                        dec2 = matrix[(row2 + 4) % 5][col2];
                    } 
                    else {
                        dec1 = matrix[row1][col2];
                        dec2 = matrix[row2][col1];
                    }
                    
                    plaintext = plaintext + dec1 + dec2;
                }

                System.out.println("Decrypted Result: " + plaintext);
                System.out.println("(Note: You may need to manually ignore 'x' letters added for padding/double letters)");

            } else if (choice == 3) {
                System.out.println("\n--- Brute Force Warning ---");
                System.out.println("Unlike previous ciphers, the Playfair cipher creates a 5x5 grid.");
                System.out.println("This means there are 25! (25 factorial) possible keys.");
                System.out.println("That is 15,511,210,043,330,985,984,000,000 combinations!");
                System.out.println("A simple computer loop cannot brute force this in our lifetime.");
                System.out.println("Returning to menu...");

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

    public static char[][] generateMatrix(String keyword) {
        String keyString = "";
        
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            if (c == 'j') c = 'i';
            
            if (keyString.indexOf(c) == -1) {
                keyString = keyString + c;
            }
        }
        
        for (char c = 'a'; c <= 'z'; c++) {
            if (c == 'j') continue; 
            
            if (keyString.indexOf(c) == -1) {
                keyString = keyString + c;
            }
        }
        
        char[][] matrix = new char[5][5];
        int index = 0;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                matrix[row][col] = keyString.charAt(index);
                index++;
            }
        }
        return matrix;
    }

    public static int[] findPosition(char[][] matrix, char letter) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (matrix[row][col] == letter) {
                    return new int[]{row, col};
                }
            }
        }
        return new int[]{0, 0};
    }
}
