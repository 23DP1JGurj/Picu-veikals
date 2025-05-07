package lv.rvt.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import lv.rvt.Person;
import lv.rvt.Order;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Helper {
    private static final String USER_FILE = "data/users.json";
    private static final String REVIEWS_FILE = "data/reviews.json";
    private static final String ISSUES_FILE = "data/issues.json";
    private static final String ORDER_FILE = "data/orders.json";


    public static ArrayList<Person> loadUsers() {
        try (Reader reader = new FileReader(USER_FILE)) {
            Type userListType = new TypeToken<ArrayList<Person>>() {}.getType();
            ArrayList<Person> users = new Gson().fromJson(reader, userListType);
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }


    public static void saveUsers(ArrayList<Person> users) {
        try (Writer writer = new FileWriter(USER_FILE)) {
            Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.out.println("Kļūda saglabājot lietotājus!");
        }
    }


    public static Person findUser(String username, ArrayList<Person> users) {
        for (Person p : users) {
            if (p.getUsername().equalsIgnoreCase(username)) {
                return p;
            }
        }
        return null;
    }


    public static Person authenticate(String username, String password) {
        ArrayList<Person> users = loadUsers();
        for (Person user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static boolean registerUser(String username, String password, String email, boolean isAdmin) {
        ArrayList<Person> users = loadUsers();
        if (findUser(username, users) != null) {
            return false; 
        }

        Person newUser = new Person(username, password, email, isAdmin);
        users.add(newUser);
        saveUsers(users);
        return true;
    }


    public static ArrayList<String> loadReviews() {
        try (Reader reader = new FileReader(REVIEWS_FILE)) {
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> reviews = new Gson().fromJson(reader, type);
            return reviews != null ? reviews : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

 
    public static void saveReviews(ArrayList<String> reviews) {
        try (Writer writer = new FileWriter(REVIEWS_FILE)) {
            Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();
            gson.toJson(reviews, writer);
        } catch (IOException e) {
            System.out.println("Kļūda saglabājot atsauksmes!");
        }
    }


    public static ArrayList<String> loadIssues() {
        try (Reader reader = new FileReader(ISSUES_FILE)) {
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> issues = new Gson().fromJson(reader, type);
            return issues != null ? issues : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void saveIssues(ArrayList<String> issues) {
        try (Writer writer = new FileWriter(ISSUES_FILE)) {
            Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();
            gson.toJson(issues, writer);
        } catch (IOException e) {
            System.out.println("Kļūda saglabājot problēmas!");
        }
    }


    public static void saveOrder(Order order) {
        Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
        .setPrettyPrinting()
        .create();
        List<Order> orders = loadOrders(); 
        orders.add(order); 
        try (FileWriter writer = new FileWriter(ORDER_FILE)) {
            gson.toJson(orders, writer);
        } catch (IOException e) {
            System.err.println("Neizdevās saglabāt pasūtījumu: " + e.getMessage());
        }
    }


    public static List<Order> loadOrders() {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();
    
        try (Reader reader = new FileReader(ORDER_FILE)) {
            Type listType = new TypeToken<List<Order>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}