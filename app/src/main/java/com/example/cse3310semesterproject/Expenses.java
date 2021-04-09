package com.example.cse3310semesterproject;

import java.util.Date;

public class Expenses {
    public int priority;
    public double spending;
    public Date startDate;
    public Date endDate;

    public Expenses(){}

    public Expenses(int priority, double spending, Date startDate, Date endDate)
    {
        this.priority = priority;
        this.spending = spending;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
