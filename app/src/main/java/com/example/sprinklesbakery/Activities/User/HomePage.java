package com.example.sprinklesbakery.Activities.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprinklesbakery.Classes.Cart;
import com.example.sprinklesbakery.Classes.Cake;
import com.example.sprinklesbakery.Classes.RecViewAdapters.HomePageRecViewAdapter;
import com.example.sprinklesbakery.Database.DatabaseHelper;
import com.example.sprinklesbakery.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomePage extends AppCompatActivity {

    private FloatingActionButton btnCart;
    private ImageView backIcon;
    private RecyclerView cakeList;
    private HorizontalScrollView chips;
    private int id;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        btnCart = findViewById(R.id.ic_cart);
        backIcon = findViewById(R.id.ic_back_arrow);
        cakeList = findViewById(R.id.cake_list_rec_view);
        chips = findViewById(R.id.chip_scroll_view);

        chips.setVisibility(View.GONE);

        List<Cake> cakes = defaultCakeList();
        HomePageRecViewAdapter homePageRecViewAdapter = new HomePageRecViewAdapter(this);
        homePageRecViewAdapter.setCakes(cakes);
        cakeList.setAdapter(homePageRecViewAdapter);
        cakeList.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(HomePage.this, LoginPage.class);
                startActivity(login);
                Cart.cart.clear();
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(HomePage.this, CartPage.class);
                cart.putExtra("id", id);
                startActivity(cart);
            }
        });
        
    }

    public List<Cake> defaultCakeList() {
        DatabaseHelper databaseHelper = new DatabaseHelper(HomePage.this);
        return databaseHelper.getAllCakes();
    }
}