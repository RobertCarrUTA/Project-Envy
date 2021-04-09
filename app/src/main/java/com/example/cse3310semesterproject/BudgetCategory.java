package com.example.cse3310semesterproject;

public class BudgetCategory {
    public String categoryTitle;
    public String priorityString;
    public int priorityInt;

    public BudgetCategory(){}

    public BudgetCategory(String categoryTitle, String priorityString, int priorityInt)
    {
        this.categoryTitle = categoryTitle;
        this.priorityString = priorityString;
        this.priorityInt = priorityInt;
    }
}
