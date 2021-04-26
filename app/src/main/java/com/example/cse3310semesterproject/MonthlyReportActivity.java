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

public class MonthlyReportActivity extends AppCompatActivity
{

    Button mReturnHomeFromMonthlyReportBtn, signOutFromMonthlyReportBtn;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reff = database.getReference().child("Users").child(uid).child("Expenses");
    // Initializing the graph to be a line graph
    private LineGraphSeries<DataPoint> monthly_series;

    Calendar calendar = Calendar.getInstance();
    Calendar calendar2 = Calendar.getInstance();
    Calendar calender3 = Calendar.getInstance();


    static Date tempDate = new Date();
    static Date tempDate2 = new Date();
    static Date tempDate3 = new Date();
    static Date tempDate4 = new Date();
    static Date tempDate5 = new Date();
    static Date tempDate6 = new Date();
    static Date tempDate7 = new Date();


    // 7 slots for 7 days out of the week
    double spending2[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    Date dateArr[] = new Date[7];
    // Expenses
    double x, y;
    String monthAxis = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report);

        mReturnHomeFromMonthlyReportBtn = findViewById(R.id.returnHomeFromMonthlyReportBtn);
        signOutFromMonthlyReportBtn = findViewById(R.id.signOutFromMonthlyReportBtn);



        //------------------------------------------------------------------------------------------
        // Graphing Section
        //
        //
        // Graph documentation here: https://github.com/jjoe64/GraphView/wiki/Download-and-Getting-Started

        GraphView monthlyGraph = (GraphView)findViewById(R.id.MonthlyReportGraph);
        monthly_series = new LineGraphSeries<>();

        // Setting the axis titles:
        // Possibly look at this for date axis: https://github.com/jjoe64/GraphView/wiki/Dates-as-labels
        // Also this: https://github.com/jjoe64/GraphView/wiki/Style-options
        monthlyGraph.getGridLabelRenderer().setHorizontalAxisTitle("Date");
        monthlyGraph.getGridLabelRenderer().setVerticalAxisTitle("Expenses ($)");

        // Setting graph title:
        monthlyGraph.setTitle("Monthly Expenses");

        double x = 0, y = 0;

        // I guess we need to discuss how we wish to go about graphing these graphs, weekly as in
        // the weeks of any given month, or just the days of that week? Monthly as in the weeks in
        // a single month or monthly as in every month in a year?
        int numMonths = 12;

        // This for loop added in filler data for now. We need to be able to put user expenses with
        // their dates into this graph
        for(int i = 0; i < numMonths; i++)
        {
            x = i + 1;
            y = y + 150;
            monthly_series.appendData(new DataPoint(x,y), true, 10);
        }

        monthlyGraph.addSeries(monthly_series);


        //------------------------------------------------------------------------------------------
        // Let the user go back to the homepage
        mReturnHomeFromMonthlyReportBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        //------------------------------------------------------------------------------------------
        // Let the user sign out
        signOutFromMonthlyReportBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // The toast is what makes the message pop up when the user signs out
                Toast.makeText(MonthlyReportActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut(); //firebase command to delete token created for account locally
                startActivity(new Intent(getApplicationContext(), Login.class)); //back to login screen
            }
        });
    }
}