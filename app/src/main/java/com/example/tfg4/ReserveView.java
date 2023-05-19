package com.example.tfg4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReserveView extends AppCompatActivity {

    TextView scName, scAddress, facilityName, txtDateHour, txtDateHour2;
    Button btnDateHour, btnEdit, btnDelete;

    FirebaseDatabase database;
    DatabaseReference reservesRef;
    Query query;

    DatabaseReference reservesRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_view);

        txtDateHour2 = findViewById(R.id.txtDateHour2);
        reservesRef2 = FirebaseDatabase.getInstance().getReference("Reserves");

        Bundle extras = getIntent().getExtras();
        String value = extras.getString("reserveKey");

        reservesRef2 = reservesRef2.child(value);

        database = FirebaseDatabase.getInstance();
        reservesRef = database.getReference("Reserves");
        query = reservesRef.child(value);

        scName = findViewById(R.id.scName);
        scAddress = findViewById(R.id.scAddress);
        facilityName = findViewById(R.id.facilityName);
        txtDateHour = findViewById(R.id.txtDateHour);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Reserve reserve = snapshot.getValue(Reserve.class);
                scName.setText(reserve.getScName());
                scAddress.setText(reserve.getScAddress());
                facilityName.setText(reserve.getFacilityName());
                txtDateHour.setText(reserve.getDateHour());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReserveView.this, "Error", Toast.LENGTH_SHORT);
            }
        });

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reserves.class);
                startActivity(intent);
            }
        });

        btnDateHour = findViewById(R.id.btnDateHour);
        btnDateHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(txtDateHour2);
            }
        });

        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtDateHour.equals(txtDateHour2)) {
                    Toast.makeText(ReserveView.this, "Misma fecha y hora", Toast.LENGTH_SHORT);
                } else {
                    String DateHour = txtDateHour2.getText().toString().trim();
                    String Date = DateHour.substring(0, 10);
                    String Hour = DateHour.substring(11);
                    Map<String, Object> update = new HashMap<>();
                    update.put("hour", Hour);
                    update.put("date", Date);
                    update.put("dateHour", DateHour);
                    reservesRef2.updateChildren(update);
                    Intent intent = new Intent(getApplicationContext(), Reserves.class);
                    startActivity(intent);
                }
            }
        });

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservesRef2.removeValue();
                Intent intent = new Intent(getApplicationContext(), Reserves.class);
                startActivity(intent);
            }
        });
    }

    private void showDateTimeDialog(TextView txtDateHour2) {
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
                        txtDateHour2.setText(simpleDateFormat.format(calendar.getTime()));
                        btnEdit.setVisibility(View.VISIBLE);
                        btnEdit.setEnabled(true);
                    }
                };
                new TimePickerDialog(ReserveView.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };
        new DatePickerDialog(ReserveView.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}