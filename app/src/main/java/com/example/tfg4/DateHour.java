package com.example.tfg4;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateHour extends AppCompatActivity {

    TextView scName, scAddress, facilityName, txtDateHour;
    Button btnReserve;

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_hour);

        txtDateHour = findViewById(R.id.txtDateHour);

        Bundle extras = getIntent().getExtras();
        String value = extras.getString("facilityName");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        scName = findViewById(R.id.scName);
        scAddress = findViewById(R.id.scAddress);
        ImageView imageView = findViewById(R.id.image_view);
        facilityName = findViewById(R.id.facilityName);

        scName.setText(SportsCenters.sportsCenters.get(Facilities.position).getName());
        scAddress.setText(SportsCenters.sportsCenters.get(Facilities.position).getAddress());
        imageView.setImageResource(SportsCenters.sportsCenters.get(Facilities.position).getImage());
        facilityName.setText(value);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Facilities.class);
                startActivity(intent);
            }
        });

        Button btnDateHour = findViewById(R.id.btnDateHour);
        btnDateHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(txtDateHour);
            }
        });

        btnReserve = findViewById(R.id.btnReserve);
        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rscName = scName.getText().toString().trim();
                String rscAddress = scAddress.getText().toString().trim();
                String rFacilityName = facilityName.getText().toString().trim();
                String userID = user.getUid();
                String DateHour = txtDateHour.getText().toString().trim();
                String Date = DateHour.substring(0, 10);
                String Hour = DateHour.substring(11);
                Reserve reserve = new Reserve(rscName, rscAddress, rFacilityName, userID, Date, Hour, DateHour);
                mDatabase.child("Reserves").push().setValue(reserve);
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

    }

    private void showDateTimeDialog(TextView txtDateHour) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        txtDateHour.setText(simpleDateFormat.format(calendar.getTime()));
                        btnReserve.setVisibility(View.VISIBLE);
                        btnReserve.setEnabled(true);
                    }
                };
                new TimePickerDialog(DateHour.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };
        new DatePickerDialog(DateHour.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}