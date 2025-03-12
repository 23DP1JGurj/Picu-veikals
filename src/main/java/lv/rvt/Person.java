package lv.rvt;

import java.util.*;

class Person {
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

    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}