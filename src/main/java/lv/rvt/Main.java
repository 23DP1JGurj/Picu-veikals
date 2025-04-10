package lv.rvt;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import lv.rvt.tools.EmailService;
import lv.rvt.tools.Helper;

public class Main {
    private static final EmailService emailService = new EmailService();
    private static ArrayList<Pica> picuSaraksts = new ArrayList<>();
    private static ArrayList<String> atsauksmes = new ArrayList<>();
    private static ArrayList<String> problemas = new ArrayList<>();
    private static Person loggedInUser = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        inicializetPicuSarakstu();
        atsauksmes = Helper.loadReviews();
        problemas = Helper.loadIssues();

        do {
            clearConsole();
            System.out.println("=== Pica veikals ===");
            if (loggedInUser != null) {
                System.out.println("Sveicināts, " + loggedInUser.getUsername() + "!");
            }
            System.out.println("1 - Picu saraksts");
            System.out.println("2 - Pasūtīt picu");
            System.out.println("3 - Akcijas");
            if (loggedInUser == null) {
                System.out.println("4 - Ielogoties profilā"); 
            } else {
                System.out.println("4 - Iziet no profila");
            }
            System.out.println("5 - Sazināties ar mums");
            System.out.println("0 - Iziet");
            System.out.print("Ievadiet izvēles numuru: ");
            choice = getIntInput(scanner, "Ievadiet izvēles numuru: ");

            switch (choice) {
                case 1 -> attelotPicuSarakstu(scanner);
                case 2 -> pasutitPicu(scanner);
                case 3 -> {
                    System.out.println("Akcijas:");
                    System.out.println("1 - Pica nr.1 un nr.2 kopā par īpašu cenu");
                    System.out.println("2 - Pica nr.4 ar 40% atlaidi");
                }
                case 4 -> {
                    if (loggedInUser == null) {
                        loginOrRegister(scanner);
                    } else {
                        loggedInUser = null;
                        System.out.println("Jūs esat izrakstījies no profila.");
                    }
                }
                case 5 -> handleContactUs(scanner, loggedInUser);
                case 0 -> System.out.println("Paldies, ka izmantojāt Pica veikalu!");
                default -> System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
    
            System.out.println("\nNospiediet Enter, lai turpinātu...");
            scanner.nextLine();
        } while (choice != 0);
    }

