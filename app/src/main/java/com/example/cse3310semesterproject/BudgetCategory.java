package com.example.cse3310semesterproject;

public class BudgetCategory {
    public String userID;
    public String categoryTitle;
    public String priorityString;
    public int priorityInt;

    public BudgetCategory(){}

    public BudgetCategory(String userID, String categoryTitle, String priorityString, int priorityInt)
    {
        this.userID = userID;
        this.categoryTitle = categoryTitle;
        this.priorityString = priorityString;
        this.priorityInt = priorityInt;
    }
}
