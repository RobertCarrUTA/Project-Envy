package com.example.cse3310semesterproject;

import java.util.Date;

public class Expenses {
    public String userID;
    public int priority;
    public double spending;
    public Date creationDate;
    public String email;

    public Expenses(){}

    public Expenses(String userID, int priority, double spending, Date creationDate, String email)
    {
        this.userID = userID;
        this.priority = priority;
        this.spending = spending;
        this.creationDate = creationDate;
        this.email = email;
    }
}
