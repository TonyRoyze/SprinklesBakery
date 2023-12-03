package com.example.sprinklesbakery.Classes.RecViewAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprinklesbakery.Classes.Cart;
import com.example.sprinklesbakery.Classes.Cake;
import com.example.sprinklesbakery.R;

import java.util.ArrayList;
import java.util.List;

public class HomePageRecViewAdapter extends RecyclerView.Adapter<HomePageRecViewAdapter.ViewHolder> {

    List<Cake> cakes = new ArrayList<>();

    Context context;
    public HomePageRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cake_item, parent, false);
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

        if (!Cart.cart.isEmpty()) {
            for (Cart c : Cart.cart) {
                if ((c.getCake().getId()) == (cake.getId())) {
                    holder.btnStepperText.setText(String.valueOf(c.getQuantity()));
                }
            }
        }

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
        ImageView cakeImg, btnStepperAdd, btnStepperRemove;
        TextView cakeName, cakeDesc, cakePrice, quantity, btnStepperText;
        Cake cake;
        HomePageRecViewAdapter adapter;
        int count = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cakeCard = itemView.findViewById(R.id.cake_card);
            cakeImg = itemView.findViewById(R.id.item_img);
            cakeName = itemView.findViewById(R.id.cake_name);
            cakeDesc = itemView.findViewById(R.id.cake_desc);
            cakePrice = itemView.findViewById(R.id.cake_price);
            quantity = itemView.findViewById(R.id.quantity);
            btnStepperText = itemView.findViewById(R.id.btn_stepper_text);
            btnStepperAdd = itemView.findViewById(R.id.btn_stepper_add);
            btnStepperRemove = itemView.findViewById(R.id.btn_stepper_remove);



            btnStepperAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (count < cake.getQuantity()) {
                        btnStepperText.setText(String.valueOf(++count));
                        Cart.addToCart(cake);
                    }
                }
            });

            btnStepperRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (count != 0) {
                        btnStepperText.setText(String.valueOf(--count));
                        Cart.removeFromCart(cake);
                    }
                }
            });

        }

        public ViewHolder linkAdapter(HomePageRecViewAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }

    public void setCakes(List<Cake> cakes) {
        this.cakes = cakes;
        notifyDataSetChanged();
    }
}
