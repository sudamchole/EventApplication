package com.example.sudam.eventapplicationamura;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Sudam Chole on 22/02/19.
 */

public class AddEventActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText editTextEventName,editTextEventAgenda,editTextDate,editTextTime;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String eventId;
    final Calendar myCalendar = Calendar.getInstance();
    private String amPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTextEventName=findViewById(R.id.editTextEventName);
        editTextEventAgenda=findViewById(R.id.editTextAgenda);
        editTextDate=findViewById(R.id.editTextDate);
        editTextTime=findViewById(R.id.editTextTime);
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'Events' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Events");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Add Events");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }


        };
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddEventActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                  @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                      if (hourOfDay >= 12) {
                          amPm = "PM";
                      } else {
                          amPm = "AM";
                      }
                      editTextTime.setText(hourOfDay + ":" + minutes+" "+amPm );

                  }


                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTextDate.setText(sdf.format(myCalendar.getTime()));
    }
    public void onClick(View view) {
        if (view.getId() == R.id.buttonAddEvent) {
            //processing data and sending to server
            ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
            pb.setVisibility(ProgressBar.VISIBLE);
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.addingEvent), Toast.LENGTH_SHORT).show();
            if(!editTextEventName.getText().toString().isEmpty()&&!editTextEventAgenda.getText().toString().isEmpty()
                    &&!editTextDate.getText().toString().isEmpty()&&!editTextTime.getText().toString().isEmpty()){
                createEvent(editTextEventName.getText().toString().trim(),editTextEventAgenda.getText().toString().trim(),editTextDate.getText().toString().trim(),editTextTime.getText().toString().trim());
                pb.setVisibility(ProgressBar.GONE);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.eventAdded), Toast.LENGTH_SHORT).show();
                editTextEventName.setText("");
                editTextEventAgenda.setText("");
                editTextDate.setText("");
                editTextTime.setText("");
                this.finish();
            } else {
                Toast.makeText(getApplicationContext(), "All fields are mandatory.", Toast.LENGTH_SHORT).show();
                pb.setVisibility(ProgressBar.INVISIBLE);
            }
        }
    }

    /**
     * Creating new event node under 'Events'
     */
    private void createEvent(String eventName, String eventAgenda,String eventDate,String eventTime) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(eventId)) {
            eventId = mFirebaseDatabase.push().getKey();
        }

        Events events = new Events(eventName, eventAgenda,eventDate,eventTime);

        mFirebaseDatabase.child(eventId).setValue(events);
   }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
