package com.example.cse3310semesterproject;

import java.util.Date;

public class Budgets {
    public String email;
    public Date startDate;
    public Date endDate;
    public double budget;

    public Budgets(){}

    public Budgets(String email, Date startDate, Date endDate, double budget)
    {
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
    }

}
