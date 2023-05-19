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

public class SportsCenterAdapter extends RecyclerView.Adapter<SportsCenterAdapter.SportsCenterViewHolder> {

    private ArrayList<SportsCenter> sportsCenters;

    public static class SportsCenterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        public ImageView imageView;
        public TextView scName;
        public TextView scAddress;
        public Button btnSelect;

        public SportsCenterViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            context = itemView.getContext();
            scName = itemView.findViewById(R.id.scName);
            scAddress = itemView.findViewById(R.id.scAddress);
            btnSelect = itemView.findViewById(R.id.btnSelect);
        }

        void setOnClickListeners() {
            btnSelect.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnSelect:
                    Intent intent = new Intent (v.getContext(), Facilities.class);
                    intent.putExtra("scName", scName.getText());
                    context.startActivity(intent);
            }
        }
    }

    public SportsCenterAdapter(ArrayList<SportsCenter> sportsCenters) {
        this.sportsCenters = sportsCenters;
    }

    @Override
    public SportsCenterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sportscenter, parent, false);
        SportsCenterAdapter.SportsCenterViewHolder viewHolder = new SportsCenterAdapter.SportsCenterViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SportsCenterViewHolder holder, int position) {
        SportsCenter currentItem = sportsCenters.get(position);

        holder.scName.setText(currentItem.getName());
        holder.imageView.setImageResource(currentItem.getImage());
        holder.scAddress.setText(currentItem.getAddress());

        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return sportsCenters.size();
    }
}
