package com.example.tfg4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Facilities extends AppCompatActivity {

    private RecyclerView facilityRecyclerView;
    private RecyclerView.Adapter facilityAdapter;
    private RecyclerView.LayoutManager facilityLayoutManager;
    public static ArrayList<Facility> facilities;
    public static int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilities);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String value = extras.getString("scName");
            boolean found = false;
            do {
                if (value.equals(SportsCenters.sportsCenters.get(position).getName())) {
                    found = true;
                } else {
                    position++;
                }
            } while (position < SportsCenters.sportsCenters.size() && !found);
        }

        TextView scName = findViewById(R.id.scName);
        TextView scAddress = findViewById(R.id.scAddress);
        ImageView imageView = findViewById(R.id.image_view);

        scName.setText(SportsCenters.sportsCenters.get(position).getName());
        scAddress.setText(SportsCenters.sportsCenters.get(position).getAddress());
        imageView.setImageResource(SportsCenters.sportsCenters.get(position).getImage());

        facilities = new ArrayList<Facility>();
        facilities.add(new Facility("Pista de Pádel", R.drawable.f1));
        facilities.add(new Facility("Campo de Fútbol Hierba", R.drawable.f2));
        facilities.add(new Facility("Pista de Tenis", R.drawable.f3));
        facilities.add(new Facility("Pista de Fútbol Sala", R.drawable.f4));
        facilities.add(new Facility("Pista de Baloncesto", R.drawable.f5));
        facilities.add(new Facility("Pista de Voleibol", R.drawable.f6));
        facilities.add(new Facility("Pista de Bádminton", R.drawable.f7));
        facilities.add(new Facility("Pabellón", R.drawable.f8));

        facilityLayoutManager = new GridLayoutManager(this, 1);
        facilityAdapter = new FacilityAdapter(facilities);
        facilityRecyclerView = findViewById(R.id.rvFacilities);
        facilityRecyclerView.setLayoutManager(facilityLayoutManager);
        facilityRecyclerView.setAdapter(facilityAdapter);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SportsCenters.class);
                startActivity(intent);
            }
        });
    }
}