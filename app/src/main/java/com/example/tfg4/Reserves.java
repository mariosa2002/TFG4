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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Reserves extends AppCompatActivity {

    public static ArrayList<String> keys = new ArrayList<String>();

    BottomNavigationView bottomNavigationView;
    Button btnExpiredReserves;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reservesRef;
    Query query;

    private RecyclerView reservesRecyclerView;
    private RecyclerView.Adapter reservesAdapter;
    private RecyclerView.LayoutManager reservesLayoutManager;
    public static ArrayList<Reserve> reserves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserves);

        reservesRecyclerView = findViewById(R.id.rvReserves);
        reserves = new ArrayList<Reserve>();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String userID = user.getUid();
        database = FirebaseDatabase.getInstance();
        reservesRef = database.getReference("Reserves");
        query = reservesRef.orderByChild("userID").equalTo(userID);

        Recycler();

        btnExpiredReserves = findViewById(R.id.btnExpiredReserves);
        btnExpiredReserves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExpiredReserves.class);
                startActivity(intent);
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.reserves:
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
                        startActivity(new Intent(getApplicationContext(), Reviews.class));
                        overridePendingTransition(0, 0);
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
        reservesLayoutManager = new LinearLayoutManager(this);
        reservesRecyclerView.setLayoutManager(reservesLayoutManager);
        reservesAdapter = new ReserveAdapter(reserves);
        reservesRecyclerView.setAdapter(reservesAdapter);
        Content();
    }

    private void Content() {
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Calendar actualDate = Calendar.getInstance();
                int actualYear = actualDate.get(Calendar.YEAR);
                int actualMonth = actualDate.get(Calendar.MONTH) + 1;
                int actualDay = actualDate.get(Calendar.DAY_OF_MONTH);

                for (DataSnapshot reserveSnapshot: snapshot.getChildren()) {
                    Reserve reserve = reserveSnapshot.getValue(Reserve.class);

                    String reserveDate = reserve.getDate();
                    int reserveYear = Integer.parseInt(reserveDate.substring(6, 10));
                    int reserveMonth = Integer.parseInt(reserveDate.substring(3, 5));
                    int reserveDay = Integer.parseInt(reserveDate.substring(0, 2));

                    if (reserveYear > actualYear) {
                        reserves.add(reserve);
                    } else if (reserveYear == actualYear) {
                        if (reserveMonth > actualMonth) {
                            reserves.add(reserve);
                        } else if (reserveMonth == actualMonth) {
                            if (reserveDay > actualDay) {
                                reserves.add(reserve);
                            }
                        }
                    }

                    String key = reserveSnapshot.getKey();
                    keys.add(key);
                }
                reservesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Reserves.this, "Error", Toast.LENGTH_SHORT);
            }
        });
    }
}