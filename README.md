# Pica Veikals 🍕 [LV]

```
   ____  _                        _ _         _
  |  _ \(_) ___ _   _  __   _____(_) | ____ _| |___    
  | |_) | |/ __| | | | \ \ / / _ \ | |/ / _` | / __|  
  |  __/| | (__| |_| |  \ V /  __/ |   < (_| | \__ \   
  |_|   |_|\___|\__,_|   \_/ \___|_|_|\_\__,_|_|___/ 
              PizzaShop Console App
```

## 👥 Autori

- **Rodions Poplavskis** <br> [![GitHub](https://img.shields.io/badge/GitHub-%23121011.svg?logo=github&logoColor=white)](https://github.com/23DP1RPopl)
- **Jegors Gurjevs** <br> [![GitHub](https://img.shields.io/badge/GitHub-%23121011.svg?logo=github&logoColor=white)](https://github.com/23DP1JGurj)

## 📝 Apraksts

Pica Veikals ir Java konsoles lietotne, kas simulē picas veikalu ar šādām iespējām:

* **Picu saraksts** ar filtrēšanas, kārtošanas un meklēšanas opcijām (pēc izmēra, cenas, nosaukuma, sastāvdaļām u.c.).
* **Pasūtīšana**: izvēlies picas, norādi izmēru, daudzumu, promokodu, piegādes veidu, automātiska atlaide un kopējā summa.
* **Email apstiprinājums** pasūtījumam (izmantojot Gmail SMTP).
* **Lietotāju profili**: reģistrācija, autentifikācija (parasts lietotājs vs administrators).
* **Pasūtījumu vēsture**: katram lietotājam un administratoram pieejama pasūtījumu arhīvs.
* **Atsauksmes un problēmu ziņojumi** ar laika zīmogu un autoru, glabājas JSON formātā.

### Lomas sistēmā

* **Viesis** (Guest):

  * Apskata picu sarakstu un akcijas.
  * Pasūta picu kā viesis (bez saglabātas vēstures).
  * Atstājas atsauksmes un ziņo par problēmām anonīmi.

* **Reģistrēts lietotājs** (User):

  * Papildus viesja funkcijām — saglabā pasūtījumu vēsturi savā profilā.
  * Pārvalda savus datus, saņem e-pasta apstiprinājumus.
  * Redz un dzēš savas atsauksmes un problēmu ierakstus.

* **Administrators** (Admin):

  * Vēro un pārvalda visus pasūtījumus, atsauksmes un problēmas.
  * Piekļūst lietotāju sarakstam, var dzēst vai modificēt ierakstus.
  * Sazinās ar klientiem un pārrauga sistēmas darbību.
 
    
## ⚙️ Sistēmas prasības

* Java JDK 17 vai jaunāka
* `jakarta.mail` (SMTP klientam)
* `com.google.code.gson` (JSON apstrādei)
* Terminālis ar UTF-8 atbalstu

## 🛠 Instalācija un Palaišana

1. Klonē repozitoriju:

   ```bash
   git clone https://github.com/username/pica-veikals.git
   ```
2. Iej projekta mapē:

   ```bash
   cd pica-veikals
   ```
3. Kompilē un izveido JAR:

   ```bash
   mvn clean package
   ```
4. Palaiž lietotni:

   ```bash
   java -jar target/pica-veikals.jar
   ```

> **Piezīme**: Ja neizmantojat Maven, kompilējiet un palaidiet manuāli, iekļaujot klases ceļā ārējās bibliotēkas (jakarta.mail, gson).

## ⚖️ Projekta struktūra

```text
pica-veikals/
├─ src/main/java/lv/rvt/
│   ├── Main.java
│   ├── PicaVeikalsApp.java
│   ├── Pica.java
│   ├── Order.java
│   ├── Person.java
│   └── ConsoleColors.java
└─ src/main/java/lv/rvt/tools/
    ├── EmailService.java
    ├── Helper.java
    ├── InputHelper.java
    └── LocalDateTimeAdapter.java
```

## 📦 Konfigurācija

* `data/` direktorijā glabājas JSON faili:

  * `users.json` – reģistrētie lietotāji
  * `orders.json` – saglabātie pasūtījumi
  * `reviews.json` – atsauksmes
  * `issues.json` – problēmu ziņojumi
* SMTP dati `EmailService.java` (lietotājvārds, parole un servera iestatījumi).

## 🚀 Lietošana

1. Palaid aplikāciju un izvēlies darbību no galvenā izvēlnes.
2. Apskati picu sarakstu vai noformē jaunu pasūtījumu.
3. Reģistrējies / piesakies, lai saglabātu pasūtījuma vēsturi.
4. Atstāj atsauksmi vai ziņo par problēmu sadaļā "Sazināties ar mums".

## 📩 Kontakti

Ja vēlies ziņot par kļūdām vai iesniegt ieteikumus, raksti uz: **[piceveikals@example.com](mailto:piccaveikalsad@gmail.com)**

Musu mājaslapa https://github.com/23DP1JGurj/Picu-veikals-majaslapa

---
<br><br>

# Pica Shop 🍕 [ENG]

```
   ____  _                        _ _         _
  |  _ \(_) ___ _   _  __   _____(_) | ____ _| |___    
  | |_) | |/ __| | | | \ \ / / _ \ | |/ / _` | / __|  
  |  __/| | (__| |_| |  \ V /  __/ |   < (_| | \__ \   
  |_|   |_|\___|\__,_|   \_/ \___|_|_|\_\__,_|_|___/ 
              PizzaShop Console App
```

## 👥 Authors

- **Rodions Poplavskis** <br> [![GitHub](https://img.shields.io/badge/GitHub-%23121011.svg?logo=github&logoColor=white)](https://github.com/23DP1RPopl)
- **Jegors Gurjevs** <br> [![GitHub](https://img.shields.io/badge/GitHub-%23121011.svg?logo=github&logoColor=white)](https://github.com/23DP1JGurj)

## 📝 Description

Pica Veikals is a Java console application that simulates a pizza shop with the following features:

* **Pizza Menu** with filtering, sorting, and search options (by size, price, name, ingredients, etc.).
* **Ordering**: select pizzas, specify size, quantity, promo code, delivery method, automatic discounts, and final total calculation.
* **Email Confirmation** sent for orders (using Gmail SMTP).
* **User Profiles**: registration and authentication (Guest vs. Registered User vs. Administrator).
* **Order History**: each registered user and administrator can view past orders.
* **Feedback and Issue Reporting** with timestamps and user info, stored in JSON files.

### User Roles

* **Guest**:

  * Browse the pizza menu and special deals.
  * Place orders anonymously (order history not saved).
  * Leave feedback and report issues without an account.

* **Registered User**:

  * All Guest features plus:

    * Save order history under their profile.
    * Manage personal data and receive email confirmations.
    * View and delete own feedback and issue reports.

* **Administrator**:

  * View and manage all orders, feedback, and issue reports.
  * Access the full user list and modify or delete entries.
  * Monitor system activity and communicate with users.

## ⚙️ System Requirements

* Java JDK 17 or newer
* `jakarta.mail` (for SMTP email client)
* `com.google.code.gson` (for JSON processing)
* Terminal supporting UTF-8 encoding

## 🛠 Installation & Running

1. Clone the repository:

   ```bash
   git clone https://github.com/username/pica-veikals.git
   ```
2. Navigate to the project folder:

   ```bash
   cd pica-veikals
   ```
3. Build the project and create a JAR (using Maven):

   ```bash
   mvn clean package
   ```
4. Run the application:

   ```bash
   java -jar target/pica-veikals.jar
   ```

> **Note**: If you are not using Maven, compile and run manually by including external libraries (`jakarta.mail`, `gson`) on the classpath.

## ⚖️ Project Structure

```
pica-veikals/
├─ src/main/java/lv/rvt/
│   ├── Main.java
│   ├── PicaVeikalsApp.java
│   ├── Pica.java
│   ├── Order.java
│   ├── Person.java
│   └── ConsoleColors.java
└─ src/main/java/lv/rvt/tools/
    ├── EmailService.java
    ├── Helper.java
    ├── InputHelper.java
    └── LocalDateTimeAdapter.java
```

## 📦 Configuration

* JSON files are stored in the `data/` directory:

  * `users.json` – registered users
  * `orders.json` – saved orders
  * `reviews.json` – feedback entries
  * `issues.json` – issue reports
* SMTP credentials and settings are configured in `EmailService.java` (username, password, host, port).

## 🚀 Usage

1. Launch the application and choose an option from the main menu.
2. Browse the pizza menu or place a new order.
3. Register or log in to save your order history.
4. Leave feedback or report issues under "Contact Us."

## 📩 Contact

If you encounter bugs or have suggestions, please email: **[piceveikals@example.com](mailto:piccaveikalsad@gmail.com)** 

Our vewsite https://github.com/23DP1JGurj/Picu-veikals-majaslapa

---
