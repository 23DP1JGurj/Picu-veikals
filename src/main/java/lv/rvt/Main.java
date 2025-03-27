package lv.rvt;

import java.util.*;
import java.util.stream.Collectors;

public class Main 
{   
    // Picas saraksts
    private static ArrayList<Pica> picuSaraksts = new ArrayList<>();
    // Lietotāju saraksts, kurā glabājas visi reģistrētie lietotāji
    private static ArrayList<Person> users = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        inicializetPicuSarakstu();

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
            switch (choice) {
                case 1:
                    clearConsole();
                    attelotPicuSarakstu(scanner);
                    break;
                case 2:
                    clearConsole();
                    System.out.println("Pasūtīt picu:");
                    System.out.println("Izvēlieties picu pēc numura: ");
                    int pizzaChoice = scanner.nextInt();
                    System.out.println("Jūs pasūtījāt Pica nr." + pizzaChoice);
                    break;
                case 3:
                    clearConsole();
                    System.out.println("Akcijas:");
                    System.out.println("1 - Pica nr.1 un nr.2 kopā par īpašu cenu");
                    System.out.println("2 - Pica nr.4 ar 40% atlaidi");
                    break;
                case 4:
                    clearConsole();
                    loginOrRegister(scanner); // Izsauc funkciju, lai pieteiktos vai reģistrētos
                    break;
                case 5:
                    clearConsole();
                    System.out.println("Sazināties ar mums:");
                    System.out.println("1 - Atsūtīt atsauksmi");
                    System.out.println("2 - Ziņot par problēmu");
                    break;
                case 0:
                    System.out.println("Paldies, ka izmantojāt Pica veikalu!");
                    break;
                default:
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
    
            switch (choice) {
                case 1:
                case 2:
                    clearConsole();
                    System.out.print("Ievadiet lietotājvārdu: ");
                    String username = scanner.nextLine();
                    System.out.print("Ievadiet paroli: ");
                    String password = scanner.nextLine();
    
                    Person foundUser = findUser(username);
    
                    if (foundUser != null && foundUser.validatePassword(password)) {
                        if ((choice == 1 && !foundUser.isAdmin()) || (choice == 2 && foundUser.isAdmin())) {
                            System.out.println("Laipni lūdzam, " + username + 
                                (foundUser.isAdmin() ? " (administrators)!" : "!"));
                        } else {
                            System.out.println("Jums nav piekļuves šim profilam.");
                        }
                    } else {
                        System.out.println("Nepareizs lietotājvārds vai parole.");
                    }
                    break;
                case 3:
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
                    break;
                case 0:
                    System.out.println("Atgriežamies galvenajā izvēlnē...");
                    return;
                default:
                    System.out.println("Nederīgs ievads, mēģiniet vēlreiz.");
            }
    
            System.out.println("\nNospiediet Enter, lai turpinātu...");
            scanner.nextLine();  
        } while (true);
    }
    
    private static void attelotPicuSarakstu(Scanner scanner) {
        List<Pica> filtretasPicas = new ArrayList<>(picuSaraksts); // Kopē oriģinālo sarakstu
        int choice;

        do {
            clearConsole(); // Notīra ekrānu
            System.out.println("=== PICU SARAKSTS ===");
            System.out.printf("%-3s | %-20s | %-6s | %-7s | %-30s%n", "Nr", "Nosaukums", "Izmērs", "Cena", "Sastāvdaļas");
            System.out.println("----------------------------------------------------------------------------------------------");

            for (Pica p : filtretasPicas) {
                System.out.printf("%-3d | %-20s | %-6s | %6.2f€ | %-30s%n", 
                    p.getNr(), p.getNosaukums(), p.getIzmers(), p.getCena(), p.getSastavdalas());
            }

            // **Izvēlnes iespējas**
            System.out.println("\n=== FILTRĒŠANAS/KĀRTOŠANAS IESPĒJAS ===");
            System.out.println("1 - Izmērs no mazākā uz lielāko");
            System.out.println("2 - Izmērs no lielākā uz mazāko");
            System.out.println("3 - Cena no mazākās uz lielāko");
            System.out.println("4 - Cena no lielākās uz mazāko");
            System.out.println("5 - Gaļas picas");
            System.out.println("6 - Picas ar sastāvdaļu");
            System.out.println("7 - Populārākās picas");
            System.out.println("8 - Pasūtīt picu");
            System.out.println("9 - Atgriezties");
            System.out.println("0 - Atiestatīt sarakstu");
            System.out.print("Izvēlieties: ");
            
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Notīram buferi
            } catch (Exception e) {
                System.out.println("Nepareiza ievade!");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                // *** KĀRTOŠANA ***
                case 1:
                    filtretasPicas.sort(Comparator.comparingInt(p -> Integer.parseInt(p.getIzmers().replace(" cm", ""))));
                    break;
                case 2:
                    filtretasPicas.sort((p1, p2) -> Integer.parseInt(p2.getIzmers().replace(" cm", "")) - 
                                              Integer.parseInt(p1.getIzmers().replace(" cm", "")));
                    break;
                case 3:
                    filtretasPicas.sort(Comparator.comparingDouble(Pica::getCena));
                    break;
                case 4:
                    filtretasPicas.sort((p1, p2) -> Double.compare(p2.getCena(), p1.getCena()));
                    break;
                // *** FILTRĒŠANA ***
                case 5:
                    // Filtrē gaļas picas
                    filtretasPicas = new ArrayList<>();
                    for (Pica p : picuSaraksts) {
                        if (p.getSastavdalas().matches(".*(bekons|pepperoni|vistas|desa|šķiņķis).*")) {
                            filtretasPicas.add(p);
                        }
                    }
                    break;
                case 6:
                    // Filtrē pēc sastāvdaļas
                    System.out.print("Ievadiet sastāvdaļu: ");
                    String sastavdala = scanner.nextLine().toLowerCase();

                    filtretasPicas = new ArrayList<>();
                    for (Pica p : picuSaraksts) {
                        if (p.getSastavdalas().toLowerCase().contains(sastavdala)) {
                            filtretasPicas.add(p);
                        }
                    }
                    break;
                case 7:
                    // Kārto pēc popularitātes
                    filtretasPicas.sort((p1, p2) -> Integer.compare(getPopularitate(p2), getPopularitate(p1)));
                    break;
                case 8:
                    // Pasūtīt picu
                    System.out.print("Ievadiet picas numuru: ");
                    int nr = scanner.nextInt();
                    scanner.nextLine(); // Notīram buferi
                    
                    boolean exists = false;
                    for (Pica p : picuSaraksts) {
                        if (p.getNr() == nr) {
                            System.out.println("Pasūtīta: " + p.getNosaukums() + " par " + p.getCena() + "€");
                            exists = true;
                            System.out.println("\nNospiediet Enter, lai turpinātu...");
                            scanner.nextLine(); 
                            break;
                        }
                    }
                    if (!exists) {
                        System.out.println("Šāda pica nav pieejama!");
                        System.out.println("\nNospiediet Enter, lai turpinātu...");
                        scanner.nextLine(); 
                    }
                    break;
                case 9:
                    return; // Atgriežamies uz galveno izvēlni
                case 0:
                    // **ATIETASTA VISU SARAKSTU**
                    filtretasPicas = new ArrayList<>(picuSaraksts);
                    break;
                default:
                    System.out.println("Nepareiza izvēle!");
            }

        } while (true);
    }
    
    // Palīgfunkcija popularitātes noteikšanai
    private static int getPopularitate(Pica p) {
        if (p.getNosaukums().contains("Margarita")) return 3;
        if (p.getNosaukums().contains("Pepperoni")) return 2;
        if (p.getNosaukums().contains("Četri sieri")) return 1;
        return 0;
    }

    private static void inicializetPicuSarakstu() {
        picuSaraksts.add(new Pica(1, "Margarita", "25 cm", 5.0, "tomātu mērce, mocarella, baziliks, olivelļa"));
        picuSaraksts.add(new Pica(2, "Pepperoni", "30 cm", 8.0, "tomātu mērce, mocarella, pepperoni, oregano"));
        picuSaraksts.add(new Pica(3, "Havaju", "35 cm", 9.0, "tomātu mērce, mocarella, škļņķis, ananāsi"));
        picuSaraksts.add(new Pica(4, "Galas", "40 cm", 12.0, "tomātu mērce, mocarella, bekons, desa"));
        picuSaraksts.add(new Pica(5, "Četri sieri", "30 cm", 10.0, "mocarella, parmezāns, dorblu siers, gouda"));
        picuSaraksts.add(new Pica(6, "BBQ", "50 cm", 18.0, "BBQ mērce, mocarella, vistas gaļa, sīpols"));
        picuSaraksts.add(new Pica(7, "Veģetārā", "20 cm", 6.0, "tomātu mērce, mocarella, paprika, šampinjoni"));
        picuSaraksts.add(new Pica(8, "Cēzara", "35 cm", 11.0, "cēzara mērce, vistas gaļa, kirštomāti, mocarella"));
        picuSaraksts.add(new Pica(9, "Asais Meksikānis", "30 cm", 10.0, "tomātu mērce, jalapeno, vistas gaļa, sīpols"));
        picuSaraksts.add(new Pica(10, "Ar škļņķi un sēnēm", "25 cm", 7.0, "tomātu mērce, škļņķis, šampinjoni, mocarella"));
        picuSaraksts.add(new Pica(11, "Lauku", "40 cm", 13.0, "krējuma mērce, kartupeļi, bekons, sīpols"));
        picuSaraksts.add(new Pica(12, "Jūras vešku", "50 cm", 20.0, "krēmvelda mērce, garneles, kalmāri, mocarella"));
        picuSaraksts.add(new Pica(13, "Salami", "30 cm", 9.0, "tomātu mērce, mocarella, salami, olivas"));
        picuSaraksts.add(new Pica(14, "Grieķu", "25 cm", 7.0, "tomātu mērce, feta siers, olivas, tomāti"));
        picuSaraksts.add(new Pica(15, "Carbonara", "35 cm", 12.0, "krēmvelda mērce, bekons, parmezāns, sīpols"));
        picuSaraksts.add(new Pica(16, "Firmas", "40 cm", 14.0, "tomātu mērce, vistas gaļa, bekons, šampinjoni"));
        picuSaraksts.add(new Pica(17, "Ar tunci", "30 cm", 9.0, "tomātu mērce, tuncis, sīpoli, mocarella"));
        picuSaraksts.add(new Pica(18, "Rančo", "50 cm", 17.0, "rančo mērce, vistas gaļa, bekons, čedara siers"));
        picuSaraksts.add(new Pica(19, "Itāļu", "35 cm", 13.0, "tomātu mērce, mocarella, prosciutto, rukola"));
        picuSaraksts.add(new Pica(20, "Ar trifelēm", "40 cm", 19.0, "krēmvelda mērce, trifelu eļļa, šampinjoni, mocarella"));
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