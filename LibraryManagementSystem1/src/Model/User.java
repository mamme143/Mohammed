package model;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String name;
    private String username;
    private String password; // Store password securely in practice (hashing)
    private String role;

    public User(String id, String name, String username, String password, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password; // Access password
    }

    public String getRole() {
        return role;
    }
}