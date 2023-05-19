package com.example.tfg4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WriteReview extends AppCompatActivity {

    Button btnBack, btnWriteReview;
    EditText txtText;
    RatingBar ratingBar;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        Bundle extras = getIntent().getExtras();
        String value = extras.getString("reserveKey");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtText = findViewById(R.id.txtText);
        ratingBar = findViewById(R.id.ratingBar);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExpiredReserves.class);
                startActivity(intent);
            }
        });

        btnWriteReview = findViewById(R.id.btnWriteReview);
        btnWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar actualDate = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

                String reserveID = value;
                String text = txtText.getText().toString().trim();
                float stars = ratingBar.getRating();
                String DateHour = simpleDateFormat.format(actualDate.getTime());
                Review review = new Review(reserveID, text, stars, DateHour);
                mDatabase.child("Reviews").push().setValue(review);
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });
    }
}