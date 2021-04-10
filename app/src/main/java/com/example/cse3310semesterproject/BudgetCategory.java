package com.example.cse3310semesterproject;

<<<<<<< HEAD
public class BudgetCategory {
=======
public class BudgetCategory { //
    // Should we make these private?
>>>>>>> d1c89a8507f948bf4d6e07bba0f182fa9e634095
    public String userID;
    public String categoryTitle;
    public String priorityString;
    public int priorityInt;

<<<<<<< HEAD
=======
    // We need an empty constructor when using our database
>>>>>>> d1c89a8507f948bf4d6e07bba0f182fa9e634095
    public BudgetCategory(){}

    public BudgetCategory(String userID, String categoryTitle, String priorityString, int priorityInt)
    {
        this.userID = userID;
        this.categoryTitle = categoryTitle;
        this.priorityString = priorityString;
        this.priorityInt = priorityInt;
    }
<<<<<<< HEAD
=======

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

>>>>>>> d1c89a8507f948bf4d6e07bba0f182fa9e634095
}
