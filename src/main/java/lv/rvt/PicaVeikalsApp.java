package lv.rvt;

import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lv.rvt.tools.EmailService;
import lv.rvt.tools.Helper;
import lv.rvt.tools.InputHelper;
import static lv.rvt.ConsoleColors.*;

public class PicaVeikalsApp {
    private static final EmailService emailService = new EmailService();
    private static ArrayList<Pica> picuSaraksts = new ArrayList<>();
    private static ArrayList<String> atsauksmes = new ArrayList<>();
    private static ArrayList<String> problemas = new ArrayList<>();
    private static Person loggedInUser = null;

    public void start() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        inicializetPicuSarakstu();
        atsauksmes = Helper.loadReviews();
        problemas = Helper.loadIssues();
        int choice;
        do {
            clearConsole();
            System.out.println(GREEN + "||                                                              " + LIGHT_BROWN + "         ⢰⣶⣾⣿⣿⣿⣿⣿⣷⣦⡀             " + GREEN + "   ||" + RESET);
            System.out.println(GREEN + "||                                                         " + YELLOW + "             ⣾⠏  " + LIGHT_BROWN + "⠈⠙⢿⣿⣿⣿⣿⣿⣦             " + GREEN + "  ||" + RESET);
            System.out.println(GREEN + "||                                                         " + YELLOW + "          ⡀⣠⠏" + RED+ "⠰⠶⠀   " + LIGHT_BROWN + "⠘⢿⣿⣿⣿⣿⣿⣆           " + GREEN + "   ||" + RESET);
            System.out.println(GREEN + "||  " + ORANGE + "  ____  _                        _ _         _ " + YELLOW + "                ⢀⣼⠏⠀   " + RED+ "⢀⣠⣤⣤⣀" + LIGHT_BROWN + "⠙⣿⣿⣿⣿⣿⣷⡀           " + GREEN + "  ||" + RESET);
            System.out.println(GREEN + "|| " + ORANGE + "  |  _ \\(_) ___ _   _  __   _____(_) | ____ _| |___        " + YELLOW + "    ⢠⠋" + RED+ "⢈⣉⠉" + RED+ "  ⢰⣿⣿⣿⣿⣿⣷⡈ " + LIGHT_BROWN + "⢿⣿⣿⣿⣿⣷⡀           " + GREEN + "||" + RESET);
            System.out.println(GREEN + "|| " + ORANGE + "  | |_) | |/ __| | | | \\ \\ / / _ \\ | |/ / _` | / __|     " + YELLOW + "     ⡴" + RED+ "⢡⣾⣿⣿⣷⠋ ⣿⣿⣿⣿⣿⣿⣿⠃" + LIGHT_BROWN + "⠀⡻⣿⣿⣿⣿⡇          " + GREEN + "  ||" + RESET);
            System.out.println(GREEN + "||  " + ORANGE + " |  __/| | (__| |_| |  \\ V /  __/ |   < (_| | \\__ \\      " + YELLOW + "  ⢀⠜⠁" + RED+ "⠸⣿⣿⣿⠟⠀⠀⠘⠿⣿⣿⣿⡿⠋⠰⠖" + LIGHT_BROWN + "⠱⣽⠟⠋⠉⡇          " + GREEN + "  ||" + RESET);
            System.out.println(GREEN + "||  " + ORANGE + " |_|   |_|\\___|\\__,_|   \\_/ \\___|_|_|\\_\\__,_|_|___/   " + YELLOW + "    ⡰⠉⠖⣀⠀" + RED+ "⠀⢁⣀⠀⣴⣶⣦⠀⢴⡆⠀" + LIGHT_BROWN + "   ⣀⣉⡽⠷⠶⠋⠀         " + GREEN + "   ||" + RESET);
            System.out.println(GREEN + "||                                                  " + YELLOW + "        ⡰" + RED+ "⢡⣾⣿⣿⣿⡄⠛⠋⠘⣿⣿⡿⠀" + YELLOW + "⠀⣐⣲⣤⣯⠞⠉⠁           " + GREEN + "       ||" + RESET);
            System.out.println(GREEN + "||                                                " + YELLOW + "       ⢀⠔⠁" + RED+ "⣿⣿⣿⣿⣿⡟⠀ " + YELLOW + "  ⣄⣀⡞⠉⠉⠉⠉⠁                 " + GREEN + "     ||" + RESET);
            System.out.println(GREEN + "||                                            " + YELLOW + "           ⡜⠀⠀" + RED+ "⠻⣿⣿⠿⣻ " + YELLOW + " ⢠⡟⠉⠉⠀                        " + GREEN + "    ||" + RESET);
            System.out.println(GREEN + "||                                                  " + YELLOW + "    ⠓⡤⠖⠺⢶⡾⠃⠉⠉⠈⠙⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀             " + GREEN + "     ||" + RESET);
            System.out.println(GREEN + "||==================================================================================================||" + RESET);            
            System.out.println(GREEN + "||==================================================================================================||" + RESET);
            System.out.println(GREEN + "||                                                                                                  ||" + RESET);
            if (loggedInUser != null) {
                final int INNER_WIDTH = 98;
        
                String greet = "Sveicināts, " + loggedInUser.getUsername() + "!";
                int leftIndent = 3;  
                int paddingRight = INNER_WIDTH - leftIndent - greet.length();
                if (paddingRight < 0) paddingRight = 0;
        
                String line =
                    GREEN + "||" +
                    " ".repeat(leftIndent) +
                    greet +
                    " ".repeat(paddingRight) +
                    GREEN + "||";
        
                System.out.println(line);
            } else {
                System.out.println(GREEN + "||                                                                                      " + GREEN + "            ||" + RESET);
            }
            System.out.println(GREEN + "|| " + CYAN + "  1 - Picu saraksts                                                   " + GREEN + "                           ||" + RESET);
            System.out.println(GREEN + "|| " + CYAN + "  2 - Pasūtīt picu                                                                     " + GREEN + "          ||" + RESET);
            System.out.println(GREEN + "|| " + CYAN + "  3 - Akcijas                                                                            " + GREEN + "        ||" + RESET);
            
            if (loggedInUser == null) {
                System.out.println(GREEN + "|| " + CYAN + "  4 - Ielogoties profilā                                                              " + GREEN + "           ||" + RESET);
                System.out.println(GREEN + "|| " + CYAN + "  5 - Sazināties ar mums                                                              " + GREEN + "           ||" + RESET);
                System.out.println(GREEN + "|| " + CYAN + "  0 - Iziet                                                                           " + GREEN + "           ||" + RESET);
            } else {
                System.out.println(GREEN + "|| " + CYAN + "  4 - Iziet no profila                                                                " + GREEN + "           ||" + RESET);
                System.out.println(GREEN + "|| " + CYAN + "  5 - Sazināties ar mums                                                              " + GREEN + "           ||" + RESET);
                if (loggedInUser != null && loggedInUser.isAdmin()) {
                    System.out.println(GREEN + "|| " + CYAN + "  6 - Lietotāju pasūtījumu vēsture                                                         " + GREEN + "      ||" + RESET);
                } else {
                    System.out.println(GREEN + "|| " + CYAN + "  6 - Mana pasūtījumu vēsture                                                         " + GREEN + "           ||" + RESET);
                }
                System.out.println(GREEN + "|| " + CYAN + "  0 - Iziet                                                                          " + GREEN + "            ||" + RESET);
            }
            System.out.println(GREEN + "||                                                                                                  ||" + RESET);
            int maxChoice = (loggedInUser != null) ? 6 : 5;

            choice = InputHelper.getIntInput(
                scanner,
                CYAN + "\nIevadiet izvēles numuru: " + RESET,
                0,
                maxChoice
            );

            switch (choice) {
                case 1 -> attelotPicuSarakstu(scanner);
            
                case 2 -> pasutitPicu(scanner);
            
                case 3 -> {
                    System.out.println(CYAN + "\n=== Akcijas ===");
                    System.out.println("1 - Pica nr.1 (20 cm) un nr.2 (20 cm) kopā ar 20% atlaidi");
                    System.out.println("2 - Pica nr.4 (30 cm) ar 40% atlaidi");
                    System.out.println("3 - Ja pasūtāt vairāk nekā 5 picas kopā, piegāde ir bez maksas");
                    System.out.println("4 - Ja pasūtāt 2 vai vairāk vienādas 40 cm picas, saņemat 25% atlaidi uz šīm picām");
                    System.out.println("\nNospiediet Enter, lai turpinātu..." + RESET);
                    scanner.nextLine();
                }
            
                case 4 -> {
                    if (loggedInUser == null) {
                        loginOrRegister(scanner);
                    } else {
                        loggedInUser = null;
                        System.out.println(CYAN + "Jūs esat izrakstījies no profila.");
                    }
                    System.out.println("\nNospiediet Enter, lai turpinātu..." + RESET);
                    scanner.nextLine();
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
                    System.out.println(CYAN + "\nNospiediet Enter, lai turpinātu..." + RESET);
                    scanner.nextLine();
                }
            
                case 0 -> System.out.println(CYAN + "Paldies, ka izmantojāt Pica veikalu!");
            
                default -> System.out.println("Nepareiza ievade, mēģiniet vēlreiz." + RESET);
            }

        } while (choice != 0);
    }

    private static void loginOrRegister(Scanner scanner) {
        while (true) {
            clearConsole();
            System.out.println(GREEN + "Lietotāja profils" + RESET);
            System.out.println(CYAN + "1 - Ienākt lietotāja profilā");
            System.out.println("2 - Ienākt administratora profilā");
            System.out.println("3 - Izveidot jaunu profilu");
            System.out.println("0 - Atgriezties" + RESET);
            int choice = InputHelper.getIntInput(
            scanner, 
            CYAN + "\nIevadiet izvēles numuru: " + RESET, 
            0, 
            3
        );

            switch (choice) {
                case 1, 2 -> {
                    System.out.print(CYAN + "Ievadiet lietotājvārdu: " + RESET);
                    String username = scanner.nextLine();
                    System.out.print(CYAN + "Ievadiet paroli: " + RESET);
                    String password = scanner.nextLine();

                    Person user = Helper.authenticate(username, password);
                    if (user != null) {
                        if ((choice == 1 && !user.isAdmin()) || (choice == 2 && user.isAdmin())) {
                            loggedInUser = user;
                            System.out.println(CYAN + "Laipni lūdzam, " + username + (user.isAdmin() ? " (administrators)!" : "!"));
                            return;
                        } else {
                            System.out.println(CYAN + "Jums nav piekļuves šim profilam.");
                        }
                    } else {
                        System.out.println(CYAN + "Nepareizs lietotājvārds vai parole." + RESET);
                    }
                }
                case 3 -> {
                    System.out.print(CYAN + "Ievadiet jauno lietotājvārdu: " + RESET);
                    String newUsername = scanner.nextLine();
                    System.out.print(CYAN + "Ievadiet jauno paroli: " + RESET);
                    String newPassword = scanner.nextLine();
                    System.out.print(CYAN + "Ievadiet savu e-pastu (nav obligāti): " + RESET);
                    String email = scanner.nextLine();

                    boolean success = Helper.registerUser(newUsername, newPassword, email, false);
                    if (success) {
                        System.out.println(CYAN + "Profils veiksmīgi izveidots!");
                    } else {
                        System.out.println("Šāds lietotājvārds jau pastāv!" + RESET);
                    }
                }
                case 0 -> { return; }
                default -> System.out.println(CYAN + "Nederīgs ievads, mēģiniet vēlreiz." + RESET);
            }

            System.out.println(CYAN + "\nNospiediet Enter, lai turpinātu..." + RESET);
            scanner.nextLine();
        }
    }
   private static void attelotPicuSarakstu(Scanner scanner) {
    List<Pica> filtretasPicas = new ArrayList<>(picuSaraksts);
    int choice;
    boolean isSorted = false;
    Comparator<Pica> currentComparator = null;
    String aktivaisIzmers = null;
    boolean raditSastavdalas = true;

    do {
        clearConsole();
        if (aktivaisIzmers != null) {
            System.out.println(GREEN + "===================== PICU SARAKSTS ===================");
            System.out.printf("| %-40s | %-7s  |\n", "Nosaukums", aktivaisIzmers);
            System.out.println(GREEN + "=======================================================");
        } else {
            System.out.println(GREEN + "============================= PICU SARAKSTS ==============================");
            System.out.printf("| %-40s | %-7s | %-7s |  %-6s |\n", "Nosaukums", "20cm", "30cm", "40cm");
            System.out.println(GREEN + "==========================================================================");
        }
        
        for (Pica p : filtretasPicas) {
            String sastav = p.getSastavdalas();
            if (aktivaisIzmers != null) {
                String cena = ORANGE + String.format("%7.2f€", p.getCena(aktivaisIzmers)) + RESET;
                System.out.printf(
                    GREEN + "| #%2d " + RESET
                    + RED + "%-36s" + RESET
                    + GREEN + " | " + RESET
                    + "%s"       
                    + GREEN + " |" + RESET + "%n",
                    p.getNr(),
                    p.getNosaukums(),
                    cena
                );
            if (raditSastavdalas && sastav != null && !sastav.isBlank()) {
                System.out.println(YELLOW + "  " + sastav + RESET);
            }
            } else {
                String cena20 = ORANGE + String.format("%6.2f€", p.getCena20cm()) + RESET;
                String cena30 = ORANGE + String.format("%6.2f€", p.getCena30cm()) + RESET;
                String cena40 = ORANGE + String.format("%6.2f€", p.getCena40cm()) + RESET;

                System.out.printf(
                    GREEN + "| #%2d " + RESET
                    + RED + "%-36s" + RESET
                    + GREEN + " | " + RESET
                    + "%s"         
                    + GREEN + " | " + RESET
                    + "%s"         
                    + GREEN + " | " + RESET
                    + "%s"         
                    + GREEN + " |" + RESET + "%n",
                    p.getNr(),
                    p.getNosaukums(),
                    cena20,
                    cena30,
                    cena40
                );
                if (raditSastavdalas && sastav != null && !sastav.isBlank()) {
                    System.out.println(YELLOW + "  " + sastav + RESET);
                }
            }
            
        }
        

        System.out.println(CYAN + "\n=== FILTRĒŠANAS/KĀRTOŠANAS IESPĒJAS ===");
        System.out.println("1 - Picas ar 20 cm");
        System.out.println("2 - Picas ar 30 cm");
        System.out.println("3 - Picas ar 40 cm");
        System.out.println("4 - Cena no lielākās uz mazāko");
        System.out.println("5 - Cena no mazākās uz lielāko");
        System.out.println("6 - Gaļas picas");
        System.out.println("7 - Veģetārās picas");
        System.out.println("8 - Populārākās picas");
        System.out.println("9 - Meklēt pēc sastāvdaļas");
        System.out.println("10 - Nosaukums no A līdz Z");
        System.out.println("11 - Nosaukums no Z līdz A");
        System.out.println("12 - Pasūtīt picu");
        System.out.println("13 - Picas bez sastāvdaļām");
        System.out.println("14 - Atiestatīt sarakstu");
        System.out.println("0 - Atgriezties" + RESET);

        choice = InputHelper.getIntInput(scanner, CYAN + "Ievadiet izvēles numuru: " + RESET, 0, 14);

        switch (choice) {
            case 1 -> {
                filtretasPicas = picuSaraksts.stream()
                    .filter(p -> p.getCena20cm() > 0)
                    .collect(Collectors.toList());
                aktivaisIzmers = "20 cm";
            }
            case 2 -> {
                filtretasPicas = picuSaraksts.stream()
                    .filter(p -> p.getCena30cm() > 0)
                    .collect(Collectors.toList());
                aktivaisIzmers = "30 cm";
            }
            case 3 -> {
                filtretasPicas = picuSaraksts.stream()
                    .filter(p -> p.getCena40cm() > 0)
                    .collect(Collectors.toList());
                aktivaisIzmers = "40 cm";
            }
            case 4 -> {
                currentComparator = Comparator.comparingDouble(p -> Math.max(Math.max(p.getCena20cm(), p.getCena30cm()), p.getCena40cm()));
                currentComparator = currentComparator.reversed();
                isSorted = true;
            }
            case 5 -> { 
                currentComparator = Comparator.comparingDouble(p -> {
                    double min = Double.MAX_VALUE;
                    if (p.getCena20cm() > 0) min = Math.min(min, p.getCena20cm());
                    if (p.getCena30cm() > 0) min = Math.min(min, p.getCena30cm());
                    if (p.getCena40cm() > 0) min = Math.min(min, p.getCena40cm());
                    return min;
                });
                isSorted = true;
            }
            case 6 -> filtretasPicas = picuSaraksts.stream()
                    .filter(p -> p.getSastavdalas().matches("(?i).*(bekons|pepperoni|vistas|desa|\u0161ki\u0146\u0137is|salami|tuncis).*"))
                    .collect(Collectors.toList());
            case 7 -> filtretasPicas = picuSaraksts.stream()
                    .filter(p -> !p.getSastavdalas().matches("(?i).*(bekons|pepperoni|vistas|desa|\u0161ki\u0146\u0137is|salami|tuncis).*"))
                    .collect(Collectors.toList());
            case 8 -> filtretasPicas = picuSaraksts.stream()
            .sorted((p1, p2) -> Integer.compare(getPopularitate(p2), getPopularitate(p1)))
            .limit(5)
            .collect(Collectors.toList());
                
            case 9 -> {
                Set<String> uniqueIngredients = new TreeSet<>();
                for (Pica p : picuSaraksts) {
                    String[] parts = p.getSastavdalas().split(",\\s*");
                    uniqueIngredients.addAll(Arrays.asList(parts));
                }
                List<String> ingredientList = new ArrayList<>(uniqueIngredients);
                clearConsole();
                System.out.println(CYAN + "\n=== INGREDIĒNTI ===");
                for (int i = 0; i < ingredientList.size(); i++) {
                    System.out.printf("%2d - %s%n", i + 1, ingredientList.get(i));
                }
                int ingChoice = InputHelper.getIntInput(scanner, "\nIevadiet ingredienta numuru, lai filtrētu picas: " + RESET, 1, ingredientList.size());
                String selectedIngredient = ingredientList.get(ingChoice - 1).toLowerCase();
                filtretasPicas = picuSaraksts.stream()
                        .filter(p -> p.getSastavdalas().toLowerCase().contains(selectedIngredient))
                        .collect(Collectors.toList());
            }
            case 10 -> {
                currentComparator = Comparator.comparing(Pica::getNosaukums, String.CASE_INSENSITIVE_ORDER);
                isSorted = true;
            }
            case 11 -> {
                currentComparator = Comparator.comparing(Pica::getNosaukums, String.CASE_INSENSITIVE_ORDER).reversed();
                isSorted = true;
            }
            case 12 -> pasutitPicu(scanner);
            case 13 -> raditSastavdalas = false;
            case 14 -> {
                filtretasPicas = new ArrayList<>(picuSaraksts);
                currentComparator = null;
                aktivaisIzmers = null;
                isSorted = false;
                raditSastavdalas = true;
            }
            
            case 0 -> {
                return;
            }
        }

        if (isSorted && currentComparator != null) {
            filtretasPicas.sort(currentComparator);
        }

    } while (true);
}
    
