package com.example.tfg4;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.FacilityViewHolder> {

    private ArrayList<Facility> facilities;

    public static class FacilityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        public ImageView imageView;
        public TextView facilityName;
        public Button btnSelect;

        public FacilityViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            context = itemView.getContext();
            facilityName = itemView.findViewById(R.id.facilityName);
            btnSelect = itemView.findViewById(R.id.btnSelect);
        }

        void setOnClickListeners() {
            btnSelect.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnSelect:
                    Intent intent = new Intent (v.getContext(), DateHour.class);
                    intent.putExtra("facilityName", facilityName.getText());
                    context.startActivity(intent);
            }
        }
    }

    public FacilityAdapter(ArrayList<Facility> facilities) {
        this.facilities = facilities;
    }

    @Override
    public FacilityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facility, parent, false);
        FacilityViewHolder viewHolder = new FacilityViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FacilityViewHolder holder, int position) {
        Facility currentItem = facilities.get(position);

        holder.facilityName.setText(currentItem.getName());
        holder.imageView.setImageResource(currentItem.getImage());

        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return facilities.size();
    }
}
