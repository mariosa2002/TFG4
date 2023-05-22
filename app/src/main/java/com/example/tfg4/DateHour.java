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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DateHour extends AppCompatActivity {

    TextView scName, scAddress, facilityName, txtDateHour;
    Button btnReserve;

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference mDatabase, dbReference;
    Query query;

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
                boolean aux = true;
                ArrayList<Reserve> reserves = new ArrayList<Reserve>();
                int position = 0;
                boolean aux2 = true;

                String rscName = scName.getText().toString().trim();
                String rscAddress = scAddress.getText().toString().trim();
                String rFacilityName = facilityName.getText().toString().trim();
                String userID = user.getUid();
                String DateHour = txtDateHour.getText().toString().trim();
                String Date = DateHour.substring(0, 10);
                String Hour = DateHour.substring(11);

                int hourAux = Integer.parseInt(Hour.substring(0, 2));
                if (hourAux < 8 || hourAux > 21) {
                    aux = false;
                    Toast.makeText(DateHour.this, "El horario de reserva es de 8 a 22", Toast.LENGTH_SHORT).show();
                } else {
                    dbReference = FirebaseDatabase.getInstance().getReference("Reserves");
                    query = dbReference.orderByChild("scName");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Reserve reserve = snapshot.getValue(Reserve.class);
                            if (reserve.getScName().equals(rscName) && reserve.getFacilityName().equals(rFacilityName)) {
                                reserves.add(reserve);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    do {
                        if (reserves.get(position).getDateHour().equals(DateHour)) {
                            aux2 = false;
                            Toast.makeText(DateHour.this, "Fecha y hora no disponible", Toast.LENGTH_SHORT).show();
                        }
                        position++;
                    } while (position < reserves.size() && aux2);
                }

                if (aux & aux2) {
                    Reserve reserve = new Reserve(rscName, rscAddress, rFacilityName, userID, Date, Hour, DateHour);
                    mDatabase.child("Reserves").push().setValue(reserve);
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                }
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