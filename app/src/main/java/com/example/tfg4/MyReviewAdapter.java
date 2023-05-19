package com.example.tfg4;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.MyReviewViewHolder> {

    private ArrayList<Review> myReviews;

    FirebaseDatabase database;
    DatabaseReference reservesRef;
    Query query;

    public static class MyReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        public TextView scName;
        public TextView scAddress;
        public TextView facilityName;
        public TextView txtText;
        public RatingBar ratingBar;
        public TextView DateHour;
        public TextView position;
        public Button btnView;

        public MyReviewViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            scName = itemView.findViewById(R.id.scName);
            scAddress = itemView.findViewById(R.id.scAddress);
            facilityName = itemView.findViewById(R.id.facilityName);
            txtText = itemView.findViewById(R.id.txtText);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            DateHour = itemView.findViewById(R.id.DateHour);
            position = itemView.findViewById(R.id.position);
            btnView = itemView.findViewById(R.id.btnView);
        }

        void setOnClickListeners() {
            btnView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnView:
                    int aux = Integer.parseInt(position.getText().toString().trim());
                    Intent intent = new Intent (v.getContext(), ReviewView.class);
                    intent.putExtra("reviewKey", MyReviews.reviewsKeys.get(aux));
                    context.startActivity(intent);
            }
        }
    }

    public MyReviewAdapter(ArrayList<Review> myReviews) {
        this.myReviews = myReviews;
    }

    @Override
    public MyReviewAdapter.MyReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_review, parent, false);
        MyReviewAdapter.MyReviewViewHolder viewHolder = new MyReviewAdapter.MyReviewViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyReviewAdapter.MyReviewViewHolder holder, int position) {
        Review currentItem = myReviews.get(position);
        String reserveID = currentItem.getReserveID();

        database = FirebaseDatabase.getInstance();
        reservesRef = database.getReference("Reserves");
        query = reservesRef.child(reserveID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Reserve reserve = snapshot.getValue(Reserve.class);
                holder.scName.setText(reserve.getScName());
                holder.scAddress.setText(reserve.getScAddress());
                holder.facilityName.setText(reserve.getFacilityName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error
            }
        });

        holder.txtText.setText(currentItem.getText());
        holder.ratingBar.setRating(currentItem.getStars());
        holder.DateHour.setText(currentItem.getDateHour());
        String aux2 = String.valueOf(position);
        holder.position.setText(aux2);

        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return myReviews.size();
    }
}
