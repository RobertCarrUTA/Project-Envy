package com.example.cse3310semesterproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class WeeklyReportActivity extends AppCompatActivity
{
    //LocalDate currDate = LocalDate.now();
    //LocalDate weekAgoDate = currDate.minusDays(7);
    //LocalDate monthAgoDate = currDate.minusDays(30);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
    //Date myDate = dateFormat.parse();


    Button mReturnHomeFromWeeklyReportBtn, signOutFromWeeklyReportBtn;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reff = database.getReference().child("Users").child(uid).child("Expenses");

    // Initializing the graph to be a line graph
    private LineGraphSeries<DataPoint> weekly_series;

    // 7 slots for 7 days out of the week
    double spending2[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

    // Expenses
    double x, y;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_report);

        mReturnHomeFromWeeklyReportBtn = findViewById(R.id.returnHomeFromWeeklyReportBtn);
        signOutFromWeeklyReportBtn = findViewById(R.id.signOutFromWeeklyReportBtn);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Calendar calendar = Calendar.getInstance();
        Date currDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date weekAgoDate = calendar.getTime();
        List<Expenses> expensesList = new ArrayList<Expenses>();

        //------------------------------------------------------------------------------------------
        // Graphing Section
        //
        //
        // Graph documentation here: https://github.com/jjoe64/GraphView/wiki/Download-and-Getting-Started

        // Possibly look at this for date axis: https://github.com/jjoe64/GraphView/wiki/Dates-as-labels
        //Calendar calendar = Calendar.getInstance();
        //Date d1 = calendar.getTime();
        //calendar.add(Calendar.DATE, 1);
        //Date d2 = calendar.getTime();
        //calendar.add(Calendar.DATE, 1);
        //Date d3 = calendar.getTime();


        //------------------------------------------------------------------------------------------
        // We had to add the graph into this scope below or it would not save the values
        // correctly into the array to be graphed
        reff.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                GraphView weeklyGraph = (GraphView)findViewById(R.id.WeeklyReportGraph);
                weekly_series = new LineGraphSeries<>();

                // Setting the axis titles:
                // Possibly look at this for date axis: https://github.com/jjoe64/GraphView/wiki/Dates-as-labels
                // Also this: https://github.com/jjoe64/GraphView/wiki/Style-options
                weeklyGraph.getGridLabelRenderer().setHorizontalAxisTitle("Date");
                weeklyGraph.getGridLabelRenderer().setVerticalAxisTitle("Expenses ($)");
                // Setting how many values are on the x axis:
                weeklyGraph.getGridLabelRenderer().setNumHorizontalLabels(7);
                // Setting graph title:
                weeklyGraph.setTitle("Weekly Expenses");

                DateFormat format = new SimpleDateFormat("M-dd");
                String[] days = new String[7];

                int i = 0;

                // Iterating through the database for the Expenses
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Expenses expenses = snapshot.getValue(Expenses.class);
                    if(expenses.creationDate.compareTo(currDate) <= 0 && expenses.creationDate.compareTo(weekAgoDate) >= 0)
                    {
                            expensesList.add(expenses);
                            spending2[i] = expenses.spending;
                            System.out.println(spending2[i]);
                            i++;
                            System.out.println(expenses.creationDate);
                        System.out.println(expenses.creationDate.compareTo(currDate));
                        System.out.println(expenses.creationDate.compareTo(weekAgoDate));
                    }

                }

                Date dateCount = weekAgoDate;
                int test = calendar.get(Calendar.DAY_OF_MONTH);
                // Populating the graph
                for(i = 1; i < 8; i++)
                {
                    // We need to change this to dates
                    y = spending2[i-1];
                    //weekly_series.appendData(new DataPoint(dateCount,y), true, 10);
                    weekly_series.appendData(new DataPoint(test,y), true, 10);
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    test = calendar.get(Calendar.DAY_OF_MONTH);
                    System.out.println(test);
                }
                weeklyGraph.addSeries(weekly_series);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                // Failed to read value
                Log.v("TestRead", "Failed to read value.", databaseError.toException());
            }
        });


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