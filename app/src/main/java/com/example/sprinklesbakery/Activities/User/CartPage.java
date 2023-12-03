package com.example.sprinklesbakery.Activities.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprinklesbakery.Classes.Cake;
import com.example.sprinklesbakery.Classes.Cart;
import com.example.sprinklesbakery.Classes.RecViewAdapters.CartPageRecViewAdapter;
import com.example.sprinklesbakery.Database.DatabaseHelper;
import com.example.sprinklesbakery.R;

public class CartPage extends AppCompatActivity {
    private Button btnPay;
    private ImageView backIcon;
    private RecyclerView cartList;
    public static TextView total;
    private int id;
    private double cartTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        DatabaseHelper databaseHelper = new DatabaseHelper(CartPage.this);

        btnPay = findViewById(R.id.btn_pay_now);
        backIcon = findViewById(R.id.ic_back_arrow);
        cartList = findViewById(R.id.cart_list_rec_view);
        total = findViewById(R.id.total);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        cartTotal = Cart.getCartTotal();
        total.setText("$ " + cartTotal + "0");
        
        CartPageRecViewAdapter cartPageRecViewAdapter = new CartPageRecViewAdapter(this);
        cartList.setAdapter(cartPageRecViewAdapter);
        cartList.setLayoutManager(new LinearLayoutManager(this));

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(CartPage.this, HomePage.class);
                cart.putExtra("id",id);
                startActivity(cart);
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartPage.this, OrderPage.class);
                Bundle extras = new Bundle();
                extras.putInt("id",id);
                extras.putDouble("total", cartTotal);

                String items = "";
                for (Cart c : Cart.cart) {
                    items += c.getCake().getCakeName() + ":" + c.getQuantity() + ", ";
                    Cake cake = null;
                    int qty = c.getCake().getQuantity() - c.getQuantity();
                    try {
                        cake = new Cake(c.getCake().getId(), c.getCake().getCakeName(), c.getCake().getCakeDesc(), c.getCake().getImgName(), c.getCake().getPrice(), qty, c.getCake().getCatId());
                    } catch (Exception e) {
                        Toast.makeText(CartPage.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                    boolean success = databaseHelper.updateCake(cake);
                }

                extras.putString("items", items);

                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        
        
    }

}