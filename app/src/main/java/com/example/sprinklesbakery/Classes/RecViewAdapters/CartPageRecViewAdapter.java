package com.example.sprinklesbakery.Classes.RecViewAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprinklesbakery.Activities.User.CartPage;
import com.example.sprinklesbakery.Classes.Cart;
import com.example.sprinklesbakery.Classes.Cake;
import com.example.sprinklesbakery.R;

public class CartPageRecViewAdapter extends RecyclerView.Adapter<CartPageRecViewAdapter.ViewHolder>{


    Context context;
    double total = 0;
    public CartPageRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        ViewHolder holder = new ViewHolder(view).linkAdapter(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Cake cake = Cart.cart.get(position).getCake();
        int quantity = Cart.cart.get(position).getQuantity();
        holder.cakeName.setText(cake.getCakeName());
        holder.cakePrice.setText("$ " + String.valueOf(cake.getPrice()) + "0");
        holder.btnStepperText.setText(String.valueOf(quantity));
        holder.cake = cake;
        holder.count = quantity;

        double item_total = cake.getPrice()*quantity;
        holder.cakeTotal.setText("$ " + String.valueOf(item_total) + "0");

    }

    @Override
    public int getItemCount() {
        return Cart.cart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout cartCard;
        ImageView btnStepperAdd, btnStepperRemove;
        TextView cakeName, cakePrice, cakeTotal, btnStepperText;
        Cake cake;
        CartPageRecViewAdapter adapter;
        int count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartCard = itemView.findViewById(R.id.cart_card);
            cakeName = itemView.findViewById(R.id.cake_name);
            cakeTotal = itemView.findViewById(R.id.item_total);
            cakePrice = itemView.findViewById(R.id.price);
            btnStepperText = itemView.findViewById(R.id.btn_stepper_text);
            btnStepperAdd = itemView.findViewById(R.id.btn_stepper_add);
            btnStepperRemove = itemView.findViewById(R.id.btn_stepper_remove);


            btnStepperAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnStepperText.setText(String.valueOf(++count));
                    Cart.addToCart(cake);

                    double item_total = cake.getPrice()*count;
                    cakeTotal.setText("$ " + String.valueOf(item_total) + "0");
                    double cartTotal = Cart.getCartTotal();
                    CartPage.total.setText("$ " + cartTotal + "0");

                }
            });

            btnStepperRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (count != 0) {
                        btnStepperText.setText(String.valueOf(--count));
                        Cart.removeFromCart(cake);

                        double item_total = cake.getPrice()*count;
                        cakeTotal.setText("$ " + String.valueOf(item_total) + "0");
                        double cartTotal = Cart.getCartTotal();
                        CartPage.total.setText("$ " + cartTotal + "0");
                    }
                }
            });

        }

        public ViewHolder linkAdapter(CartPageRecViewAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }

}