    private static void loginOrRegister(Scanner scanner) {
        int choice;
        while (true) {
            clearConsole();
            System.out.println("\n1 - Ienākt lietotāja profilā");
            System.out.println("2 - Ienākt administratora profilā");
            System.out.println("3 - Izveidot jaunu profilu");
            System.out.println("0 - Atgriezties");
            System.out.print("Ievadiet izvēles numuru: ");

            choice = getIntInput(scanner, "Ievadiet izvēles numuru: ");

            switch (choice) {
                case 1, 2 -> {
                    System.out.print("Ievadiet lietotājvārdu: ");
                    String username = scanner.nextLine();
                    System.out.print("Ievadiet paroli: ");
                    String password = scanner.nextLine();

                    Person user = Helper.authenticate(username, password);
                    if (user != null) {
                        if ((choice == 1 && !user.isAdmin()) || (choice == 2 && user.isAdmin())) {
                            loggedInUser = user;
                            System.out.println("Laipni lūdzam, " + username + (user.isAdmin() ? " (administrators)!" : "!"));
                            return; // ← Возврат в главное меню
                        } else {
                            System.out.println("Jums nav piekļuves šim profilam.");
                        }
                    } else {
                        System.out.println("Nepareizs lietotājvārds vai parole.");
                    }
                }
                case 3 -> {
                    System.out.print("Ievadiet jauno lietotājvārdu: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Ievadiet jauno paroli: ");
                    String newPassword = scanner.nextLine();
                    System.out.print("Ievadiet savu e-pastu (nav obligāti): ");
                    String email = scanner.nextLine();

                    boolean success = Helper.registerUser(newUsername, newPassword, email, false);
                    if (success) {
                        System.out.println("Profils veiksmīgi izveidots!");
                    } else {
                        System.out.println("Šāds lietotājvārds jau pastāv!");
                    }
                }
                case 0 -> { return; }
                default -> System.out.println("Nederīgs ievads, mēģiniet vēlreiz.");
            }

            System.out.println("\nNospiediet Enter, lai turpinātu...");
            scanner.nextLine();
        }
    }
    private static void attelotPicuSarakstu(Scanner scanner) {
        List<Pica> filtretasPicas = new ArrayList<>(picuSaraksts);
        int choice;

        do {
            clearConsole();
            System.out.println("=== PICU SARAKSTS ===");
            System.out.printf("%-3s | %-20s | %-6s | %-7s | %-30s%n", "Nr", "Nosaukums", "Izmērs", "Cena", "Sastāvdaļas");
            System.out.println("----------------------------------------------------------------------------------------------");

            for (Pica p : filtretasPicas) {
                System.out.printf("%-3d | %-20s | %-6s | %6.2f€ | %-30s%n", p.getNr(), p.getNosaukums(), p.getIzmers(), p.getCena(), p.getSastavdalas());
            }

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

            choice = getIntInput(scanner, "Ievadiet izvēles numuru: ");

            switch (choice) {
                case 1 -> filtretasPicas.sort(Comparator.comparingInt(p -> Integer.parseInt(p.getIzmers().replace(" cm", ""))));
                case 2 -> filtretasPicas.sort((p1, p2) -> Integer.parseInt(p2.getIzmers().replace(" cm", "")) - Integer.parseInt(p1.getIzmers().replace(" cm", "")));
                case 3 -> filtretasPicas.sort(Comparator.comparingDouble(Pica::getCena));
                case 4 -> filtretasPicas.sort((p1, p2) -> Double.compare(p2.getCena(), p1.getCena()));
                case 5 -> {
                    filtretasPicas = new ArrayList<>();
                    for (Pica p : picuSaraksts) {
                        if (p.getSastavdalas().matches(".*(bekons|pepperoni|vistas|desa|šķiņķis).*")) {
                            filtretasPicas.add(p);
                        }
                    }
                }
                case 6 -> {
                    System.out.print("Ievadiet sastāvdaļu: ");
                    String sastavdala = scanner.nextLine().toLowerCase();
                    filtretasPicas = new ArrayList<>();
                    for (Pica p : picuSaraksts) {
                        if (p.getSastavdalas().toLowerCase().contains(sastavdala)) {
                            filtretasPicas.add(p);
                        }
                    }
                }
                case 7 -> filtretasPicas.sort((p1, p2) -> Integer.compare(getPopularitate(p2), getPopularitate(p1)));
                case 8 -> pasutitPicu(scanner);
                case 9 -> { return; }
                case 0 -> filtretasPicas = new ArrayList<>(picuSaraksts);
                default -> System.out.println("Nepareiza izvēle!");
            }
        } while (true);
    }

    private static int getPopularitate(Pica p) {
        if (p.getNosaukums().contains("Margarita")) return 3;
        if (p.getNosaukums().contains("Pepperoni")) return 2;
        if (p.getNosaukums().contains("Četri sieri")) return 1;
        return 0;
    }

