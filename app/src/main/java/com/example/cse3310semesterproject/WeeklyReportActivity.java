package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class WeeklyReportActivity extends AppCompatActivity
{

    Button mReturnHomeFromWeeklyReportBtn, signOutFromWeeklyReportBtn;

    // Initializing the graph to be a line graph
    private LineGraphSeries<DataPoint> weekly_series;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_report);

        mReturnHomeFromWeeklyReportBtn = findViewById(R.id.returnHomeFromWeeklyReportBtn);
        signOutFromWeeklyReportBtn = findViewById(R.id.signOutFromWeeklyReportBtn);


        //------------------------------------------------------------------------------------------
        // Graphing Section
        //
        //
        // Graph documentation here: https://github.com/jjoe64/GraphView/wiki/Download-and-Getting-Started

        GraphView weeklyGraph = (GraphView)findViewById(R.id.WeeklyReportGraph);
        weekly_series = new LineGraphSeries<>();

        // Possibly look at this for date axis: https://github.com/jjoe64/GraphView/wiki/Dates-as-labels
        // Also this: https://github.com/jjoe64/GraphView/wiki/Style-options
        weeklyGraph.getGridLabelRenderer().setHorizontalAxisTitle("Date");
        weeklyGraph.getGridLabelRenderer().setVerticalAxisTitle("Expenses ($)");

        // Setting graph title:
        weeklyGraph.setTitle("Weekly Expenses");

        double x = 0, y = 0;

        // I guess we need to discuss how we wish to go about graphing these graphs, weekly as in
        // the weeks of any given month, or just the days of that week? Monthly as in the weeks in
        // a single month or monthly as in every month in a year?
        int weekDays = 7;

        // This for loop added in filler data for now. We need to be able to put user expenses with
        // their dates into this graph
        for(int i = 0; i < weekDays; i++)
        {
            x = i + 1;
            if(i % 2 == 0)
            {
                y = (y - 85);
            }
            y = y + 100;
            weekly_series.appendData(new DataPoint(x,y), true, 10);
        }

        weeklyGraph.addSeries(weekly_series);


        //------------------------------------------------------------------------------------------
        // Let the user go back to the homepage
        mReturnHomeFromWeeklyReportBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        //------------------------------------------------------------------------------------------
        // Let the user sign out
        signOutFromWeeklyReportBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // The toast is what makes the message pop up when the user signs out
                Toast.makeText(WeeklyReportActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut(); //firebase command to delete token created for account locally
                startActivity(new Intent(getApplicationContext(), Login.class)); //back to login screen
            }
        });
    }
}