package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;

public class WeeklyReportActivity extends AppCompatActivity
{

    Button mReturnHomeFromWeeklyReportBtn, signOutFromWeeklyReportBtn;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reff = database.getReference().child("Users").child(uid).child("Expenses");

    // Initializing the graph to be a line graph
    private LineGraphSeries<DataPoint> weekly_series;

    double spending2[] = {0.0, 0.0, 0.0};
    double spending1[] = {0.0, 0.0, 0.0};


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
        //Calendar calendar = Calendar.getInstance();
        //Date d1 = calendar.getTime();
        //calendar.add(Calendar.DATE, 1);
        //Date d2 = calendar.getTime();
        //calendar.add(Calendar.DATE, 1);
        //Date d3 = calendar.getTime();

        reff.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                int i = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Expenses expenses = snapshot.getValue(Expenses.class);
                    spending2[i] = expenses.spending;
                    spending1[i] = expenses.spending;
                    System.out.println(expenses.spending);
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                // Failed to read value
                Log.v("TestRead", "Failed to read value.", databaseError.toException());
            }
        });

        // Also this: https://github.com/jjoe64/GraphView/wiki/Style-options
        weeklyGraph.getGridLabelRenderer().setHorizontalAxisTitle("Date");
        weeklyGraph.getGridLabelRenderer().setVerticalAxisTitle("Expenses ($)");
        weeklyGraph.getGridLabelRenderer().setNumHorizontalLabels(3);

        // Setting graph title:
        weeklyGraph.setTitle("Weekly Expenses");

        // Expenses
        double x, y;

        // I guess we need to discuss how we wish to go about graphing these graphs, weekly as in
        // the weeks of any given month, or just the days of that week? Monthly as in the weeks in
        // a single month or monthly as in every month in a year?
        int weekDays = 7;

        System.out.println("Spending 1:");
        System.out.println(spending1[1]);
        System.out.println("This is before the loop");
        System.out.println(spending2[1]);

        // This for loop added in filler data for now. We need to be able to put user expenses with
        // their dates into this graph
        for(int i = 0; i < 2; i++)
        {
            x = 1.00 * i;
            //if(i % 2 == 0)
            //{
            //    y = (y - 85);
            //}
            y = spending2[i];
            System.out.println("This is in the for loop");
            System.out.println(y);
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