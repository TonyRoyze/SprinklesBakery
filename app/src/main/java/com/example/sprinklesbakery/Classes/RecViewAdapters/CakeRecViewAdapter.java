package com.example.sprinklesbakery.Classes.RecViewAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprinklesbakery.Activities.Admin.AddEditCake;
import com.example.sprinklesbakery.Classes.Cake;
import com.example.sprinklesbakery.Database.DatabaseHelper;
import com.example.sprinklesbakery.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CakeRecViewAdapter extends RecyclerView.Adapter<CakeRecViewAdapter.ViewHolder> {

    List<Cake> cakes = new ArrayList<>();
    Context context;
    public CakeRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_cake_item, parent, false);
        ViewHolder holder = new ViewHolder(view).linkAdapter(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Cake cake =  cakes.get(position);

        holder.cakeName.setText(cakes.get(position).getCakeName());
        holder.cakeDesc.setText(cakes.get(position).getCakeDesc());
        holder.cakePrice.setText("$ " + cakes.get(position).getPrice() + "0");
        holder.quantity.setText("Left : " + cakes.get(position).getQuantity());
        holder.cake = cake;

        int imageResourceId = context.getResources().getIdentifier(cake.getImgName(), "mipmap", context.getPackageName());

        if (imageResourceId != 0) {
            holder.cakeImg.setImageResource(imageResourceId);
        } else {
            holder.cakeImg.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return cakes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cakeCard;
        FloatingActionButton btnEdit;
        ImageView cakeImg;
        TextView cakeName, cakeDesc, cakePrice, quantity;
        Cake cake;
        CakeRecViewAdapter adapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cakeCard = itemView.findViewById(R.id.cake_card);
            cakeImg = itemView.findViewById(R.id.item_img);
            cakeName = itemView.findViewById(R.id.cake_name);
            cakeDesc = itemView.findViewById(R.id.cake_desc);
            cakePrice = itemView.findViewById(R.id.cake_price);
            quantity = itemView.findViewById(R.id.quantity);
            btnEdit = itemView.findViewById(R.id.ic_edit);

            btnEdit.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    boolean success = databaseHelper.deleteCake(cake);
                    if (success){
                        Toast.makeText(context, cake.getCakeName() + " Deleted Successfully", Toast.LENGTH_SHORT).show();
                        adapter.cakes.remove(getAdapterPosition());
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(context, cake.getCakeName() + " Delete Failed", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddEditCake.class);
                    intent.putExtra("id", cake.getId());
                    context.startActivity(intent);
                }
            });

        }

        public ViewHolder linkAdapter(CakeRecViewAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }

    public void setCakes(List<Cake> cakes) {
        this.cakes = cakes;
        notifyDataSetChanged();
    }
}
