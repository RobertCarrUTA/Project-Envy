package com.example.cse3310semesterproject;

import java.util.Date;

public class subBudget {
    public int userID;
    public int priority;
    public double budgetPiece;
    public Date creationDate;

    public subBudget(){}

    public subBudget(int userID, int priority, double budgetPiece, Date creationDate)
    {
        this.userID = userID;
        this.priority = priority;
        this.budgetPiece = budgetPiece;
        this.creationDate = creationDate;
    }
}
