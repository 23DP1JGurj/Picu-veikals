package lv.rvt;

import java.util.*;

public class Main 
{
    // Lietotāju saraksts, kurā glabājas visi reģistrētie lietotāji
    private static ArrayList<Person> users = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        // Pievienojam testa lietotājus
        users.add(new Person("user1", "pass1", false));
        users.add(new Person("user2", "pass2", false));
        users.add(new Person("admin", "adminpass", true));

        // Galvenā programmas cilpa, kas nodrošina lietotāja izvēlni
        do {
            clearConsole(); // Notīram konsoli
            System.out.println("Pica veikals");
            System.out.println("1 - Picu saraksts");
            System.out.println("2 - Pasūtīt picu");
            System.out.println("3 - Akcijas");
            System.out.println("4 - Ielogoties profilā");
            System.out.println("5 - Sazināties ar mums");
            System.out.println("0 - Iziet");
            System.out.print("Ievadiet izvēles numuru: ");

            choice = scanner.nextInt();
            scanner.nextLine();  // Attīrām buferi

            // Apstrādā lietotāja izvēli
            if (choice == 1) {
                clearConsole();
                System.out.println("Picu saraksts:");
                System.out.println("1 - Pica nr.1 (20cm, 9.99€)");
                System.out.println("2 - Pica nr.2 (30cm, 10.99€)");
                System.out.println("3 - Pica nr.3 (40cm, 15.99€)");
                System.out.println("4 - Pica nr.4 (50cm, 19.99€)");
            } else if (choice == 2) {
                clearConsole();
                System.out.println("Pasūtīt picu:");
                System.out.println("Izvēlieties picu pēc numura: ");
                int pizzaChoice = scanner.nextInt();
                System.out.println("Jūs pasūtījāt Pica nr." + pizzaChoice);
            } else if (choice == 3) {
                clearConsole();
                System.out.println("Akcijas:");
                System.out.println("1 - Pica nr.1 un nr.2 kopā par īpašu cenu");
                System.out.println("2 - Pica nr.4 ar 40% atlaidi");
            } else if (choice == 4) {
                clearConsole();
                loginOrRegister(scanner); // Izsauc funkciju, lai pieteiktos vai reģistrētos
            } else if (choice == 5) {
                clearConsole();
                System.out.println("Sazināties ar mums:");
                System.out.println("1 - Atsūtīt atsauksmi");
                System.out.println("2 - Ziņot par problēmu");
            } else if (choice == 0) {
                System.out.println("Paldies, ka izmantojāt Pica veikalu!");
            } else {
                System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }

            System.out.println("\nNospiediet Enter, lai turpinātu...");
            scanner.nextLine(); 
        } while (choice != 0);
    }

    // Funkcija, kas nodrošina lietotāja pieteikšanos vai reģistrēšanos
    private static void loginOrRegister(Scanner scanner) {
        int choice;
        do {
            clearConsole(); 
            System.out.println("\n1 - Ienākt lietotāja profilā");
            System.out.println("2 - Ienākt administratora profilā");
            System.out.println("3 - Izveidot jaunu profilu");
            System.out.println("0 - Iziet");
            System.out.print("Ievadiet izvēles numuru: ");
    
            choice = scanner.nextInt();
            scanner.nextLine();  
    
            if (choice == 1 || choice == 2) {
                clearConsole();
                System.out.print("Ievadiet lietotājvārdu: ");
                String username = scanner.nextLine();
                System.out.print("Ievadiet paroli: ");
                String password = scanner.nextLine();
    
                Person foundUser = findUser(username);
    
                if (foundUser != null && foundUser.validatePassword(password)) {
                    if (choice == 1 && !foundUser.isAdmin()) {
                        System.out.println("Laipni lūdzam, " + username + "!");
                    } else if (choice == 2 && foundUser.isAdmin()) {
                        System.out.println("Laipni lūdzam, administrators " + username + "!");
                    } else {
                        System.out.println("Jums nav piekļuves šim profilam.");
                    }
                } else {
                    System.out.println("Nepareizs lietotājvārds vai parole.");
                }
            } else if (choice == 3) {
                clearConsole();
                System.out.print("Ievadiet jauno lietotājvārdu: ");
                String newUsername = scanner.nextLine();
                System.out.print("Ievadiet jauno paroli: ");
                String newPassword = scanner.nextLine();
    
                if (findUser(newUsername) == null) {
                    users.add(new Person(newUsername, newPassword, false));
                    System.out.println("Profils veiksmīgi izveidots!");
                } else {
                    System.out.println("Šāds lietotājvārds jau pastāv!");
                }

            } else if (choice == 0) {
                System.out.println("Paldies, ka izmantoji Pica veikalu!");
                break;
            } else {
                System.out.println("Nederīgs ievads, mēģiniet vēlreiz.");
            }
    
            System.out.println("\nNospiediet Enter, lai turpinātu...");
            scanner.nextLine();  
        } while (true);
    }
    
    // Funkcija, kas atrod lietotāju pēc lietotājvārda
    private static Person findUser(String username) {
        for (Person user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // Funkcija, kas notīra konsoli
    public static void clearConsole() {
        System.out.print("\033c");
        System.out.flush();
    }
}