package com.example.sprinklesbakery.Activities.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprinklesbakery.Classes.Order;
import com.example.sprinklesbakery.Database.DatabaseHelper;
import com.example.sprinklesbakery.R;

import java.util.List;

public class AddEditOrder extends AppCompatActivity {

    private ImageView backIcon;
    private Button btnAdd;
    private EditText orderUserId, orderAddress, orderItems, orderTotal;
    private int id;
    private Order order;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_order);

        backIcon = findViewById(R.id.ic_back_arrow);
        btnAdd = findViewById(R.id.btnAddOrder);
        orderUserId =findViewById(R.id.order_user_id);
        orderAddress =findViewById(R.id.order_address);
        orderItems =findViewById(R.id.order_items);
        orderTotal =findViewById(R.id.order_total);
        
        DatabaseHelper databaseHelper = new DatabaseHelper(AddEditOrder.this);
        List<Order> orders = databaseHelper.getAllOrders();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        
        if (id >=0) {
            for (Order o : orders) {
                if (o.getId() == id) {
                    order = o;
                }
            }
            orderUserId.setText(String.valueOf(order.getUserId()));
            orderAddress.setText(order.getAddress());
            orderItems.setText(order.getItems());
            orderTotal.setText(String.valueOf(order.getTotal()));
            btnAdd.setText("Update");
        }

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToDash();
            }
        });
        
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(AddEditOrder.this);
                if (id >= 0) {
                    Order order = null;
                    try {
                        order = new Order(id, Integer.parseInt(orderUserId.getText().toString()), orderAddress.getText().toString(), orderItems.getText().toString(),  Double.parseDouble(orderTotal.getText().toString()));
                    } catch (Exception e) {
                        Toast.makeText(AddEditOrder.this, "Fields Cannot be Left Empty", Toast.LENGTH_SHORT).show();
                    }

                    boolean success = databaseHelper.updateOrder(order);
                    if (success) {
                        Toast.makeText(AddEditOrder.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                        moveToDash();
                    } else {
                        Toast.makeText(AddEditOrder.this, "Error Updating Data", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Order order = null;
                    try {
                        order = new Order(-1, Integer.parseInt(orderUserId.getText().toString()), orderAddress.getText().toString(), orderItems.getText().toString(),  Double.parseDouble(orderTotal.getText().toString()));
                    } catch (Exception e) {
                        Toast.makeText(AddEditOrder.this, "Fields Cannot be Left Empty", Toast.LENGTH_SHORT).show();
                    }


                    boolean success = databaseHelper.insertOrder(order);
                    if (success) {
                        Toast.makeText(AddEditOrder.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddEditOrder.this, "Error Inserting Data", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void moveToDash() {
        Intent intent = new Intent(AddEditOrder.this, AdminDash.class);
        startActivity(intent);
    }
}