package com.example.sprinklesbakery.Activities.Admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprinklesbakery.Activities.User.LoginPage;
import com.example.sprinklesbakery.Classes.Category;
import com.example.sprinklesbakery.Classes.Cake;
import com.example.sprinklesbakery.Classes.Order;
import com.example.sprinklesbakery.Classes.RecViewAdapters.CatRecViewAdapter;
import com.example.sprinklesbakery.Classes.RecViewAdapters.CakeRecViewAdapter;
import com.example.sprinklesbakery.Classes.RecViewAdapters.OrderRecViewAdapter;
import com.example.sprinklesbakery.Classes.RecViewAdapters.UserRecViewAdapter;
import com.example.sprinklesbakery.Classes.User;
import com.example.sprinklesbakery.Database.DatabaseHelper;
import com.example.sprinklesbakery.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdminDash extends AppCompatActivity {

    private FloatingActionButton btnAdd;
    private RecyclerView cakeList, catList, userList, orderList;
    private Button btnCakes, btnCat, btnUsers, btnOrders;
    private ImageView backIcon;
    private int selectedType = 0, id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);

        backIcon = findViewById(R.id.ic_back_arrow);
        btnAdd = findViewById(R.id.ic_add);
        btnCakes = findViewById(R.id.btnCake);
        btnCat = findViewById(R.id.btnCat);
        btnUsers = findViewById(R.id.btnUser);
        btnOrders = findViewById(R.id.btnOrder);
        cakeList = findViewById(R.id.cake_list_rec_view);
        catList = findViewById(R.id.cat_list_rec_view);
        userList = findViewById(R.id.user_list_rec_view);
        orderList = findViewById(R.id.order_list_rec_view);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);


        List<Cake> cakes = defaultCakeList();
        CakeRecViewAdapter cakeRecViewAdapter = new CakeRecViewAdapter(this);
        cakeRecViewAdapter.setCakes(cakes);
        cakeList.setAdapter(cakeRecViewAdapter);
        cakeList.setLayoutManager(new LinearLayoutManager(this));

        List<Category> categories = defaultCatList();
        CatRecViewAdapter catRecViewAdapter = new CatRecViewAdapter(this);
        catRecViewAdapter.setCategories(categories);
        catList.setAdapter(catRecViewAdapter);
        catList.setLayoutManager(new LinearLayoutManager(this));

        List<User> users = defaultUserList();
        UserRecViewAdapter userRecViewAdapter = new UserRecViewAdapter(this);
        userRecViewAdapter.setUsers(users);
        userList.setAdapter(userRecViewAdapter);
        userList.setLayoutManager(new LinearLayoutManager(this));

        List<Order> orders = defaultOrderList();
        OrderRecViewAdapter orderRecViewAdapter = new OrderRecViewAdapter(this);
        orderRecViewAdapter.setOrders(orders);
        orderList.setAdapter(orderRecViewAdapter);
        orderList.setLayoutManager(new LinearLayoutManager(this));

        cakeList.setVisibility(View.VISIBLE);
        catList.setVisibility(View.GONE);
        userList.setVisibility(View.GONE);
        orderList.setVisibility(View.GONE);


        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(AdminDash.this, LoginPage.class);
                startActivity(login);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (selectedType) {
                    case 0:
                        Intent addCake = new Intent(AdminDash.this, AddEditCake.class);
                        startActivity(addCake);
                        break;

                    case 1:
                        Intent addCat = new Intent(AdminDash.this, AddEditCat.class);
                        startActivity(addCat);
                        break;

                    case 2:
                        Intent addUser = new Intent(AdminDash.this, AddEditUser.class);
                        startActivity(addUser);
                        break;

                    case 3:
                        Intent addOrder = new Intent(AdminDash.this, AddEditOrder.class);
                        startActivity(addOrder);
                        break;

                    default:
                        Toast.makeText(AdminDash.this, "No such type", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });

        btnCakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = 0;

                btnCakes.setBackgroundColor(getResources().getColor(R.color.selected));
                btnCat.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnUsers.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnOrders.setBackgroundColor(getResources().getColor(R.color.not_selected));

                cakeList.setVisibility(View.VISIBLE);
                catList.setVisibility(View.GONE);
                userList.setVisibility(View.GONE);
                orderList.setVisibility(View.GONE);
            }
        });

        btnCat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectedType = 1;

                btnCakes.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnCat.setBackgroundColor(getResources().getColor(R.color.selected));
                btnUsers.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnOrders.setBackgroundColor(getResources().getColor(R.color.not_selected));


                cakeList.setVisibility(View.GONE);
                catList.setVisibility(View.VISIBLE);
                userList.setVisibility(View.GONE);
                orderList.setVisibility(View.GONE);

            }
        });

        btnUsers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectedType = 2;

                btnCakes.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnCat.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnUsers.setBackgroundColor(getResources().getColor(R.color.selected));
                btnOrders.setBackgroundColor(getResources().getColor(R.color.not_selected));

                cakeList.setVisibility(View.GONE);
                catList.setVisibility(View.GONE);
                userList.setVisibility(View.VISIBLE);
                orderList.setVisibility(View.GONE);
            }
        });

        btnOrders.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectedType = 3;

                btnCakes.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnCat.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnUsers.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnOrders.setBackgroundColor(getResources().getColor(R.color.selected));

                cakeList.setVisibility(View.GONE);
                catList.setVisibility(View.GONE);
                userList.setVisibility(View.GONE);
                orderList.setVisibility(View.VISIBLE);
            }
        });

    }

    public List<Cake> defaultCakeList() {
        DatabaseHelper databaseHelper = new DatabaseHelper(AdminDash.this);
        return databaseHelper.getAllCakes();
    }
    public List<Category> defaultCatList() {
        DatabaseHelper databaseHelper = new DatabaseHelper(AdminDash.this);
        return databaseHelper.getAllCategories();
    }
    public List<User> defaultUserList() {
        DatabaseHelper databaseHelper = new DatabaseHelper(AdminDash.this);
        return databaseHelper.getAllUsers();
    }
    public List<Order> defaultOrderList() {
        DatabaseHelper databaseHelper = new DatabaseHelper(AdminDash.this);
        return databaseHelper.getAllOrders();
    }

}