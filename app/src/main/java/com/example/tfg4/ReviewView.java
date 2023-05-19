package com.example.tfg4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

public class ReviewView extends AppCompatActivity {

    Button btnBack, btnEdit, btnDelete;
    EditText txtText;
    RatingBar ratingBar;

    FirebaseDatabase database;
    DatabaseReference reviewsRef;
    Query query;

    DatabaseReference reviewsRef2;

    String previousText;
    float previousStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_view);

        reviewsRef2 = FirebaseDatabase.getInstance().getReference("Reviews");

        Bundle extras = getIntent().getExtras();
        String value = extras.getString("reviewKey");

        reviewsRef2 = reviewsRef2.child(value);

        database = FirebaseDatabase.getInstance();
        reviewsRef = database.getReference("Reviews");
        query = reviewsRef.child(value);

        txtText = findViewById(R.id.txtText);
        ratingBar = findViewById(R.id.ratingBar);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Review review = snapshot.getValue(Review.class);
                previousText = review.getText();
                txtText.setText(previousText);
                previousStars = review.getStars();
                ratingBar.setRating(previousStars);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReviewView.this, "Error", Toast.LENGTH_SHORT);
            }
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyReviews.class);
                startActivity(intent);
            }
        });

        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = txtText.getText().toString().trim();
                float stars = ratingBar.getRating();
                if (text.equals(previousText) && stars == previousStars) {
                    Toast.makeText(ReviewView.this, "Reseña sin modificaciones", Toast.LENGTH_SHORT);
                } else {
                    Calendar actualDate = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    String DateHour = simpleDateFormat.format(actualDate.getTime());

                    Map<String, Object> update = new HashMap<>();
                    update.put("text", text);
                    update.put("stars", stars);
                    update.put("dateHour", DateHour);
                    reviewsRef2.updateChildren(update);
                    Intent intent = new Intent(getApplicationContext(), MyReviews.class);
                    startActivity(intent);

                }
            }
        });

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewsRef2.removeValue();
                Intent intent = new Intent(getApplicationContext(), MyReviews.class);
                startActivity(intent);
            }
        });
    }
}