package com.example.drivercab10.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drivercab10.API.ResponseBill;
import com.example.drivercab10.OrderDetailsActivity;
import com.example.drivercab10.R;

import java.util.List;

public class FinanceAdapter extends RecyclerView.Adapter<FinanceAdapter.MyViewHolder> {
    Context context;
    List<ResponseBill.BillObject> list;

    public FinanceAdapter(Context context, List<ResponseBill.BillObject> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_finance, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final ResponseBill.BillObject billObject = list.get(i);
        if (i % 2 == 0)
            myViewHolder.itemView.setBackgroundResource(R.color.colorGreyLight);
        else
            myViewHolder.itemView.setBackgroundResource(android.R.color.white);

        myViewHolder.txtDate.setText(billObject.getDate());
        myViewHolder.txtId.setText(billObject.getId() + "");
        myViewHolder.txtName.setText(billObject.getUser_name());
        myViewHolder.txtPrice.setText(billObject.getTotal() + " " + billObject.getCurrency());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("RequestId",billObject.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtPrice, txtName, txtDate, txtId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtName = itemView.findViewById(R.id.txtName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtId = itemView.findViewById(R.id.txtId);
        }
    }
}
