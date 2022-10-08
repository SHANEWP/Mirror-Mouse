package pkg.main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Server or Client? (s/c): ");
        
        String userType = scanner.nextLine();
        if(userType.equalsIgnoreCase("s")) {
            new Server(1111);
        } else if(userType.equalsIgnoreCase("c")) {
            new Client("10.0.0.203", 1111);
        } else {
            System.out.println("Invalid response");
        }

        scanner.close();
    }
}
