package com.example.sprinklesbakery.Activities.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprinklesbakery.Classes.Cart;
import com.example.sprinklesbakery.Classes.Order;
import com.example.sprinklesbakery.Database.DatabaseHelper;
import com.example.sprinklesbakery.R;

public class OrderPage extends AppCompatActivity {

    private ImageView backIcon;
    private Button btnAdd;
    private TextView orderUserId;
    private EditText orderAddress, orderItems, orderTotal;
    private int id;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        backIcon = findViewById(R.id.ic_back_arrow);
        btnAdd = findViewById(R.id.btnAddOrder);
        orderUserId =findViewById(R.id.order_user_id);
        orderAddress =findViewById(R.id.order_address);
        orderItems =findViewById(R.id.order_items);
        orderTotal =findViewById(R.id.order_total);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String items = extras.getString("items");
        double total = extras.getDouble("total");
        id = intent.getIntExtra("id", -1);

        if (id >= 0) {
            
            orderUserId.setText("User ID : " + String.valueOf(id));
            orderItems.setText(items);
            orderTotal.setText(String.valueOf(total));
        }

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToCart();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(OrderPage.this);
                Order order = null;
                try {
                    order = new Order(-1, id, orderAddress.getText().toString(), orderItems.getText().toString(),  Double.parseDouble(orderTotal.getText().toString()));
                } catch (Exception e) {
                    Toast.makeText(OrderPage.this, "Fields Cannot be Left Empty", Toast.LENGTH_SHORT).show();
                }


                boolean success = databaseHelper.insertOrder(order);
                if (success) {
                    Toast.makeText(OrderPage.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderPage.this, "Error Inserting Data", Toast.LENGTH_SHORT).show();
                }
                Cart.cart.clear();
                moveToHome();
            }
        });

    }
    private void moveToCart() {

        Intent intent = new Intent(OrderPage.this, CartPage.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
    private void moveToHome() {

        Intent intent = new Intent(OrderPage.this, HomePage.class);
        startActivity(intent);
    }
}