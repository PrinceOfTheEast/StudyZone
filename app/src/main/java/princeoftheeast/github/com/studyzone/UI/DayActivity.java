package princeoftheeast.github.com.studyzone.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import princeoftheeast.github.com.studyzone.Adapter.NotificationReceiver;
import princeoftheeast.github.com.studyzone.Adapter.TimePickerFragment;
import princeoftheeast.github.com.studyzone.Models.Subject;
import princeoftheeast.github.com.studyzone.Adapter.rvAdapter;
import princeoftheeast.github.com.studyzone.R;

public class DayActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    private static final String TAG = DayActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private princeoftheeast.github.com.studyzone.Adapter.rvAdapter rvAdapter;

    private EditText addSubjectET;
    private Button addSubjectTimeStart;
    private Button addSubjectTimeEnd;
    private Button addSubjectBTN;
    private List<Subject> allEntries;

    private Calendar alarmCalendar;
    private String startTime;
    private String endTime;
    private boolean whichTimeButton = true;//to check which time button has been clicked last so correct values can be stored in time variables e.g. startHour
    private boolean isStartTimeSet = false;
    private ArrayList<PendingIntent> intentArray;


    private DatabaseReference databaseReference;
    private FirebaseAuth fbAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        allEntries = new ArrayList<Subject>();
        intentArray = new ArrayList<PendingIntent>();

        fbAuth = FirebaseAuth.getInstance();//to get an instance of the authenticator into the variable
        FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
        databaseReference = fbDatabase.getReference(fbAuth.getUid());//in firebase every  user has unique uid

        addSubjectET = (EditText)findViewById(R.id.etAddSubject);
        addSubjectTimeStart = (Button)findViewById(R.id.btnAddSubjectTimeStart);
        addSubjectTimeEnd = (Button)findViewById(R.id.btnAddSubjectTimeEnd);
        recyclerView = (RecyclerView)findViewById(R.id.rvDayList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        addSubjectBTN = (Button)findViewById(R.id.btnAddSubject);


        addSubjectTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichTimeButton = true;
                isStartTimeSet = true;
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");

            }
        });

        addSubjectTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichTimeButton = false;
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        addSubjectBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredSubject = addSubjectET.getText().toString();

                addSubjectTimeStart.setText("Start");//as method onTimeSet changes the text
                addSubjectTimeEnd.setText("End");//as method onTimeSet changes the text

                if(TextUtils.isEmpty(enteredSubject)){
                    Toast.makeText(DayActivity.this, "Activity must not be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if(enteredSubject.length() < 2){
                    Toast.makeText(DayActivity.this, "Activity must be more than 1 character", Toast.LENGTH_LONG).show();
                    return;
                }

                String enteredSubjectTime = startTime + " - " + endTime;
                if(isStartTimeSet) {
                    startAlarm(alarmCalendar);
                }
                else Toast.makeText(DayActivity.this, "Start time not entered, Notification not set", Toast.LENGTH_LONG).show();

                Subject entryObject = new Subject(enteredSubject, enteredSubjectTime);
                databaseReference.child(WeeklyActivity.SELECTED_DAY).push().setValue(entryObject);
                addSubjectET.setText("");

            }
        });
        databaseReference.child(WeeklyActivity.SELECTED_DAY).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllEntries(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllEntries(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                removeEntry(dataSnapshot);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void getAllEntries(DataSnapshot dataSnapshot){

        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            String entryName = dataSnapshot.child("subject").getValue(String.class);
            String entryTime = dataSnapshot.child("subjectTime").getValue(String.class);

            allEntries.add(new Subject(entryName, entryTime));
            rvAdapter = new rvAdapter(DayActivity.this, allEntries);
            recyclerView.setAdapter(rvAdapter);

            return;
        }

    }
    private void removeEntry(DataSnapshot dataSnapshot){
        //dataSnapshot.child(SELECTED_DAY);
        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            String entryName = singleSnapshot.getValue(String.class);
            for(int i = 0; i < allEntries.size(); i++){
                if(allEntries.get(i).getSubject().equals(entryName)){
                    allEntries.remove(i);
                }
            }
            Log.d(TAG, "Entry tile " + entryName);
            rvAdapter.notifyDataSetChanged();
            rvAdapter = new rvAdapter(DayActivity.this, allEntries);
            recyclerView.setAdapter(rvAdapter);
        }
        //databaseReference.child("Monday").child("Tasks").setValue("Hello, Lovely World!");

    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        if(whichTimeButton){
            startTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
            addSubjectTimeStart.setText(startTime);
            alarmCalendar = calendar;
            //startAlarm(alarmCalendar);
        }
        else{
            endTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
            addSubjectTimeEnd.setText(endTime);
        }

    }
    private void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, allEntries.size(), intent, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1); // if time selected has passed notification does not go off immediately
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        intentArray.add(pendingIntent);
    }
}

