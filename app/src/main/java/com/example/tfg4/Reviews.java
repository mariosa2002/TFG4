package com.example.tfg4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Reviews extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    ArrayList<String> aux;

    Button btnMyReviews;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reviewsRef;
    DatabaseReference reservesRef;
    Query reservesQuery;
    Query reviewsQuery;

    private RecyclerView reviewsRecyclerView;
    private RecyclerView.Adapter reviewsAdapter;
    private RecyclerView.LayoutManager reviewsLayoutManager;
    public static ArrayList<Review> reviews;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        reviewsRecyclerView = findViewById(R.id.rvReviews);
        reviews = new ArrayList<Review>();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        database = FirebaseDatabase.getInstance();
        reviewsRef = database.getReference("Reviews");
        reservesRef = database.getReference("Reserves");
        reservesQuery = reservesRef.orderByChild("userID");
        aux = new ArrayList<String>();

        Recycler();

        btnMyReviews = findViewById(R.id.btnMyReviews);
        btnMyReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyReviews.class);
                startActivity(intent);
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.reviews);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.reserves:
                        startActivity(new Intent(getApplicationContext(), Reserves.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.maps:
                        startActivity(new Intent(getApplicationContext(), Maps.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.reviews:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private void Recycler() {
        reviewsLayoutManager = new LinearLayoutManager(this);
        reviewsRecyclerView.setLayoutManager(reviewsLayoutManager);
        reviewsAdapter = new ReviewAdapter(reviews);
        reviewsRecyclerView.setAdapter(reviewsAdapter);
        Content();
    }

    private void Content() {
        reservesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot reserveSnapshot : snapshot.getChildren()) {
                    Reserve reserve = reserveSnapshot.getValue(Reserve.class);
                    if (!reserve.getUserID().equals(userID)) {
                        String reserveKey = reserveSnapshot.getKey();
                        aux.add(reserveKey);
                    }
                }
                for (String auxKey : aux) {
                    reviewsQuery = reviewsRef.orderByChild("reserveID").equalTo(auxKey);
                    reviewsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot reviewSnapshot : snapshot.getChildren()) {
                                Review review = reviewSnapshot.getValue(Review.class);
                                reviews.add(review);
                            }
                            reviewsAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Reviews.this, "Error", Toast.LENGTH_SHORT);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Reviews.this, "Error", Toast.LENGTH_SHORT);
            }
        });
    }
}