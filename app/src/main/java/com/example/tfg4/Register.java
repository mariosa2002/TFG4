package com.example.tfg4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText txtName, txtSurname, txtEmail, txtUserName, txtPassword, txtPassword2;
    Button btnRegister;
    TextView txtLogin;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);
        txtName = findViewById(R.id.txtName);
        txtSurname = findViewById(R.id.txtSurname);
        txtEmail = findViewById(R.id.txtEmail);
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        txtPassword2 = findViewById(R.id.txtPassword2);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = txtName.getText().toString().trim();
                String userSurname = txtSurname.getText().toString().trim();
                String userEmail = txtEmail.getText().toString().trim();
                String userUserName = txtUserName.getText().toString().trim();
                String userPassword = txtPassword.getText().toString().trim();
                String userPassword2 = txtPassword2.getText().toString().trim();

                if (userName.isEmpty() || userSurname.isEmpty() || userEmail.isEmpty() || userUserName.isEmpty() || userPassword.isEmpty() || userPassword2.isEmpty()) {
                    Toast.makeText(Register.this, "Los campos deben de estar rellenos", Toast.LENGTH_SHORT).show();
                } else {
                    if (userPassword.equals(userPassword2)) {
                        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            String userID = mAuth.getCurrentUser().getUid();
                                            writeNewUser(userID, userName, userSurname, userEmail, userUserName);
                                            Intent intent = new Intent (getApplicationContext(), Login.class);
                                            startActivity(intent);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(Register.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(Register.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Login.class);
                startActivity(intent);
            }
        });
    }

    private void writeNewUser(String userID, String userName, String userSurname, String userEmail, String userUserName) {
        User user = new User(userName, userSurname, userEmail, userUserName);
        mDatabase.child("Users").child(userID).setValue(user);
    }
}