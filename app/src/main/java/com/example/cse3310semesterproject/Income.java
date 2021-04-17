package com.example.cse3310semesterproject;

import java.util.Date;

public class Income {
    public String userID;
    public Date createDate;
    public double weeklyIncome;

    public Income(){}

    public Income(String userID, Date createDate, double weeklyIncome)
    {
        this.userID = userID;
        this.createDate = createDate;
        this.weeklyIncome = weeklyIncome;
    }
}
