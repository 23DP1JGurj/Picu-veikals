package lv.rvt.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.*;
import lv.rvt.Person;
import java.util.ArrayList;
import java.util.List;

public class Helper {
    private static final String USERS_FILE = "data/users.csv";

    public static List<Person> loadUsersFromCSV() {
        List<Person> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    boolean isAdmin = Boolean.parseBoolean(parts[2]);
                    users.add(new Person(username, password, isAdmin));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    public static void saveUsersToCSV(List<Person> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (Person user : users) {
                bw.write(user.getUsername() + "," + user.getPassword() + "," + user.isAdmin());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public static void addUserToCSV(Person user) {
        List<Person> users = loadUsersFromCSV();
        users.add(user);
        saveUsersToCSV(users);
    }

    public static Person findUserInCSV(String username) {
        List<Person> users = loadUsersFromCSV();
        for (Person user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}