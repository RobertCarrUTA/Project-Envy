package com.example.cse3310semesterproject;

public class BudgetCategory { //
    // Should we make these private?
    public String userID;
    public String categoryTitle;
    public String priorityString;
    public int priorityInt;

    // We need an empty constructor when using our database
    public BudgetCategory(){}

    public BudgetCategory(String userID, String categoryTitle, String priorityString, int priorityInt)
    {
        this.userID = userID;
        this.categoryTitle = categoryTitle;
        this.priorityString = priorityString;
        this.priorityInt = priorityInt;
    }

    ///////////////////////////////////////////////////////
    // We need getters and setters for our data
    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }


    ///////////////////////////////////////////////////////
    public String getCategoryTitle()
    {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle)
    {
        this.categoryTitle = categoryTitle;
    }

    ///////////////////////////////////////////////////////
    public String getPriorityString()
    {
        return priorityString;
    }

    public void setPriorityString(String priorityString)
    {
        this.priorityString = priorityString;
    }

    ///////////////////////////////////////////////////////
    public int getPriorityInt()
    {
        return priorityInt;
    }

    public void setPriorityString(int priorityInt)
    {
        this.priorityInt = priorityInt;
    }

}
