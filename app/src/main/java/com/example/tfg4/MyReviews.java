package com.example.tfg4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyReviews extends AppCompatActivity {

    public static ArrayList<String> reviewsKeys = new ArrayList<String>();

    ArrayList<String> aux;

    Button btnReviews;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reviewsRef;
    DatabaseReference reservesRef;
    Query reservesQuery;
    Query reviewsQuery;

    private RecyclerView myReviewsRecyclerView;
    private RecyclerView.Adapter myReviewsAdapter;
    private RecyclerView.LayoutManager myReviewsLayoutManager;
    public static ArrayList<Review> myReviews;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reviews);

        myReviewsRecyclerView = findViewById(R.id.rvMyReviews);
        myReviews = new ArrayList<Review>();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        database = FirebaseDatabase.getInstance();
        reviewsRef = database.getReference("Reviews");
        reservesRef = database.getReference("Reserves");
        reservesQuery = reservesRef.orderByChild("userID").equalTo(userID);
        aux = new ArrayList<String>();

        Recycler();

        btnReviews = findViewById(R.id.btnReviews);
        btnReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reviews.class);
                startActivity(intent);
            }
        });
    }

    private void Recycler() {
        myReviewsLayoutManager = new LinearLayoutManager(this);
        myReviewsRecyclerView.setLayoutManager(myReviewsLayoutManager);
        myReviewsAdapter = new MyReviewAdapter(myReviews);
        myReviewsRecyclerView.setAdapter(myReviewsAdapter);
        Content();
    }

    private void Content() {
        reservesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot reserveSnapshot : snapshot.getChildren()) {
                    String reserveKey = reserveSnapshot.getKey();
                    aux.add(reserveKey);
                }
                for (String auxKey : aux) {
                    reviewsQuery = reviewsRef.orderByChild("reserveID").equalTo(auxKey);
                    reviewsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot reviewSnapshot : snapshot.getChildren()) {
                                Review review = reviewSnapshot.getValue(Review.class);
                                myReviews.add(review);

                                reviewsKeys.add(reviewSnapshot.getKey());
                            }
                            myReviewsAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MyReviews.this, "Error", Toast.LENGTH_SHORT);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyReviews.this, "Error", Toast.LENGTH_SHORT);
            }
        });
    }
}