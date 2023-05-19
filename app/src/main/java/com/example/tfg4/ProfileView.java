package com.example.tfg4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileView extends AppCompatActivity {

    EditText txtName, txtSurname, txtUserName;
    Button btnBack, btnEdit;

    FirebaseDatabase database;
    DatabaseReference profileRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        database = FirebaseDatabase.getInstance();
        profileRef = database.getReference("Users");

        Bundle extras = getIntent().getExtras();
        String userID = extras.getString("userID");
        String userName = extras.getString("userName");
        String userSurname = extras.getString("userSurname");
        String userUserName = extras.getString("userUserName");

        profileRef = profileRef.child(userID);

        txtName = findViewById(R.id.txtName);
        txtSurname = findViewById(R.id.txtSurname);
        txtUserName = findViewById(R.id.txtUserName);

        txtName.setText(userName);
        txtSurname.setText(userSurname);
        txtUserName.setText(userUserName);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });

        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> update = new HashMap<>();
                update.put("userName", txtName.getText().toString().trim());
                update.put("userSurname", txtSurname.getText().toString().trim());
                update.put("userUserName", txtUserName.getText().toString().trim());
                profileRef.updateChildren(update);
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }
}