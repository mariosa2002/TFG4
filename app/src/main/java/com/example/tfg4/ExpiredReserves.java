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
import java.util.Calendar;

public class ExpiredReserves extends AppCompatActivity {

    Button btnReserves;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reservesRef;
    Query query;

    private RecyclerView expiredReservesRecyclerView;
    private RecyclerView.Adapter expiredReservesAdapter;
    private RecyclerView.LayoutManager expiredReservesLayoutManager;
    public static ArrayList<Reserve> expiredReserves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expired_reserves);

        expiredReservesRecyclerView = findViewById(R.id.rvExpiredReserves);
        expiredReserves = new ArrayList<Reserve>();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String userID = user.getUid();
        database = FirebaseDatabase.getInstance();
        reservesRef = database.getReference("Reserves");
        query = reservesRef.orderByChild("userID").equalTo(userID);

        Recycler();

        btnReserves = findViewById(R.id.btnReserves);
        btnReserves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reserves.class);
                startActivity(intent);
            }
        });
    }

    private void Recycler() {
        expiredReservesLayoutManager = new LinearLayoutManager(this);
        expiredReservesRecyclerView.setLayoutManager(expiredReservesLayoutManager);
        expiredReservesAdapter = new ExpiredReserveAdapter(expiredReserves);
        expiredReservesRecyclerView.setAdapter(expiredReservesAdapter);
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

                    if (actualYear > reserveYear) {
                        expiredReserves.add(reserve);
                    } else if (actualYear == reserveYear) {
                        if (actualMonth > reserveMonth) {
                            expiredReserves.add(reserve);
                        } else if (actualMonth == reserveMonth) {
                            if (actualDay > reserveDay) {
                                expiredReserves.add(reserve);
                            }
                        }
                    }
                }
                expiredReservesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ExpiredReserves.this, "Error", Toast.LENGTH_SHORT);
            }
        });
    }
}