    private static void pasutitPicu(Scanner scanner) {
        clearConsole();
        System.out.println("Pasūtīt picu:");
        System.out.print("Izvēlieties picu pēc numura: ");
        int pizzaChoice = getIntInput(scanner, "Ievadiet izvēles numuru: ");

        Pica selectedPizza = null;
        for (Pica p : picuSaraksts) {
            if (p.getNr() == pizzaChoice) {
                selectedPizza = p;
                break;
            }
        }

        if (selectedPizza != null) {
            System.out.print("Ievadiet savu e-pastu: ");
            String customerEmail = scanner.nextLine();

            String orderDetails = "Jūs pasūtījāt: " + selectedPizza.getNosaukums() +
                    "\nIzmērs: " + selectedPizza.getIzmers() +
                    "\nCena: " + selectedPizza.getCena() + "€";

            emailService.sendOrderConfirmation(customerEmail, orderDetails);

            System.out.println("Pasūtījums apstiprināts! Apstiprinājums tika nosūtīts uz " + customerEmail);
        } else {
            System.out.println("Pica ar šo numuru nav pieejama!");
        }
    }
    private static void handleContactUs(Scanner scanner, Person loggedInUser) {
        int subChoice;
        do {
            clearConsole();
            System.out.println("=== Sazināties ar mums ===");
            System.out.println("1 - Atstāt atsauksmi");
            System.out.println("2 - Ziņot par problēmu");
            if (loggedInUser != null && loggedInUser.isAdmin()) {
                System.out.println("3 - Skatīt visas atsauksmes");
                System.out.println("4 - Skatīt visas problēmas");
            }
            System.out.println("0 - Atgriezties");
            System.out.print("Izvēlieties: ");
            subChoice = getIntInput(scanner, "Ievadiet izvēles numuru: ");
            switch (subChoice) {
                case 1 -> {
                    System.out.println("=== Atstāt atsauksmi ===");
                    System.out.print("Jūsu viedoklis: ");
                    String feedback = scanner.nextLine();
    
                    String userInfo = loggedInUser != null
                            ? " (Lietotājs: " + loggedInUser.getUsername() +
                              (loggedInUser.getEmail() != null && !loggedInUser.getEmail().isEmpty()
                                  ? ", E-pasts: " + loggedInUser.getEmail() : "") + ")"
                            : "";
    
                    atsauksmes.add(feedback + userInfo);
                    Helper.saveReviews(atsauksmes); 
                    System.out.println("Paldies par Jūsu atsauksmi!");
                }
                case 2 -> {
                    System.out.println("=== Ziņot par problēmu ===");
                    System.out.print("Vai ir kāda problēma: ");
                    String issue = scanner.nextLine();
    
                    String userInfo = loggedInUser != null
                            ? " (Lietotājs: " + loggedInUser.getUsername() +
                              (loggedInUser.getEmail() != null && !loggedInUser.getEmail().isEmpty()
                                  ? ", E-pasts: " + loggedInUser.getEmail() : "") + ")"
                            : "";
    
                    problemas.add(issue + userInfo);
                    Helper.saveIssues(problemas); 
                    System.out.println("Problēma tika reģistrēta. Paldies!");
                }
                case 3 -> {
                    if (loggedInUser != null && loggedInUser.isAdmin()) {
                        System.out.println("=== VISAS ATSAUKSMES ===");
                        if (atsauksmes.isEmpty()) System.out.println("Nav nevienas atsauksmes.");
                        else atsauksmes.forEach(a -> System.out.println("- " + a));
                    } else {
                        System.out.println("Nepieciešama administratora piekļuve.");
                    }
                }
                case 4 -> {
                    if (loggedInUser != null && loggedInUser.isAdmin()) {
                        System.out.println("=== VISAS PROBLĒMAS ===");
                        if (problemas.isEmpty()) System.out.println("Nav reģistrētu problēmu.");
                        else problemas.forEach(p -> System.out.println("- " + p));
                    } else {
                        System.out.println("Nepieciešama administratora piekļuve.");
                    }
                }
                case 0 -> { /* Atgriezties */ }
                default -> System.out.println("Nepareiza izvēle.");
            }
    
        } while (subChoice != 0);
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
    public static int getIntInput(Scanner scanner, String prompt) {
        int input = -1;
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                input = Integer.parseInt(line);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Nepareiza ievade! Lūdzu, ievadiet skaitli.");
            }
        }
        return input;
    }
    
    public static void clearConsole() {
        System.out.print("\033c");
        System.out.flush();
    }
}