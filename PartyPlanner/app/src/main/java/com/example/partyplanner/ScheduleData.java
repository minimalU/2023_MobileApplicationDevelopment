// FILE         : ScheduleData.java
// PROJECT      : Party Planner
// PROGRAMMER(s): Beunard Lecaj, Jainish Patel, Raj Dudhat, Yujung Park
// FIRST VERSION: 2023-03-10
// DESCRIPTION  : This ScheduleData class file has constructor, getter, setter for ScheduleData class.
// REFERENCE    : 7_sql_database
// https://www.youtube.com/watch?v=toDZ9X5uSXw&list=PLzkhjlqMgxvBxi3Wyak9NicQI7UwhFU2O&index=89
// https://www.youtube.com/watch?v=312RhjfetP8


package com.example.partyplanner;

public class ScheduleData {
    private int id;
    private String date;
    private String time;
    private String theme;
    private float budget;
    private int guest;


    // constructors
    public ScheduleData(int id, String date, String time, String theme, float budget, int guest) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.theme = theme;
        this.budget = budget;
        this.guest = guest;
    }

    public ScheduleData() {
    }
    //


    @Override
    public String toString() {
        return  "Party ID: " + id +
                "\nDate: " + date + ", time: " + time +
                "\nTheme: " + theme +
                "\nBudget: " + budget +
                "\nNo of Guest: " + guest;
    }

    // getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public int getGuest() {
        return guest;
    }

    public void setGuest(int guest) {
        this.guest = guest;
    }


}
