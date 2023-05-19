package com.example.tfg4;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpiredReserveAdapter extends RecyclerView.Adapter<ExpiredReserveAdapter.ExpiredReserveViewHolder> {

    private ArrayList<Reserve> expiredReserves;

    public static class ExpiredReserveViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        public TextView scName;
        public TextView scAddress;
        public TextView facilityName;
        public TextView DateHour;
        public TextView position;
        public Button btnWriteReview;

        public ExpiredReserveViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            scName = itemView.findViewById(R.id.scName);
            scAddress = itemView.findViewById(R.id.scAddress);
            facilityName = itemView.findViewById(R.id.facilityName);
            DateHour = itemView.findViewById(R.id.DateHour);
            position = itemView.findViewById(R.id.position);
            btnWriteReview = itemView.findViewById(R.id.btnWriteReview);
        }

        void setOnClickListeners() {
            btnWriteReview.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnWriteReview:
                    int aux = Integer.parseInt(position.getText().toString().trim());
                    Intent intent = new Intent (v.getContext(), WriteReview.class);
                    intent.putExtra("reserveKey", Reserves.keys.get(aux));
                    context.startActivity(intent);
            }
        }
    }

    public ExpiredReserveAdapter(ArrayList<Reserve> expiredReserves) {
        this.expiredReserves = expiredReserves;
    }

    @Override
    public ExpiredReserveAdapter.ExpiredReserveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expired_reserve, parent, false);
        ExpiredReserveAdapter.ExpiredReserveViewHolder viewHolder = new ExpiredReserveAdapter.ExpiredReserveViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ExpiredReserveAdapter.ExpiredReserveViewHolder holder, int position) {
        Reserve currentItem = expiredReserves.get(position);

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
        return expiredReserves.size();
    }
}
