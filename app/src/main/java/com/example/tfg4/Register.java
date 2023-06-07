package com.example.tfg4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class Register extends AppCompatActivity {

    EditText txtName, txtSurname, txtEmail, txtUserName, txtPassword, txtPassword2;
    Button btnRegister, btnImg;
    TextView txtLogin;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase, userRfef;
    StorageReference mStorage;
    Query query;

    String img = "";

    ArrayList<User> users;

    private static final int galleryIntent = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();

        userRfef = FirebaseDatabase.getInstance().getReference("Users");
        users = new ArrayList<User>();

        btnImg = findViewById(R.id.btnImg);
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
                        query = userRfef.orderByChild("userID");
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                if (user.getUserUserName().equals(txtUserName)) {
                                    users.add(user);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        if (users.size() == 0) {
                            mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                String userID = mAuth.getCurrentUser().getUid();
                                                writeNewUser(userID, userName, userSurname, userEmail, userUserName, img);
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
                            Toast.makeText(Register.this, "Nombre de usuario no disponible", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Register.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, galleryIntent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == galleryIntent && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            StorageReference filePath = mStorage.child("UsersIMG").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    img = uri.getLastPathSegment();
                }
            });
        }
    }

    private void writeNewUser(String userID, String userName, String userSurname, String userEmail, String userUserName, String img) {
        User user = new User(userName, userSurname, userEmail, userUserName, img);
        mDatabase.child("Users").child(userID).setValue(user);
    }
}