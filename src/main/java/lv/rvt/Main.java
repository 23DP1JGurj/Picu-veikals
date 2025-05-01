package lv.rvt;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lv.rvt.tools.EmailService;
import lv.rvt.tools.Helper;
import lv.rvt.tools.InputHelper;

public class Main {
    private static final EmailService emailService = new EmailService();
    private static ArrayList<Pica> picuSaraksts = new ArrayList<>();
    private static ArrayList<String> atsauksmes = new ArrayList<>();
    private static ArrayList<String> problemas = new ArrayList<>();
    private static Person loggedInUser = null;

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        inicializetPicuSarakstu();
        atsauksmes = Helper.loadReviews();
        problemas = Helper.loadIssues();
        AsciiDoorAnimation.play();
        int choice;
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
                System.out.println("5 - Sazināties ar mums");
            } else {
                System.out.println("4 - Iziet no profila");
                System.out.println("5 - Sazināties ar mums");
                System.out.println("6 - Mana pasūtījumu vēsture");
            }
            System.out.println("0 - Iziet");


            int maxChoice = (loggedInUser != null) ? 6 : 5;

            choice = InputHelper.getIntInput(
                scanner, 
                "Ievadiet izvēles numuru: ", 
                0, 
                maxChoice
            );

            switch (choice) {
                case 1 -> attelotPicuSarakstu(scanner);
                case 2 -> pasutitPicu(scanner);
                case 3 -> {
                    System.out.println("\nAkcijas:");
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
                case 6 -> {
                    if (loggedInUser != null) {
                        if (loggedInUser.isAdmin()) {
                            showAllOrders();
                        } else {
                            showUserOrders(loggedInUser.getUsername());
                        }
                    }
                }
                case 0 -> System.out.println("Paldies, ka izmantojāt Pica veikalu!");
                default -> System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
            System.out.println("\nNospiediet Enter, lai turpinātu...");
            scanner.nextLine();
        } while (choice != 0);
    }

    private static void loginOrRegister(Scanner scanner) {
        while (true) {
            clearConsole();
            System.out.println("\n1 - Ienākt lietotāja profilā");
            System.out.println("2 - Ienākt administratora profilā");
            System.out.println("3 - Izveidot jaunu profilu");
            System.out.println("0 - Atgriezties");

            int choice = InputHelper.getIntInput(
            scanner, 
            "Ievadiet izvēles numuru: ", 
            0, 
            3
        );

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
                System.out.printf("%-3d | %-20s | %-6s | %6.2f€ | %-30s%n",
                        p.getNr(), p.getNosaukums(), p.getIzmers(), p.getCena(), p.getSastavdalas());
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
            System.out.println("9 - Atiestatīt sarakstu");
            System.out.println("0 - Atgriezties");
    
            choice = InputHelper.getIntInput(
                    scanner,
                    "Ievadiet izvēles numuru: ",
                    0,
                    9
            );
    
            switch (choice) {
                case 1 -> filtretasPicas.sort(Comparator.comparingInt(p -> Integer.parseInt(p.getIzmers().replace(" cm", ""))));
                case 2 -> filtretasPicas.sort((p1, p2) ->
                        Integer.parseInt(p2.getIzmers().replace(" cm", "")) - Integer.parseInt(p1.getIzmers().replace(" cm", "")));
                case 3 -> filtretasPicas.sort(Comparator.comparingDouble(Pica::getCena));
                case 4 -> filtretasPicas.sort((p1, p2) -> Double.compare(p2.getCena(), p1.getCena()));
                case 5 -> {
                    filtretasPicas = new ArrayList<>();
                    for (Pica p : picuSaraksts) {
                        if (p.getSastavdalas().matches("(?i).*(bekons|pepperoni|vistas|desa|šķiņķis).*")) {
                            filtretasPicas.add(p);
                        }
                    }
                }
                case 6 -> {

                    Set<String> uniqueIngredients = new TreeSet<>();
                    for (Pica p : picuSaraksts) {
                        String[] parts = p.getSastavdalas().split(",\\s*");
                        uniqueIngredients.addAll(Arrays.asList(parts));
                    }
    
                    List<String> ingredientList = new ArrayList<>(uniqueIngredients);
                    clearConsole();
                    System.out.println("\n=== INGREDIĒNTI ===");
                    for (int i = 0; i < ingredientList.size(); i++) {
                        System.out.printf("%2d - %s%n", i + 1, ingredientList.get(i));
                    }
    
                    int ingChoice = InputHelper.getIntInput(
                            scanner,
                            "\nIevadiet ingredienta numuru, lai filtrētu picas: ",
                            1,
                            ingredientList.size()
                    );
    
                    String selectedIngredient = ingredientList.get(ingChoice - 1).toLowerCase();
    
                    filtretasPicas = new ArrayList<>();
                    for (Pica p : picuSaraksts) {
                        if (p.getSastavdalas().toLowerCase().contains(selectedIngredient)) {
                            filtretasPicas.add(p);
                        }
                    }
                }
                case 7 -> {
                    filtretasPicas = new ArrayList<>();
                    for (Pica p : picuSaraksts) {
                        String name = p.getNosaukums().toLowerCase();
                        if (name.contains("margarita") || name.contains("pepperoni")
                                || name.contains("sieri") || name.contains("havaju")
                                || name.contains("gavaj") || name.contains("gavay")) {
                            filtretasPicas.add(p);
                        }
                    }
                    filtretasPicas.sort((p1, p2) -> Integer.compare(getPopularitate(p2), getPopularitate(p1)));
                }
                case 8 -> pasutitPicu(scanner);
                case 9 -> filtretasPicas = new ArrayList<>(picuSaraksts);
                case 0 -> {
                    return;
                }
            }
    
        } while (true);
    }
    
    private static int getPopularitate(Pica p) {
        String name = p.getNosaukums().toLowerCase();
        if (name.contains("margarita")) return 4;
        if (name.contains("pepperoni")) return 3;
        if (name.contains("havaju") || name.contains("gavaj") || name.contains("gavay")) return 2;
        if (name.contains("sieri")) return 1;
        return 0;
    }

    private static void pasutitPicu(Scanner scanner) {
        List<String> orderSummaries = new ArrayList<>();
        double totalPrice = 0.0;
        Map<Integer, Integer> pizzaCountMap = new HashMap<>();
    
        while (true) {
            clearConsole();
            System.out.println("=== Pieejamās picas ===");
            for (Pica p : picuSaraksts) {
                System.out.printf("%-3d | %-20s | %-6s | %.2f€%n", p.getNr(), p.getNosaukums(), p.getIzmers(), p.getCena());
            }
            System.out.println();
    
            Pica selectedPizza = null;
            while (selectedPizza == null) {
                System.out.print("Izvēlieties picu pēc numura (izeja/exit - uz sākumu): ");
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.equals("izeja") || input.equals("exit")) return;
    
                int pizzaChoice;
                try {
                    pizzaChoice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Nederīgs ievads. Mēģiniet vēlreiz.");
                    continue;
                }
    
                for (Pica p : picuSaraksts) {
                    if (p.getNr() == pizzaChoice) {
                        selectedPizza = p;
                        break;
                    }
                }
    
                if (selectedPizza == null) {
                    System.out.println("Nepareizs numurs. Mēģiniet vēlreiz.");
                }
            }
    
            int quantity = 0;
            while (quantity == 0) {
                System.out.print("Ievadiet daudzumu (izeja/exit - uz sākumu): ");
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.equals("izeja") || input.equals("exit")) return;
    
                try {
                    int parsed = Integer.parseInt(input);
                    if (parsed >= 1 && parsed <= 100) {
                        quantity = parsed;
                    } else {
                        System.out.println("Lūdzu ievadiet skaitli no 1 līdz 100.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Nederīgs ievads.");
                }
            }
    
            double pizzaTotal = selectedPizza.getCena() * quantity;
            orderSummaries.add(String.format("%dx %s (%s) - %.2f€", quantity, selectedPizza.getNosaukums(), selectedPizza.getIzmers(), pizzaTotal));
            totalPrice += pizzaTotal;
            pizzaCountMap.put(
                selectedPizza.getNr(),
                pizzaCountMap.getOrDefault(selectedPizza.getNr(), 0) + quantity
            );
    
            System.out.print("Vai vēlaties pasūtīt vēl vienu picu? (jā/nē): ");
            String another = scanner.nextLine().trim().toLowerCase();
            if (!(another.contains("j") || another.contains("y"))) break;
        }
    
        System.out.print("\nIevadiet savu e-pastu (nev nepieciešams, izeja/exit - uz sākumu): ");
        String customerEmail = scanner.nextLine().trim();
        if (customerEmail.equals("izeja") || customerEmail.equals("exit")) return;
    
        System.out.print("Vai jums ir promokods? (jā/nē): ");
        String hasPromo = scanner.nextLine().trim().toLowerCase();
        boolean isYes = hasPromo.contains("j") || hasPromo.contains("y");
        double discount = 0.0;
    
        if (isYes) {
            System.out.print("\nIevadiet promokodu (izeja/exit - uz sākumu): ");
            String promoCode = scanner.nextLine().trim().toLowerCase();
            if (promoCode.equals("izeja") || promoCode.equals("exit")) return;
            if (promoCode.equals("atlaide10")) {
                discount = 0.10;
                System.out.println("\nTika piemērota 10% atlaide!");
            } else {
                System.out.println("\nNederīgs promokods.");
            }
        }
    
        System.out.print("\nVai vēlaties piegādi vai paši izņemsiet? (piegāde/savākšana): ");
        String deliveryChoice = scanner.nextLine().trim().toLowerCase();
        if (deliveryChoice.equals("izeja") || deliveryChoice.equals("exit")) return;
    
        boolean isDelivery = deliveryChoice.contains("pieg");
        double deliveryFee = isDelivery ? 2.50 : 0.0;
    

        double akcijasAtlaide = 0.0;
    

        if (pizzaCountMap.containsKey(1) && pizzaCountMap.containsKey(2)) {
            int comboCount = Math.min(pizzaCountMap.get(1), pizzaCountMap.get(2));
            for (Pica p : picuSaraksts) {
                if (p.getNr() == 1 || p.getNr() == 2) {
                    akcijasAtlaide += p.getCena() * 0.20 * comboCount;
                }
            }
        }
    

        if (pizzaCountMap.containsKey(4)) {
            int count = pizzaCountMap.get(4);
            for (Pica p : picuSaraksts) {
                if (p.getNr() == 4) {
                    akcijasAtlaide += p.getCena() * 0.40 * count;
                }
            }
        }
    
        double finalPrice = (totalPrice - akcijasAtlaide) * (1 - discount) + deliveryFee;
    
        StringBuilder orderDetails = new StringBuilder("Jūs pasūtījāt:\n");
        for (String item : orderSummaries) {
            orderDetails.append(" - ").append(item).append("\n");
        }
    
        orderDetails.append(String.format("Kopā: %.2f€\n", totalPrice));
        if (akcijasAtlaide > 0) {
            orderDetails.append(String.format("Akcijas atlaide: -%.2f€\n", akcijasAtlaide));
        }
        if (discount > 0) {
            orderDetails.append(String.format("Promokoda atlaide: -%.2f€\n", (totalPrice - akcijasAtlaide) * discount));
        }
        if (isDelivery) {
            orderDetails.append(String.format("Piegāde: %.2f€\n", deliveryFee));
        }
    
        orderDetails.append(String.format("Gala cena: %.2f€", finalPrice));
    
        boolean emailValid = isValidEmail(customerEmail);
        if (emailValid) {
            emailService.sendOrderConfirmation(customerEmail, orderDetails.toString());
            System.out.println("\nPasūtījums apstiprināts! Apstiprinājums tika nosūtīts uz " + customerEmail);
        } else {
            System.out.println("\nPasūtījums apstiprināts! (E-pasts netika nosūtīts — nav norādīts vai nav derīgs)");
        }
    
        System.out.println("\n" + orderDetails);
        Order order = new Order(
        loggedInUser != null ? loggedInUser.getUsername() : null,
        orderSummaries,
        finalPrice,
        isDelivery ? "Piegāde" : "Savākšana"
        );
        Helper.saveOrder(order);
    }
    
    
    
    
    private static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        // Vienkārša regex validācija
        return email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    }
    
    private static void handleContactUs(Scanner scanner, Person loggedInUser) {
        int subChoice;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
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
            subChoice = InputHelper.getIntInput(scanner, "Ievadiet izvēles numuru: ", 0, 5);
    
            switch (subChoice) {
                case 1 -> {
                    System.out.println("=== Atstāt atsauksmi ===");
                    System.out.print("Jūsu viedoklis: ");
                    String feedback = scanner.nextLine();
                    String timestamp = LocalDateTime.now().format(formatter);
    
                    String userInfo = loggedInUser != null
                            ? " (Lietotājs: " + loggedInUser.getUsername() +
                              (loggedInUser.getEmail() != null && !loggedInUser.getEmail().isEmpty()
                                  ? ", E-pasts: " + loggedInUser.getEmail() : "") + ")"
                            : "";
    
                    atsauksmes.add("[" + timestamp + "] " + feedback + userInfo);
                    Helper.saveReviews(atsauksmes); 
                    System.out.println("Paldies par Jūsu atsauksmi!");
                }
                case 2 -> {
                    System.out.println("=== Ziņot par problēmu ===");
                    System.out.print("Vai ir kāda problēma: ");
                    String issue = scanner.nextLine();
                    String timestamp = LocalDateTime.now().format(formatter);
    
                    String userInfo = loggedInUser != null
                            ? " (Lietotājs: " + loggedInUser.getUsername() +
                              (loggedInUser.getEmail() != null && !loggedInUser.getEmail().isEmpty()
                                  ? ", E-pasts: " + loggedInUser.getEmail() : "") + ")"
                            : "";
    
                    problemas.add("[" + timestamp + "] " + issue + userInfo);
                    Helper.saveIssues(problemas); 
                    System.out.println("Problēma tika reģistrēta. Paldies!");
                }
                case 3 -> {
                    if (loggedInUser != null && loggedInUser.isAdmin()) {
                        System.out.println("=== VISAS ATSAUKSMES ===");
                        if (atsauksmes.isEmpty()) {
                            System.out.println("Nav nevienas atsauksmes.");
                        } else {
                            for (String review : atsauksmes) {
                                System.out.println("- " + review);
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
                            for (String issue : problemas) {
                                System.out.println("- " + issue);
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
            return;
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

    public static int getIntInput(Scanner scanner, String prompt) throws InterruptedException {
        int input = -1;
        boolean firstAttempt = true;
    
        while (true) {
            if (!firstAttempt) {
                clearConsole(); 
                System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
                System.out.println("Nospiediet Enter, lai turpinātu...");
                scanner.nextLine();
            }
    
            clearConsole();
            System.out.print(prompt);
            String line = scanner.nextLine();
    
            try {
                input = Integer.parseInt(line);
                break;
            } catch (NumberFormatException e) {
                firstAttempt = false;
            }
        }
    
        return input;
    }

    private static void showAllOrders() {
        List<Order> orders = Helper.loadOrders();
        if (orders.isEmpty()) {
            System.out.println("Nav neviena pasūtījuma.");
        } else {
            System.out.println("=== Visi pasūtījumi ===");
            for (Order order : orders) {
                System.out.println("\n" + order);
            }
        }
    }

    private static void showUserOrders(String username) {
        List<Order> orders = Helper.loadOrders();
        boolean hasOrders = false;

        System.out.println("=== Jūsu pasūtījumu vēsture ===");
        for (Order order : orders) {
            if (order.getUsername().equals(username)) {
                System.out.println("\n" + order);
                hasOrders = true;
            }
        }

        if (!hasOrders) {
            System.out.println("Jums nav neviena pasūtījuma.");
        }
    }
    public static void clearConsole() {
        System.out.print("\033c");
        System.out.flush();
    }
}