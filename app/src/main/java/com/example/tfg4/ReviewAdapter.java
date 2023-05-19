package com.example.tfg4;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<Review> reviews;

    FirebaseDatabase database;
    DatabaseReference reservesRef;
    DatabaseReference userRef;
    Query query;
    Query query2;

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        Context context;
        public TextView userName;
        public TextView scName;
        public TextView scAddress;
        public TextView facilityName;
        public TextView txtText;
        public RatingBar ratingBar;
        public TextView DateHour;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            userName = itemView.findViewById(R.id.userName);
            scName = itemView.findViewById(R.id.scName);
            scAddress = itemView.findViewById(R.id.scAddress);
            facilityName = itemView.findViewById(R.id.facilityName);
            txtText = itemView.findViewById(R.id.txtText);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            DateHour = itemView.findViewById(R.id.DateHour);
        }
    }

    public ReviewAdapter(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        ReviewAdapter.ReviewViewHolder viewHolder = new ReviewAdapter.ReviewViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewViewHolder holder, int position) {
        Review currentItem = reviews.get(position);
        String reserveID = currentItem.getReserveID();

        database = FirebaseDatabase.getInstance();
        reservesRef = database.getReference("Reserves");
        query = reservesRef.child(reserveID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Reserve reserve = snapshot.getValue(Reserve.class);
                String userID = reserve.getUserID();
                holder.scName.setText(reserve.getScName());
                holder.scAddress.setText(reserve.getScAddress());
                holder.facilityName.setText(reserve.getFacilityName());
                userRef = FirebaseDatabase.getInstance().getReference("Users");
                query2 = userRef.child(userID);
                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        holder.userName.setText("@"+user.getUserUserName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //error
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error
            }
        });

        holder.txtText.setText(currentItem.getText());
        holder.ratingBar.setRating(currentItem.getStars());
        holder.DateHour.setText(currentItem.getDateHour());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
