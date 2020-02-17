package com.example.caloriecounter.model;

public class User {
    private String username;
    private String password;
    private String email;
    private double weight;

    public User(String username, String password, String email,
                double weight){
        this.username = username;
        this.password = password;
        this.email = email;
        this.weight = weight;
    }

    public User(){
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
