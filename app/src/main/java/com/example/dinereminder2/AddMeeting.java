package com.example.dinereminder2;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddMeeting extends AppCompatActivity {

    EditText meetPurpose;
    TextView setDate,setTime;
    Spinner spinner;
    Button save;
    ArrayList<Meeting> meetsArr;
    Calendar meetingCalender = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        createMeetingChannel();
        loadData();
        spinner = (Spinner)findViewById(R.id.spinner);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        final String locationString = pref.getString("locations",null);
        System.out.println("locationString");
        System.out.println(locationString);
            String[] locations = locationString.split("---");
            System.out.println(locations[0]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,locations);
            spinner.setAdapter(adapter);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        meetPurpose = findViewById(R.id.meet_purpose);
        setDate = findViewById(R.id.setDate);
        setTime = findViewById(R.id.setTime);
        setDate.setText(dateFormatter.format(date));
        setTime.setText(timeFormatter.format(date));
        save = findViewById(R.id.saveMeeting);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Meeting meeting = new Meeting();
                String getMeetPurpose = meetPurpose.getText().toString();
                if(getMeetPurpose.equals("")) {
                    Toast.makeText(AddMeeting.this, "Please add the meeting purpose", Toast.LENGTH_LONG).show();
                    return;
                }
                String getDate = setDate.getText().toString();
                String getTime = setTime.getText().toString();
                String getPlace = spinner.getSelectedItem().toString();
                meeting.setMeetPurpose(getMeetPurpose);
                meeting.setDate(getDate);
                meeting.setTime(getTime);
                meeting.setPlace(getPlace);
                meetsArr.add(meeting);
                System.out.println("Meets Array");
                System.out.println(meetsArr);
                saveData();
                createAlarm();
                finish();
            }
        });
    }

    public void createAlarm() {
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, MeetingNotificationHandler.class);
            PendingIntent pi = PendingIntent.getBroadcast(this,1,intent,0);
            System.out.println("Setting Alarm at " +meetingCalender.getTimeInMillis());
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, meetingCalender.getTimeInMillis(),pi);

    }


    public void createMeetingChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
        CharSequence name = "Meeting Channel";
        String description = "Channel for all meeting alerts";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("notify channel", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(meetsArr);
        editor.putString("task list", json);
        editor.apply();
    }


    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Meeting>>() {}.getType();
        meetsArr = gson.fromJson(json, type);

        if (meetsArr == null) {
            meetsArr = new ArrayList<>();
        }
    }


    public void setDate(View v){
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        setDate.setText(i1+"/"+i2+"/"+i);
                        meetingCalender.set(Calendar.YEAR, i);
                        meetingCalender.set(Calendar.MONTH,i1);
                        meetingCalender.set(Calendar.DAY_OF_MONTH,i2);
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void setTime(View v){
        Calendar c = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        setTime.setText(i+":"+i1);
                        meetingCalender.set(Calendar.HOUR_OF_DAY, i);
                        meetingCalender.set(Calendar.MINUTE,i1);
                        meetingCalender.set(Calendar.SECOND,00);
                    }
                },c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),false);
        timePickerDialog.show();
    }
}
