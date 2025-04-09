package lv.rvt.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import lv.rvt.Person;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Helper {
    private static final String USER_FILE = "data/users.json";

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
        Person user = findUser(username, users);
        if (user != null && user.validatePassword(password)) {
            return user;
        }
        return null;
    }

    // Reģistrē jaunu lietotāju
    public static boolean registerUser(String username, String password, String email, boolean isAdmin) {
        ArrayList<Person> users = loadUsers();

        // Pārbauda, vai lietotājvārds jau eksistē
        if (findUser(username, users) != null) {
            return false;
        }

        // Izveido jaunu lietotāju un pievieno sarakstam
        Person newUser = new Person(username, password, email, isAdmin);
        users.add(newUser);
        saveUsers(users);
        return true;
    }
}

