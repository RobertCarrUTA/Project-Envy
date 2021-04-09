package com.example.cse3310semesterproject;

import java.util.Date;

public class subBudget {
    public int priority;
    public double budgetPiece;
    public Date startDate;
    public Date endDate;

    public subBudget(){}

    public subBudget(int priority, double budgetPiece, Date startDate, Date endDate)
    {
        this.priority = priority;
        this.budgetPiece = budgetPiece;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
