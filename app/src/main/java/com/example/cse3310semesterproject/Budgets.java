package com.example.cse3310semesterproject;

import java.util.Date;

public class Budgets
{
    public String userID;
    public Date creationDate;
    public double budget;

    public Budgets(){}

    public Budgets(String userID, Date creationDate, double budget)
    {
        this.userID = userID;
        this.creationDate = creationDate;
        this.budget = budget;
    }

}
