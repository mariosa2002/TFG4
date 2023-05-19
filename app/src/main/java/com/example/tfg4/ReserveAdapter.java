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

public class ReserveAdapter extends RecyclerView.Adapter<ReserveAdapter.ReserveViewHolder> {

    private ArrayList<Reserve> reserves;

    public static class ReserveViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        public TextView scName;
        public TextView scAddress;
        public TextView facilityName;
        public TextView DateHour;
        public TextView position;
        public Button btnView;

        public ReserveViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            scName = itemView.findViewById(R.id.scName);
            scAddress = itemView.findViewById(R.id.scAddress);
            facilityName = itemView.findViewById(R.id.facilityName);
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
                    Intent intent = new Intent (v.getContext(), ReserveView.class);
                    intent.putExtra("reserveKey", Reserves.keys.get(aux));
                    context.startActivity(intent);
            }
        }
    }

    public ReserveAdapter(ArrayList<Reserve> reserves) {
        this.reserves = reserves;
    }

    @Override
    public ReserveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserve, parent, false);
        ReserveViewHolder viewHolder = new ReserveViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReserveViewHolder holder, int position) {
        Reserve currentItem = reserves.get(position);

        holder.scName.setText(currentItem.getScName());
        holder.scAddress.setText(currentItem.getScAddress());
        holder.facilityName.setText(currentItem.getFacilityName());
        holder.DateHour.setText(currentItem.getDateHour());
        String aux2 = String.valueOf(position);
        holder.position.setText(aux2);

        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return reserves.size();
    }
}
