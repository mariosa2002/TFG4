package com.example.tfg4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ProfileView extends AppCompatActivity {

    EditText txtName, txtSurname, txtUserName;
    ImageView imageView;
    Button btnBack, btnEdit, btnImg, btnImg2;

    FirebaseDatabase database;
    DatabaseReference profileRef;
    Query query;
    StorageReference storageReference, mStorage;

    String img = "";
    String imgAux = "";

    private static final int galleryIntent = 1;

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

        query = profileRef.child(userID);
        storageReference = FirebaseStorage.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        profileRef = profileRef.child(userID);

        txtName = findViewById(R.id.txtName);
        txtSurname = findViewById(R.id.txtSurname);
        txtUserName = findViewById(R.id.txtUserName);
        imageView = findViewById(R.id.imageView);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (!user.getImg().equals("")) {
                    img = user.getImg();
                    imgAux = user.getImg();
                    StorageReference filePath = storageReference.child("UsersIMG").child(user.getImg());
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(ProfileView.this).load(uri).fitCenter().centerCrop().into(imageView);
                        }
                    });
                } else {
                    imageView.setImageResource(R.drawable.profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                update.put("img", img);

                profileRef.updateChildren(update);
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });

        btnImg = findViewById(R.id.btnImg);
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, galleryIntent);
            }
        });

        btnImg2 = findViewById(R.id.btnImg2);
        btnImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img = "";
                imageView.setImageResource(R.drawable.profile);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == galleryIntent && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            StorageReference filePath2 = mStorage.child("UsersIMG").child(uri.getLastPathSegment());
            filePath2.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    img = uri.getLastPathSegment();
                    if (!img.equals(imgAux)) {
                        StorageReference filePath3 = storageReference.child("UsersIMG").child(img);
                        filePath3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(ProfileView.this).load(uri).fitCenter().centerCrop().into(imageView);
                            }
                        });
                    }
                }
            });
        }
    }
}