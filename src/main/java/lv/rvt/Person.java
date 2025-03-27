package lv.rvt;

import java.util.*;

public class Person {
    private String username;
    private String password;
    private boolean isAdmin;

    public Person(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}