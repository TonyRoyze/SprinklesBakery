package com.example.sprinklesbakery.Classes.RecViewAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprinklesbakery.Activities.Admin.AddEditOrder;
import com.example.sprinklesbakery.Classes.Order;
import com.example.sprinklesbakery.Database.DatabaseHelper;
import com.example.sprinklesbakery.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class OrderRecViewAdapter extends RecyclerView.Adapter<OrderRecViewAdapter.ViewHolder> {

    List<Order> orders = new ArrayList<>();
    Context context;
    public OrderRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_order_item, parent, false);
        ViewHolder holder = new ViewHolder(view).linkAdapter(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Order order =  orders.get(position);

        holder.orderUserId.setText(String.valueOf(orders.get(position).getUserId()));
        holder.orderAddress.setText(orders.get(position).getAddress());
        holder.orderItems.setText(orders.get(position).getItems());
        holder.orderTotal.setText("$ " + orders.get(position).getTotal() + "0");
        holder.order = order;
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout orderCard;
        FloatingActionButton btnEdit;
        TextView orderUserId, orderAddress, orderItems, orderTotal;
        Order order;
        OrderRecViewAdapter adapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderCard = itemView.findViewById(R.id.order_card);
            orderUserId = itemView.findViewById(R.id.order_user_id);
            orderAddress = itemView.findViewById(R.id.order_address);
            orderItems = itemView.findViewById(R.id.order_items);
            orderTotal = itemView.findViewById(R.id.order_total);
            btnEdit = itemView.findViewById(R.id.ic_edit);

            btnEdit.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent intent = new Intent(context, AddEditOrder.class);
                    intent.putExtra("id", order.getId());
                    context.startActivity(intent);

                    return true;
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    boolean success = databaseHelper.deleteOrder(order);
                    if (success){
                        Toast.makeText(context, order.getUserId() + " Deleted Successfully", Toast.LENGTH_SHORT).show();
                        adapter.orders.remove(getAdapterPosition());
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(context, order.getUserId() + " Delete Failed", Toast.LENGTH_SHORT).show();
                    }


                }
            });

        }

        public ViewHolder linkAdapter(OrderRecViewAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }
}
