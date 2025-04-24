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
    

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int choice;
        inicializetPicuSarakstu();
        atsauksmes = Helper.loadReviews();
        problemas = Helper.loadIssues();
        AsciiDoorAnimation.play();
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
                            return;
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
            System.out.println("\nNospiediet Enter, lai turpinātu...");
            scanner.nextLine();
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
        System.out.println("=== Pieejamās picas ===");
        for (Pica p : picuSaraksts) {
            System.out.printf("%-3d | %-20s | %-6s | %.2f€%n", p.getNr(), p.getNosaukums(), p.getIzmers(), p.getCena());
        }
    
        Pica selectedPizza = null;
    
        while (selectedPizza == null) {
            System.out.print("Izvēlieties picu pēc numura vai ievadiet 0 lai atgrieztos: ");
            int pizzaChoice = getIntInput(scanner, "");
            if (pizzaChoice == 0) return;
    
            for (Pica p : picuSaraksts) {
                if (p.getNr() == pizzaChoice) {
                    selectedPizza = p;
                    break;
                }
            }
    
            if (selectedPizza == null) {
                System.out.println("Nepareizs numurs! Mēģiniet vēlreiz.");
            }
        }
    
        System.out.print("Ievadiet savu e-pastu (vai atstājiet tukšu): ");
        String customerEmail = scanner.nextLine().trim();
    
        System.out.print("Vai jums ir promokods? (jā/nē): ");
        String hasPromo = scanner.nextLine().trim().toLowerCase();
        boolean isYes = hasPromo.contains("j") || hasPromo.contains("y");
        double discount = 0.0;
    
        if (isYes) {
            System.out.print("Ievadiet promokodu: ");
            String promoCode = scanner.nextLine().trim().toLowerCase();
            if (promoCode.equals("atlaide10")) {
                discount = 0.10;
                System.out.println("Tika piemērota 10% atlaide!");
            } else {
                System.out.println("Nederīgs promokods.");
            }
        }
    
        double deliveryFee = 2.50;
        double finalPrice = selectedPizza.getCena() * (1 - discount) + deliveryFee;
    
        String orderDetails = "Jūs pasūtījāt: " + selectedPizza.getNosaukums() +
                "\nIzmērs: " + selectedPizza.getIzmers() +
                String.format("\nCena ar atlaidi un piegādi: %.2f€ (t.sk. piegāde: %.2f€)", finalPrice, deliveryFee);
    
        boolean emailValid = isValidEmail(customerEmail);
    
        if (emailValid) {
            emailService.sendOrderConfirmation(customerEmail, orderDetails);
            System.out.println("Pasūtījums apstiprināts! Apstiprinājums tika nosūtīts uz " + customerEmail);
        } else {
            System.out.println("Pasūtījums apstiprināts! (E-pasts netika nosūtīts — nav norādīts vai nav derīgs)");
        }
    
        System.out.println("\n" + orderDetails);
        System.out.println("\nNospiediet Enter, lai turpinātu...");
        scanner.nextLine();
    }
    
    
    private static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        // Vienkārša regex validācija
        return email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
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
                        if (atsauksmes.isEmpty()) {
                            System.out.println("Nav nevienas atsauksmes.");
                        } else {
                            for (int i = 0; i < atsauksmes.size(); i++) {
                                System.out.println("- " + atsauksmes.get(i));
                            }
                        }
                    } else {
                        System.out.println("Nepieciešama administratora piekļuve.");
                    }
                }
                case 4 -> {
                    if (loggedInUser != null && loggedInUser.isAdmin()) {
                        System.out.println("=== VISAS PROBLĒMAS ===");
                        if (problemas.isEmpty()) {
                            System.out.println("Nav reģistrētu problēmu.");
                        } else {
                            for (int i = 0; i < problemas.size(); i++) {
                                System.out.println("- " + problemas.get(i));
                            }
                        }
                    } else {
                        System.out.println("Nepieciešama administratora piekļuve.");
                    }
                }
                case 0 -> { /* Atgriezties */ }
                default -> System.out.println("Nepareiza izvēle.");
            }
            System.out.println("\nNospiediet Enter, lai turpinātu...");
            scanner.nextLine();
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
        boolean errorShown = false;  // mainīgais, lai uzraudzītu, vai kļūda jau tika parādīta
        String line;  // mainīgais, kur glabāsim ievadīto rindu
    
        // …kamēr nav veiksmīgi pārvērsts par skaitli…
        while (true) {
            if (errorShown) {
                // Ja iepriekš bija kļūda, izdzēšam iepriekšējo kļūdas paziņojumu
                System.out.print("\033[F\033[2K"); // uz augšu 1 rindiņu un iztīra to
                System.out.print("\033[F\033[2K"); // uz augšu vēl 1 rindiņu un iztīra to
    
                // Izvada atkārtoto aicinājumu lietotājam
                System.out.print("Lūdzu, ievadiet derīgu skaitli (tikai cipari): ");
            } else {
                // Pirmreizējais aicinājums
                System.out.print(prompt);
            }
    
            // Nolasām lietotāja ievadi
            line = scanner.nextLine();
    
            try {
                // Mēģinam pārvērst par int
                input = Integer.parseInt(line);
                break;  // ja izdevās, iziet no cikla
            } catch (NumberFormatException e) {
                // Ja pārvēršana neizdevās — izvadām attiecīgo kļūdas paziņojumu
                if (!errorShown) {
                    // Pirmais kļūdas paziņojums
                    System.out.println("Nepareiza ievade! Lūdzu, ievadiet skaitli.");
                } else {
                    // Otrs un turpmākie kļūdu paziņojumi
                    System.out.println("Nepareizs skaitlis vai ievads! Lūdzu, ievadiet tikai skaitļus.");
                }
                errorShown = true;  // atzīmējam, ka kļūda jau ir bijusi
            }
        }
    
        return input;  // atgriežam iegūto skaitli
    }
    public static void clearConsole() {
        System.out.print("\033c");
        System.out.flush();
    }
}