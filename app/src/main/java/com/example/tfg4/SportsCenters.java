package com.example.tfg4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class SportsCenters extends AppCompatActivity {

    private RecyclerView scRecyclerView;
    private RecyclerView.Adapter scAdapter;
    private RecyclerView.LayoutManager scLayoutManager;
    public static ArrayList<SportsCenter> sportsCenters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_centers);



        sportsCenters = new ArrayList<SportsCenter>();

        for (SportsCenter global : Maps.global) {
            sportsCenters.add(global);
        }

        scRecyclerView = findViewById(R.id.rvSportsCenters);
        scLayoutManager = new GridLayoutManager(this, 1);
        scAdapter = new SportsCenterAdapter(sportsCenters);

        scRecyclerView.setLayoutManager(scLayoutManager);
        scRecyclerView.setAdapter(scAdapter);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });
    }
}