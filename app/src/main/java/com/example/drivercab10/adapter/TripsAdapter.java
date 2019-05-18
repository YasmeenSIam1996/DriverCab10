package com.example.drivercab10.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drivercab10.API.ResponseHistoryTrips;
import com.example.drivercab10.OrderDetailsActivity;
import com.example.drivercab10.R;

import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.MyViewHolder> {
    Context context;
    List<ResponseHistoryTrips.Request> list;

    public TripsAdapter(Context context, List<ResponseHistoryTrips.Request> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trip, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final ResponseHistoryTrips.Request request = list.get(i);
        myViewHolder.tvFirstPoint.setText(request.getS_address());
        myViewHolder.tvEndPoint.setText(request.getD_address());
        myViewHolder.tvDistance.setText(request.getDistance_travel() + request.getDistance_unit());
        myViewHolder.tvOrderTime.setText(request.getDate());
        myViewHolder.tvOrderCarType.setText(request.getTaxi_name());
        myViewHolder.currencyType.setText(request.getCurrency());
        myViewHolder.tvPrice.setText(request.getTotal() + "");
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("RequestId",request.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderTime, tvOrderCarType, tvFirstPoint, tvEndPoint, tvDistance, currencyType, tvPrice;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderTime = itemView.findViewById(R.id.tvOrderTime);
            tvOrderCarType = itemView.findViewById(R.id.tvOrderCarType);
            tvFirstPoint = itemView.findViewById(R.id.tvFirstPoint);
            tvEndPoint = itemView.findViewById(R.id.tvEndPoint);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            currencyType = itemView.findViewById(R.id.currencyType);
            tvPrice = itemView.findViewById(R.id.tvPrice);

        }
    }
}
