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
    static Date tempDate8 = new Date();

    // 7 slots for 7 days out of the week
    double spending2[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    Date dateArr[] = new Date[8];
    // Expenses
    double x, y;
    String monthAxis = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_report);

        mReturnHomeFromWeeklyReportBtn = findViewById(R.id.returnHomeFromWeeklyReportBtn);
        signOutFromWeeklyReportBtn = findViewById(R.id.signOutFromWeeklyReportBtn);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar2.setFirstDayOfWeek(Calendar.MONDAY);
        calendar2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar2.add(Calendar.DAY_OF_WEEK, -7);
        calender3.setFirstDayOfWeek(Calendar.MONDAY);
        calender3.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calender3.add(Calendar.DAY_OF_WEEK, -7);
        Date nextMon = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, -7);
        Date lastMon = calendar.getTime();
        //System.out.println(lastMon);
        //System.out.println(nextMon);
        tempDate = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate2 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate3 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate4 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate5 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate6 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate7 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate8 = calender3.getTime();
        dateArr[0] = tempDate;
        dateArr[1] = tempDate2;
        dateArr[2] = tempDate3;
        dateArr[3] = tempDate4;
        dateArr[4] = tempDate5;
        dateArr[5] = tempDate6;
        dateArr[6] = tempDate7;
        dateArr[7] = tempDate8;

        //calendar2.add(Calendar.DAY_OF_WEEK, -7);
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


        int monthTitle = Calendar.getInstance().get(Calendar.MONTH);
        if(monthTitle == 0)
        {
            monthAxis = "Date (Week starting in January)";
        }
        if(monthTitle == 1)
        {
            monthAxis = "Date (Week starting in February)";
        }
        if(monthTitle == 2)
        {
            monthAxis = "Date (Week starting in March)";
        }
        if(monthTitle == 3)
        {
            monthAxis = "Date (Week starting in April)";
        }
        if(monthTitle == 4)
        {
            monthAxis = "Date (Week starting in May)";
        }
        if(monthTitle == 5)
        {
            monthAxis = "Date (Week starting in June)";
        }
        if(monthTitle == 6)
        {
            monthAxis = "Date (Week starting in July)";
        }
        if(monthTitle == 7)
        {
            monthAxis = "Date (Week starting in August)";
        }
        if(monthTitle == 8)
        {
            monthAxis = "Date (Week starting in September)";
        }
        if(monthTitle == 9)
        {
            monthAxis = "Date (Week starting in October)";
        }
        if(monthTitle == 10)
        {
            monthAxis = "Date (Week starting in November)";
        }
        if(monthTitle == 11)
        {
            monthAxis = "Date (Week starting in December)";
        }


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
                weeklyGraph.getGridLabelRenderer().setHorizontalAxisTitle(monthAxis);
                weeklyGraph.getGridLabelRenderer().setVerticalAxisTitle("Expenses ($)");
                // Setting how many values are on the x axis:
                weeklyGraph.getGridLabelRenderer().setNumHorizontalLabels(7);
                // Setting graph title:
                weeklyGraph.setTitle("Weekly Expenses");

                DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                String[] days = new String[7];


                int i = 0;
                // Iterating through the database for the Expenses
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Expenses expenses = snapshot.getValue(Expenses.class);

                    if(expenses.creationDate.compareTo(nextMon) <= 0 && expenses.creationDate.compareTo(lastMon) >= 0)
                    {
                        expensesList.add(expenses);
                        if(expenses.creationDate.compareTo(dateArr[i+1]) < 0)
                        {
                            spending2[i] = spending2[i]+expenses.spending;
                            System.out.println(spending2[i]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+2]) < 0 && expenses.creationDate.compareTo(dateArr[i]) > 0)
                        {
                            spending2[i+1] = spending2[i+1]+expenses.spending;
                            System.out.println(spending2[i+1]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+3]) < 0 && expenses.creationDate.compareTo(dateArr[i+1]) > 0)
                        {
                            spending2[i+2] = spending2[i+2]+expenses.spending;
                            System.out.println(spending2[i+2]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+4]) < 0 && expenses.creationDate.compareTo(dateArr[i+2]) > 0)
                        {
                            spending2[i+3] = spending2[i+3]+expenses.spending;
                            System.out.println(spending2[i+3]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+5]) < 0 && expenses.creationDate.compareTo(dateArr[i+3]) > 0)
                        {
                            spending2[i+4] = spending2[i+4]+expenses.spending;
                            System.out.println(spending2[i+4]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+6]) < 0 && expenses.creationDate.compareTo(dateArr[i+4]) > 0)
                        {
                            spending2[i+5] = spending2[i+5]+expenses.spending;
                            System.out.println(spending2[i+5]);
                        }
                        else if (expenses.creationDate.compareTo(dateArr[i+7]) < 0 && expenses.creationDate.compareTo(dateArr[i+5]) > 0)
                        {
                            spending2[i+6] = spending2[i+6]+expenses.spending;
                            System.out.println(spending2[i+6]);
                        }
                        //System.out.println(spending2[i]);
                        System.out.println(expenses.creationDate);
                        System.out.println(expenses.creationDate.compareTo(lastMon));
                        System.out.println(expenses.creationDate.compareTo(nextMon));
                    }

                }

                int test = calendar2.get(Calendar.DAY_OF_MONTH);
                // Populating the graph
                for(i = 1; i < 8; i++)
                {
                    // We need to change this to dates
                    y = spending2[i-1];
                    //weekly_series.appendData(new DataPoint(dateCount,y), true, 10);
                    weekly_series.appendData(new DataPoint(test,y), true, 10);
                    calendar2.add(Calendar.DAY_OF_YEAR, 1);
                    System.out.println(test);
                    test = calendar2.get(Calendar.DAY_OF_MONTH);

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