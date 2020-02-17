package model;

import java.util.Objects;

public class Food {
    private String name;
    private String image;
    private String description;
    private int calories;

    public Food() {
    }

    public Food(String name, String image, String description, int calories) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food)) return false;
        Food food = (Food) o;
        return getCalories() == food.getCalories() &&
                getName().equals(food.getName()) &&
                Objects.equals(getImage(), food.getImage()) &&
                Objects.equals(getDescription(), food.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getImage(), getDescription(), getCalories());
    }
}