private static int getPopularitate(Pica p) {
    List<Order> orders = Helper.loadOrders();
    String targetName = p.getNosaukums().toLowerCase();

    int count = 0;

    for (Order order : orders) {
        for (String item : order.getItems()) {

            String[] parts = item.split("x ");
            if (parts.length < 2) continue;

            try {
                int quantity = Integer.parseInt(parts[0].trim());
                String namePart = parts[1].toLowerCase();

                if (namePart.contains(targetName)) {
                    count += quantity;
                }
            } catch (NumberFormatException e) {
                continue;
            }
        }
    }

    return count;
}


private static void pasutitPicu(Scanner scanner) {
    List<String> orderSummaries = new ArrayList<>();
    double totalPrice = 0.0;
    Map<Integer, Integer> pizzaCountMap = new HashMap<>();
    Map<Integer, String> pizzaSizeMap = new HashMap<>();

    boolean continueOrdering = true;

    while (continueOrdering) {
        clearConsole();
        System.out.println(GREEN + "========================== Pieejamās picas ===============================");
        System.out.printf(GREEN + "| %-40s | %-7s | %-7s | %-7s |\n" + GREEN , "Nosaukums", "20cm", "30cm", "40cm");
        System.out.println(GREEN + "==========================================================================" + RESET);

        for (Pica p : picuSaraksts) {
            String sastav = p.getSastavdalas();
            System.out.printf(GREEN + "| " + GREEN + "#%2d " + RED + "%-36s" + RESET + GREEN + " | " + ORANGE + "%6.2f€" + RESET + GREEN + " | " + ORANGE + "%6.2f€" + RESET + GREEN + " | " + ORANGE + "%6.2f€" + RESET + GREEN + " |\n" + RESET,
                              p.getNr(), p.getNosaukums(), p.getCena20cm(), p.getCena30cm(), p.getCena40cm());
            System.out.println("  " + YELLOW + sastav + RESET);
        }

        Pica selectedPizza = null;
        while (selectedPizza == null) {
            System.out.print(CYAN + "\nIzvēlieties picu pēc numura (vai rakstiet 'exit' lai izietu): " + RESET);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("exit") || input.equals("izeja")) return;
    
            try {
                int pizzaChoice = Integer.parseInt(input);
                selectedPizza = picuSaraksts.stream()
                        .filter(p -> p.getNr() == pizzaChoice)
                        .findFirst()
                        .orElse(null);
                if (selectedPizza == null) {
                    System.out.println(CYAN + "Nepareizs picu numurs." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(CYAN + "Lūdzu, ievadiet derīgu skaitli." + RESET);
        }
    }

    String pizzaSize = "";
    while (pizzaSize.isEmpty()) {
        System.out.print(CYAN + "Izvēlieties izmēru (20, 30, 40 cm): " + RESET);
        String inputSize = scanner.nextLine().trim();
        if (inputSize.matches("20")) pizzaSize = "20 cm";
        else if (inputSize.matches("30")) pizzaSize = "30 cm";
        else if (inputSize.matches("40")) pizzaSize = "40 cm";
        else System.out.println(CYAN + "Nederīgs izmērs. Pieejamie izmēri: 20, 30 vai 40." + RESET);
    }

    double pizzaPrice = selectedPizza.getCena(pizzaSize);

    int quantity = 0;
    while (quantity <= 0) {
        System.out.print(CYAN + "Ievadiet daudzumu (1-100): " + RESET);
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("izeja")) return;
        try {
            quantity = Integer.parseInt(input);
            if (quantity < 1 || quantity > 100) {
                System.out.println(CYAN + "Skaitlis ārpus robežām (1-100)." + RESET);
                quantity = 0;
            }
        } catch (NumberFormatException e) {
            System.out.println(CYAN + "Nederīgs skaitlis." + RESET);
        }
    }

        double pizzaTotal = pizzaPrice * quantity;
        orderSummaries.add(String.format("%dx %s (%s) - %.2f€", quantity, selectedPizza.getNosaukums(), pizzaSize, pizzaTotal));
        totalPrice += pizzaTotal;
        pizzaCountMap.put(selectedPizza.getNr(), pizzaCountMap.getOrDefault(selectedPizza.getNr(), 0) + quantity);
        pizzaSizeMap.put(selectedPizza.getNr(), pizzaSize);

        while (true) {
            System.out.print(CYAN + "Vai vēlaties pasūtīt vēl kādu picu? (jā/nē): " + RESET);
            String continueOrder = scanner.nextLine().trim().toLowerCase();
            if (continueOrder.equals("jā") || continueOrder.equals("ja") || continueOrder.equals("j") || continueOrder.equals("yes") || continueOrder.equals("y")) {
                break; 
            } else if (continueOrder.equals("nē") || continueOrder.equals("ne") || continueOrder.equals("n") || continueOrder.equals("no")) {
                continueOrdering = false; 
                break;
            } else {
                System.out.println(CYAN + "Nederīga atbilde. Lūdzu, ievadiet 'jā' vai 'nē'.");
            }
        }
    }

    System.out.print(CYAN + "Ievadiet e-pastu (var izlaist, nospiežot Enter): " + RESET);
    String customerEmail = scanner.nextLine().trim();
    boolean sendEmail = false;
    if (!customerEmail.isEmpty()) {
        if (isValidEmail(customerEmail)) {
            sendEmail = true;
        } else {
            System.out.println(CYAN + "Nederīgs e-pasts. Apstiprinājums netiks nosūtīts.");
        }
    }

    boolean hasPromo = false;
    while (true) {
        System.out.print(CYAN + "Vai jums ir promokods? (jā/nē): " + RESET);
        String promoInput = scanner.nextLine().trim().toLowerCase();
        if (promoInput.matches("jā|ja|y|yes")) {
            hasPromo = true;
            break;
        } else if (promoInput.matches("nē|ne|n|no")) {
            break;
        } else {
            System.out.println(CYAN + "Lūdzu, atbildiet ar 'jā' vai 'nē'." + RESET);
        }
    }

    double discount = 0.0;
    if (hasPromo) {
        System.out.print(CYAN + "Ievadiet promokodu: " + RESET);
        String promoCode = scanner.nextLine().trim().toLowerCase();
        if (promoCode.equals("atlaide10")) {
            discount = 0.10;
            System.out.println(CYAN + "Promokods derīgs: 10% atlaide piemērota.");
        } else {
            System.out.println(CYAN + "Nederīgs promokods.");
        }
    }

    boolean isDelivery = false;
    while (true) {
        System.out.print(CYAN + "Izvēlieties piegādes veidu ('piegāde' vai 'pašizvešana'): " + RESET);
        String deliveryInput = scanner.nextLine().trim().toLowerCase();
        if (deliveryInput.equals("piegāde")) {
            isDelivery = true;
            break;
        } else if (deliveryInput.equals("pašizvešana")) {
            break;
        } else {
            System.out.println(CYAN + "Lūdzu, ievadiet tikai 'piegāde' vai 'pašizvešana'." + RESET);
        }
    }

    double deliveryFee = isDelivery ? 2.50 : 0.0;

    // Akcijas atlaides
    double akcijasAtlaide = 0.0;
    if (pizzaCountMap.containsKey(1) && pizzaCountMap.containsKey(2)) {
        int combo = Math.min(pizzaCountMap.get(1), pizzaCountMap.get(2));
        akcijasAtlaide += picuSaraksts.get(0).getCena("20 cm") * 0.20 * combo;
    }

    if (pizzaCountMap.containsKey(4) && "30 cm".equals(pizzaSizeMap.get(4))) {
        akcijasAtlaide += picuSaraksts.get(3).getCena("30 cm") * 0.40 * pizzaCountMap.get(4);
    }

    int totalPizzas = pizzaCountMap.values().stream().mapToInt(Integer::intValue).sum();
    if (totalPizzas > 5) deliveryFee = 0.0;

    double lielaPicaAtlaide = 0.0;
    for (Pica p : picuSaraksts) {
        int count = pizzaCountMap.getOrDefault(p.getNr(), 0);
        if ("40 cm".equals(pizzaSizeMap.get(p.getNr())) && count >= 2) {
            lielaPicaAtlaide += p.getCena("40 cm") * 0.25 * count;
        }
    }

    double finalPrice = (totalPrice - akcijasAtlaide - lielaPicaAtlaide) * (1 - discount) + deliveryFee;

    totalPrice = Math.round(totalPrice * 100.0) / 100.0;
    finalPrice = Math.round(finalPrice * 100.0) / 100.0;

    StringBuilder orderDetails = new StringBuilder("Jūs pasūtījāt:\n");
    for (String item : orderSummaries) {
        orderDetails.append(" - ").append(item).append("\n");
    }
    orderDetails.append(String.format("Kopā: %.2f€\n", totalPrice));
    if (akcijasAtlaide > 0) {
        orderDetails.append(String.format("Akcijas atlaide: -%.2f€\n", akcijasAtlaide));
    }
    if (lielaPicaAtlaide > 0) {
        orderDetails.append(String.format("40 cm picas atlaide: -%.2f€\n", lielaPicaAtlaide));
    }
    if (discount > 0) {
        double promoDiscount = (totalPrice - akcijasAtlaide - lielaPicaAtlaide) * discount;
        orderDetails.append(String.format("Promokoda atlaide: -%.2f€\n", promoDiscount));
    }
    orderDetails.append(deliveryFee > 0 ? String.format("Piegāde: %.2f€\n", deliveryFee) : "Piegāde: bez maksas\n");
    orderDetails.append(String.format("Gala cena: %.2f€", finalPrice));

    if (sendEmail) {
        emailService.sendOrderConfirmation(customerEmail, orderDetails.toString());
        System.out.println(CYAN + "Pasūtījums apstiprināts! Apstiprinājums nosūtīts uz " + customerEmail + RESET);
    } else {
        System.out.println(CYAN + "Pasūtījums apstiprināts! (bez e-pasta apstiprinājuma)" + RESET);
    }

    System.out.println(CYAN + "\n" + orderDetails + RESET);
    Order order = new Order(loggedInUser != null ? loggedInUser.getUsername() : null, orderSummaries, finalPrice, isDelivery ? "Piegāde" : "Pašizvešana");
    Helper.saveOrder(order);

    System.out.println(CYAN + "\nNospiediet Enter, lai turpinātu..." + RESET);
    scanner.nextLine();
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
            System.out.println(GREEN + "=== Sazināties ar mums ===" + RESET);
            System.out.println(CYAN + "1 - Atstāt atsauksmi");
            System.out.println("2 - Ziņot par problēmu");
            if (loggedInUser != null && loggedInUser.isAdmin()) {
                System.out.println("3 - Skatīt visas atsauksmes");
                System.out.println("4 - Skatīt visas problēmas");
            }
            System.out.println("0 - Atgriezties");
            System.out.print("Izvēlieties: " + RESET);
            subChoice = InputHelper.getIntInput(scanner, CYAN + "Ievadiet izvēles numuru: " + RESET, 0, 5);
    
            switch (subChoice) {
                case 1 -> {
                    System.out.println(CYAN + "=== Atstāt atsauksmi ===");
                    System.out.print("Jūsu viedoklis: " + RESET);
                    String feedback = scanner.nextLine();
                    String timestamp = LocalDateTime.now().format(formatter);
    
                    String userInfo = loggedInUser != null
                            ? " (Lietotājs: " + loggedInUser.getUsername() +
                              (loggedInUser.getEmail() != null && !loggedInUser.getEmail().isEmpty()
                                  ? ", E-pasts: " + loggedInUser.getEmail() : "") + ")"
                            : "";
    
                    atsauksmes.add("[" + timestamp + "] " + feedback + userInfo);
                    Helper.saveReviews(atsauksmes); 
                    System.out.println(CYAN + "Paldies par Jūsu atsauksmi!" + RESET);
                }
                case 2 -> {
                    System.out.println(CYAN + "=== Ziņot par problēmu ===");
                    System.out.print("Vai ir kāda problēma: " + RESET);
                    String issue = scanner.nextLine();
                    String timestamp = LocalDateTime.now().format(formatter);
    
                    String userInfo = loggedInUser != null
                            ? " (Lietotājs: " + loggedInUser.getUsername() +
                              (loggedInUser.getEmail() != null && !loggedInUser.getEmail().isEmpty()
                                  ? ", E-pasts: " + loggedInUser.getEmail() : "") + ")"
                            : "";
    
                    problemas.add("[" + timestamp + "] " + issue + userInfo);
                    Helper.saveIssues(problemas); 
                    System.out.println(CYAN + "Problēma tika reģistrēta. Paldies!");
                }
                case 3 -> {
                    if (loggedInUser != null && loggedInUser.isAdmin()) {
                        System.out.println(CYAN + "=== VISAS ATSAUKSMES ===");
                        if (atsauksmes.isEmpty()) {
                            System.out.println("Nav nevienas atsauksmes." + RESET);
                        } else {
                            for (String review : atsauksmes) {
                                System.out.println("- " + review);
                            }
                        }
                    } else {
                        System.out.println(CYAN + "Nepieciešama administratora piekļuve.");
                    }
                }
                case 4 -> {
                    if (loggedInUser != null && loggedInUser.isAdmin()) {
                        System.out.println(CYAN + "=== VISAS PROBLĒMAS ===");
                        if (problemas.isEmpty()) {
                            System.out.println("Nav reģistrētu problēmu." + RESET);
                        } else {
                            for (String issue : problemas) {
                                System.out.println("- " + issue);
                            }
                        }
                    } else {
                        System.out.println(CYAN + "Nepieciešama administratora piekļuve.");
                    }
                }
                case 0 -> { /* Atgriezties */ }
                default -> System.out.println("Nepareiza izvēle.");
            }
    
            System.out.println(CYAN + "\nNospiediet Enter, lai turpinātu..." + RESET);
            scanner.nextLine();
            return;
        } while (subChoice != 0);
    }
    

    private static void inicializetPicuSarakstu() {
        picuSaraksts.add(new Pica(1, "Margarita", 7.99, 10.49, 13.49, "tomātu mērce, mocarella, baziliks, olivelļa"));
        picuSaraksts.add(new Pica(2, "Pepperoni", 9.49, 12.99, 16.49, "tomātu mērce, mocarella, pepperoni, oregano"));
        picuSaraksts.add(new Pica(3, "Havaju", 9.99, 13.49, 17.49, "tomātu mērce, mocarella, škiņķis, ananāsi"));
        picuSaraksts.add(new Pica(4, "Gaļas", 10.49, 14.95, 19.49, "tomātu mērce, mocarella, bekons, desa"));
        picuSaraksts.add(new Pica(5, "Četri sieri", 10.99, 13.99, 17.99, "mocarella, parmezāns, dorblu siers, gouda"));
        picuSaraksts.add(new Pica(6, "BBQ", 11.49, 14.45, 18.99, "BBQ mērce, mocarella, vistas gaļa, sīpols"));
        picuSaraksts.add(new Pica(7, "Veģetārā", 8.49, 11.49, 14.49, "tomātu mērce, mocarella, paprika, šampinjoni"));
        picuSaraksts.add(new Pica(8, "Cēzara", 10.49, 13.99, 17.99, "cēzara mērce, vistas gaļa, kirštomāti, mocarella"));
        picuSaraksts.add(new Pica(9, "Āsais Meksikānis", 9.99, 13.49, 17.49, "tomātu mērce, jalapeno, vistas gaļa, sīpols"));
        picuSaraksts.add(new Pica(10, "Ar šķiņķi un sēnēm", 8.99, 11.99, 14.99, "tomātu mērce, škiņķis, šampinjoni, mocarella"));
        picuSaraksts.add(new Pica(11, "Lauku", 10.49, 13.99, 17.99, "krējuma mērce, kartupeļi, bekons, sīpols"));
        picuSaraksts.add(new Pica(12, "Jūras velšu", 12.49, 16.49, 21.49, "krēmvelda mērce, garneles, kalmāri, mocarella"));
        picuSaraksts.add(new Pica(13, "Salami", 9.49, 12.49, 15.99, "tomātu mērce, mocarella, salami, olivas"));
        picuSaraksts.add(new Pica(14, "Grieķu", 8.49, 10.99, 13.99, "tomātu mērce, feta siers, olivas, tomāti"));
        picuSaraksts.add(new Pica(15, "Carbonara", 10.99, 14.49, 18.49, "krēmvelda mērce, bekons, parmezāns, sīpols"));
        picuSaraksts.add(new Pica(16, "Firmas", 11.49, 14.99, 19.49, "tomātu mērce, vistas gaļa, bekons, šampinjoni"));
        picuSaraksts.add(new Pica(17, "Ar tunci", 9.49, 12.49, 15.99, "tomātu mērce, tuncis, sīpoli, mocarella"));
        picuSaraksts.add(new Pica(18, "Rančo", 11.49, 14.99, 18.99, "rančo mērce, vistas gaļa, bekons, čedara siers"));
        picuSaraksts.add(new Pica(19, "Itāļu", 10.99, 14.99, 18.99, "tomātu mērce, mocarella, prosciutto, rukola"));
        picuSaraksts.add(new Pica(20, "Ar trifelēm", 12.99, 17.49, 21.99, "krēmvelda mērce, trifelu eļļa, šampinjoni, mocarella"));
    }
    



    public static int getIntInput(Scanner scanner, String prompt) throws InterruptedException {
        int input = -1;
        boolean firstAttempt = true;
    
        while (true) {
            if (!firstAttempt) {
                clearConsole(); 
                System.out.println(CYAN + "Nepareiza ievade, mēģiniet vēlreiz.");
                System.out.println("Nospiediet Enter, lai turpinātu..." + RESET);
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
            System.out.println(CYAN + "Nav neviena pasūtījuma.");
        } else {
            System.out.println(CYAN + "=== Visi pasūtījumi ===");
            for (Order order : orders) {
                System.out.println("\n" + order);
            }
        }
    }

    private static void showUserOrders(String username) {
        List<Order> orders = Helper.loadOrders();
        boolean hasOrders = false;
    
        System.out.println(CYAN + "=== Jūsu pasūtījumu vēsture ===");
        for (Order order : orders) {
            if (order.getUsername() != null && order.getUsername().equals(username)) {
                System.out.println("\n" + order);
                hasOrders = true;
            }
        }
    
        if (!hasOrders) {
            System.out.println("Jums nav neviena pasūtījuma." + RESET);
        }
    }
    
    public static void clearConsole() {
        System.out.print("\033c");
        System.out.flush();
    }
}

