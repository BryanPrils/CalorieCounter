package model;

import java.util.Objects;

public class Food {
    private String name;
    private String description;
    private int calories;
    private String imagePath = "/home/bryan/Desktop/mobiledev/CalorieCounter/app/src/main/res/drawable/appel.jpeg";


    public Food() {
    }


    public Food(String name, String description, int calories, String imagePath) {
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food)) return false;
        Food food = (Food) o;
        return getCalories() == food.getCalories() &&
                getName().equals(food.getName()) &&
                getDescription().equals(food.getDescription()) &&
                Objects.equals(getImagePath(), food.getImagePath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getCalories(), getImagePath());
    }
}