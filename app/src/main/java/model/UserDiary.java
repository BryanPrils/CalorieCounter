package model;

public class UserDiary {
    private String email;
    private String date;
    private String foodname;
    private int calories;

    public UserDiary() {
    }

    public UserDiary(String email, String date, String foodname, int calories) {
        this.email = email;
        this.date = date;
        this.foodname = foodname;
        this.calories = calories;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
