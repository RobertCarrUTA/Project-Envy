package com.example.cse3310semesterproject;

import androidx.annotation.NonNull;
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
    static Date tempDate8 = new Date();
    static Date tempDate9 = new Date();
    static Date tempDate10 = new Date();
    static Date tempDate11 = new Date();
    static Date tempDate12 = new Date();
    static Date tempDate13 = new Date();
    static Date tempDate14 = new Date();
    static Date tempDate15 = new Date();
    static Date tempDate16 = new Date();
    static Date tempDate17 = new Date();
    static Date tempDate18 = new Date();
    static Date tempDate19 = new Date();
    static Date tempDate20 = new Date();
    static Date tempDate21 = new Date();
    static Date tempDate22 = new Date();
    static Date tempDate23 = new Date();
    static Date tempDate24 = new Date();
    static Date tempDate25 = new Date();
    static Date tempDate26 = new Date();
    static Date tempDate27 = new Date();
    static Date tempDate28 = new Date();
    static Date tempDate29 = new Date();

    // 7 slots for 7 days out of the week
    double spending2[] = {0.0, 0.0, 0.0, 0.0};
    Date dateArr[] = new Date[5];
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

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar2.setFirstDayOfWeek(Calendar.MONDAY);
        calendar2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar2.add(Calendar.DAY_OF_WEEK, -28);
        calender3.setFirstDayOfWeek(Calendar.MONDAY);
        calender3.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calender3.add(Calendar.DAY_OF_WEEK, -28);
        Date nextMon = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, -28);
        Date lastMon = calendar.getTime();
        //System.out.println(lastMon);
        //System.out.println(nextMon);
        tempDate = calender3.getTime();
        System.out.println(tempDate);
        calender3.add(Calendar.DAY_OF_WEEK, 7);
        tempDate2 = calender3.getTime();
        System.out.println(tempDate2);
        calender3.add(Calendar.DAY_OF_WEEK, 7);
        tempDate3 = calender3.getTime();
        System.out.println(tempDate3);
        calender3.add(Calendar.DAY_OF_WEEK, 7);
        tempDate4 = calender3.getTime();
        System.out.println(tempDate4);
        calender3.add(Calendar.DAY_OF_WEEK, 7);
        tempDate5 = calender3.getTime();
        System.out.println(tempDate5);
        /*calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate6 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate7 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate8 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate9 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate10 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate11 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate12 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate13 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate14 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate15 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate16 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate17 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate18 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate19 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate20 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate21 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate22 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate23 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate24 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate25 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate26 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate27 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate28 = calender3.getTime();
        calender3.add(Calendar.DAY_OF_WEEK, 1);
        tempDate29 = calender3.getTime();*/
        dateArr[0] = tempDate;
        dateArr[1] = tempDate2;
        dateArr[2] = tempDate3;
        dateArr[3] = tempDate4;
        dateArr[4] = tempDate5;
        System.out.println(dateArr[0]);
        System.out.println(dateArr[1]);
        System.out.println(dateArr[2]);
        System.out.println(dateArr[3]);
        System.out.println(dateArr[4]);
        /*dateArr[5] = tempDate6;
        dateArr[6] = tempDate7;
        dateArr[7] = tempDate8;
        dateArr[8] = tempDate9;
        dateArr[9] = tempDate10;
        dateArr[10] = tempDate11;
        dateArr[11] = tempDate12;
        dateArr[12] = tempDate13;
        dateArr[13] = tempDate14;
        dateArr[14] = tempDate15;
        dateArr[15] = tempDate16;
        dateArr[16] = tempDate17;
        dateArr[17] = tempDate18;
        dateArr[18] = tempDate19;
        dateArr[19] = tempDate20;
        dateArr[20] = tempDate21;
        dateArr[21] = tempDate22;
        dateArr[22] = tempDate23;
        dateArr[23] = tempDate24;
        dateArr[24] = tempDate25;
        dateArr[25] = tempDate26;
        dateArr[26] = tempDate27;
        dateArr[27] = tempDate28;
        dateArr[28] = tempDate29;*/

        List<Expenses> expensesList = new ArrayList<Expenses>();

        //------------------------------------------------------------------------------------------
        // Graphing Section
        //
        //
        // Graph documentation here: https://github.com/jjoe64/GraphView/wiki/Download-and-Getting-Started


        monthAxis = "Spending by week over the last month";

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GraphView monthlyGraph = (GraphView)findViewById(R.id.MonthlyReportGraph);
                monthly_series = new LineGraphSeries<>();

                // Setting the axis titles:
                // Possibly look at this for date axis: https://github.com/jjoe64/GraphView/wiki/Dates-as-labels
                // Also this: https://github.com/jjoe64/GraphView/wiki/Style-options
                monthlyGraph.getGridLabelRenderer().setHorizontalAxisTitle(monthAxis);
                monthlyGraph.getGridLabelRenderer().setVerticalAxisTitle("Expenses ($)");
                monthlyGraph.getGridLabelRenderer().setNumHorizontalLabels(4);
                monthlyGraph.getGridLabelRenderer().setPadding(40);
                // Setting graph title:
                monthlyGraph.setTitle("Monthly Expenses");

                int i = 0;
                // Iterating through the database for the Expenses
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Expenses expenses = dataSnapshot.getValue(Expenses.class);

                  if(expenses.creationDate.compareTo(dateArr[4]) <= 0 && expenses.creationDate.compareTo(dateArr[0]) >= 0)
                    {
                        expensesList.add(expenses);
                        if(expenses.creationDate.compareTo(dateArr[1]) < 0)
                        {
                            spending2[0] = spending2[0]+expenses.spending;
                            System.out.println(spending2[0]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[2]) < 0 && expenses.creationDate.compareTo(dateArr[0]) > 0)
                        {
                            spending2[1] = spending2[1]+expenses.spending;
                            System.out.println(spending2[1]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[3]) < 0 && expenses.creationDate.compareTo(dateArr[1]) > 0)
                        {
                            spending2[2] = spending2[2]+expenses.spending;
                            System.out.println(spending2[2]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[4]) < 0 && expenses.creationDate.compareTo(dateArr[2]) > 0)
                        {
                            spending2[3] = spending2[3]+expenses.spending;
                            System.out.println(spending2[3]);
                        }
                     /*   else if(expenses.creationDate.compareTo(dateArr[i+5]) < 0 && expenses.creationDate.compareTo(dateArr[i+3]) > 0)
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
                        else if(expenses.creationDate.compareTo(dateArr[i+8]) < 0 && expenses.creationDate.compareTo(dateArr[i+6]) > 0)
                        {
                            spending2[i+7] = spending2[i+7]+expenses.spending;
                            System.out.println(spending2[i+7]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+9]) < 0 && expenses.creationDate.compareTo(dateArr[i+7]) > 0)
                        {
                            spending2[i+8] = spending2[i+8]+expenses.spending;
                            System.out.println(spending2[i+8]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+10]) < 0 && expenses.creationDate.compareTo(dateArr[i+8]) > 0)
                        {
                            spending2[i+9] = spending2[i+9]+expenses.spending;
                            System.out.println(spending2[i+9]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+11]) < 0 && expenses.creationDate.compareTo(dateArr[i+9]) > 0)
                        {
                            spending2[i+10] = spending2[i+10]+expenses.spending;
                            System.out.println(spending2[i+10]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+12]) < 0 && expenses.creationDate.compareTo(dateArr[i+10]) > 0)
                        {
                            spending2[i+11] = spending2[i+11]+expenses.spending;
                            System.out.println(spending2[i+11]);
                        }
                        else if (expenses.creationDate.compareTo(dateArr[i+13]) < 0 && expenses.creationDate.compareTo(dateArr[i+11]) > 0)
                        {
                            spending2[i+12] = spending2[i+12]+expenses.spending;
                            System.out.println(spending2[i+12]);
                        }


                        else if(expenses.creationDate.compareTo(dateArr[i+14]) < 0 && expenses.creationDate.compareTo(dateArr[i+12]) > 0)
                        {
                            spending2[i+13] = spending2[i+13]+expenses.spending;
                            System.out.println(spending2[i+13]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+15]) < 0 && expenses.creationDate.compareTo(dateArr[i+13]) > 0)
                        {
                            spending2[i+14] = spending2[i+14]+expenses.spending;
                            System.out.println(spending2[i+14]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+16]) < 0 && expenses.creationDate.compareTo(dateArr[i+14]) > 0)
                        {
                            spending2[i+15] = spending2[i+15]+expenses.spending;
                            System.out.println(spending2[i+15]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+17]) < 0 && expenses.creationDate.compareTo(dateArr[i+15]) > 0)
                        {
                            spending2[i+16] = spending2[i+16]+expenses.spending;
                            System.out.println(spending2[i+16]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+18]) < 0 && expenses.creationDate.compareTo(dateArr[i+16]) > 0)
                        {
                            spending2[i+17] = spending2[i+17]+expenses.spending;
                            System.out.println(spending2[i+17]);
                        }
                        else if (expenses.creationDate.compareTo(dateArr[i+19]) < 0 && expenses.creationDate.compareTo(dateArr[i+17]) > 0)
                        {
                            spending2[i+18] = spending2[i+18]+expenses.spending;
                            System.out.println(spending2[i+18]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+20]) < 0 && expenses.creationDate.compareTo(dateArr[i+18]) > 0)
                        {
                            spending2[i+19] = spending2[i+19]+expenses.spending;
                            System.out.println(spending2[i+19]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+21]) < 0 && expenses.creationDate.compareTo(dateArr[i+19]) > 0)
                        {
                            spending2[i+20] = spending2[i+20]+expenses.spending;
                            System.out.println(spending2[i+20]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+22]) < 0 && expenses.creationDate.compareTo(dateArr[i+20]) > 0)
                        {
                            spending2[i+21] = spending2[i+21]+expenses.spending;
                            System.out.println(spending2[i+21]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+23]) < 0 && expenses.creationDate.compareTo(dateArr[i+23]) > 0)
                        {
                            spending2[i+22] = spending2[i+22]+expenses.spending;
                            System.out.println(spending2[i+22]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+24]) < 0 && expenses.creationDate.compareTo(dateArr[i+22]) > 0)
                        {
                            spending2[i+23] = spending2[i+23]+expenses.spending;
                            System.out.println(spending2[i+23]);
                        }
                        else if (expenses.creationDate.compareTo(dateArr[i+25]) < 0 && expenses.creationDate.compareTo(dateArr[i+23]) > 0)
                        {
                            spending2[i+24] = spending2[i+24]+expenses.spending;
                            System.out.println(spending2[i+24]);
                        }

                        else if(expenses.creationDate.compareTo(dateArr[i+26]) < 0 && expenses.creationDate.compareTo(dateArr[i+24]) > 0)
                        {
                            spending2[i+26] = spending2[i+26]+expenses.spending;
                            System.out.println(spending2[i+26]);
                        }
                        else if(expenses.creationDate.compareTo(dateArr[i+27]) < 0 && expenses.creationDate.compareTo(dateArr[i+25]) > 0)
                        {
                            spending2[i+27] = spending2[i+27]+expenses.spending;
                            System.out.println(spending2[i+27]);
                        }
                        else if (expenses.creationDate.compareTo(dateArr[i+28]) < 0 && expenses.creationDate.compareTo(dateArr[i+26]) > 0)
                        {
                            spending2[i+27] = spending2[i+27]+expenses.spending;
                            System.out.println(spending2[i+27]);
                        }


                        //System.out.println(spending2[i]);
                        System.out.println(expenses.creationDate);
                        System.out.println(expenses.creationDate.compareTo(lastMon));
                        System.out.println(expenses.creationDate.compareTo(nextMon));
                    }*/
                   // i++;
                }
                }

                int test = calendar2.get(Calendar.DAY_OF_MONTH);
                //System.out.println(test);
                //monthly_series.appendData(new DataPoint(test,spending2[0]), true, 10);
                calendar2.add(Calendar.DAY_OF_YEAR, 7);
                test = calendar2.get(Calendar.DAY_OF_MONTH);
                System.out.println(test);
                //monthly_series.appendData(new DataPoint(0,0.0), true, 10);
                monthly_series.appendData(new DataPoint(1,spending2[0]), true, 10);
                calendar2.add(Calendar.DAY_OF_YEAR, 7);
                test = calendar2.get(Calendar.DAY_OF_MONTH);
                System.out.println(test);
                monthly_series.appendData(new DataPoint(2,spending2[1]), true, 10);
                calendar2.add(Calendar.DAY_OF_YEAR, 7);
                test = calendar2.get(Calendar.DAY_OF_MONTH);
                System.out.println(test);
                monthly_series.appendData(new DataPoint(3,spending2[2]), true, 10);
                calendar2.add(Calendar.DAY_OF_YEAR, 7);
                test = calendar2.get(Calendar.DAY_OF_MONTH);
                System.out.println(test);
                monthly_series.appendData(new DataPoint(4,spending2[3]), true, 10);
                //monthly_series.appendData(new DataPoint(5,0.0), true, 10);
                //monthly_series.appendData(new DataPoint(6,0.0), true, 10);
                // Populating the graph
              //  for(i = 1; i < 5; i++)
              //  {
                    // We need to change this to dates
                   // y = spending2[i-1];
                    //weekly_series.appendData(new DataPoint(dateCount,y), true, 10);
                 //   System.out.println(test);
                    //monthly_series.appendData(new DataPoint(test,y), true, 10);
                //    calendar2.add(Calendar.DAY_OF_YEAR, 7);

                //    test = calendar2.get(Calendar.DAY_OF_MONTH);

              //  }
                monthlyGraph.addSeries(monthly_series);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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