package lv.rvt.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import lv.rvt.Person;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Helper {
    private static final String USER_FILE = "data/users.json";
    private static final String REVIEWS_FILE = "data/reviews.json";
    private static final String ISSUES_FILE = "data/issues.json";
    
    
    // Ielādē lietotājus no JSON faila
    public static ArrayList<Person> loadUsers() {
        try (Reader reader = new FileReader(USER_FILE)) {
            Type userListType = new TypeToken<ArrayList<Person>>() {}.getType();
            ArrayList<Person> users = new Gson().fromJson(reader, userListType);
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    // Saglabā lietotājus JSON failā
    public static void saveUsers(ArrayList<Person> users) {
    try (Writer writer = new FileWriter(USER_FILE)) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(users, writer);
    } catch (IOException e) {
        System.out.println("Kļūda saglabājot lietotājus!");
    }
    }


    // Atrod lietotāju pēc lietotājvārda
    public static Person findUser(String username, ArrayList<Person> users) {
        for (Person p : users) {
            if (p.getUsername().equalsIgnoreCase(username)) {
                return p;
            }
        }
        return null;
    }

    // Autentificē lietotāju
    public static Person authenticate(String username, String password) {
        ArrayList<Person> users = loadUsers();
        for (Person user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    
    // Reģistrē jaunu lietotāju
    public static boolean registerUser(String username, String password, String email, boolean isAdmin) {
        ArrayList<Person> users = loadUsers();
        if (findUser(username, users) != null) {
            return false; // Lietotājvārds jau aizņemts
        }
    
        Person newUser = new Person(username, password, email, isAdmin);
        users.add(newUser);
        saveUsers(users);
        return true;
    }
    
    // Ielādē atsauksmes
    public static ArrayList<String> loadReviews() {
        try (Reader reader = new FileReader(REVIEWS_FILE)) {
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> reviews = new Gson().fromJson(reader, type);
            return reviews != null ? reviews : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    
    // Saglabā atsauksmes
    public static void saveReviews(ArrayList<String> reviews) {
        try (Writer writer = new FileWriter(REVIEWS_FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(reviews, writer);
        } catch (IOException e) {
            System.out.println("Kļūda saglabājot atsauksmes!");
        }
    }
    
    // Ielādē problēmas
    public static ArrayList<String> loadIssues() {
        try (Reader reader = new FileReader(ISSUES_FILE)) {
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> issues = new Gson().fromJson(reader, type);
            return issues != null ? issues : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    
    // Saglabā problēmas
    public static void saveIssues(ArrayList<String> issues) {
        try (Writer writer = new FileWriter(ISSUES_FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(issues, writer);
        } catch (IOException e) {
            System.out.println("Kļūda saglabājot problēmas!");
        }
    }
}

