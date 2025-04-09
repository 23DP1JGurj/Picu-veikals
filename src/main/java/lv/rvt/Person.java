package lv.rvt;

public class Person {
    private String username;
    private String password;
    private boolean isAdmin;
    private String email; 

    public Person(String username, String password, String email, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    // Геттеры
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isAdmin() { return isAdmin; }
    public String getEmail() { return email; }

    // Сеттеры
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }
    public void setEmail(String email) { this.email = email; }

    // Валидация пароля
    public boolean validatePassword(String inputPassword) {
        return password.equals(inputPassword);
    }
}
