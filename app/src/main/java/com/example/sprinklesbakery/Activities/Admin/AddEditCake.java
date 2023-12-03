package com.example.sprinklesbakery.Activities.Admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprinklesbakery.Classes.Category;
import com.example.sprinklesbakery.Classes.Cake;
import com.example.sprinklesbakery.Database.DatabaseHelper;
import com.example.sprinklesbakery.R;

import java.util.ArrayList;
import java.util.List;

public class AddEditCake extends AppCompatActivity {
    private Spinner categorySpinner;
    private ImageView backIcon;
    private Button btnAdd;
    private EditText cakeName, cakeDesc, price, quantity, imgName;
    private int id;
    private Cake cake;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_cake);

        categorySpinner = findViewById(R.id.cat_id);
        backIcon = findViewById(R.id.ic_back_arrow);
        btnAdd = findViewById(R.id.btnAdd);
        cakeName = findViewById(R.id.cake_name);
        cakeDesc = findViewById(R.id.cake_desc);
        price = findViewById(R.id.cake_price);
        quantity = findViewById(R.id.quantity);
        imgName = findViewById(R.id.img_name);

        DatabaseHelper databaseHelper = new DatabaseHelper(AddEditCake.this);
        List<Cake> cakes = databaseHelper.getAllCakes();
        List<Category> categories = databaseHelper.getAllCategories();
        List<String> categoryNames = new ArrayList<>();

        for (Category category : categories) {
            categoryNames.add(category.getCatName());
        }

        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this, R.layout.spinner_list, categoryNames);
        categorySpinner.setAdapter(catAdapter);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        if (id >= 0) {
            for (Cake f : cakes) {
                if (f.getId() == id) {
                    cake = f;
                }
            }
            cakeName.setText(cake.getCakeName());
            cakeDesc.setText(cake.getCakeDesc());
            price.setText(String.valueOf(cake.getPrice()));
            quantity.setText(String.valueOf(cake.getQuantity()));
            imgName.setText(cake.getImgName());
            btnAdd.setText("Update");
            categorySpinner.setSelection((int) cake.getCatId() - 1);
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
                DatabaseHelper databaseHelper = new DatabaseHelper(AddEditCake.this);
                if (id >= 0) {
                    Cake cake = null;
                    try {
                        cake = new Cake(id, cakeName.getText().toString(), cakeDesc.getText().toString(), imgName.getText().toString(), Double.parseDouble(price.getText().toString()), Integer.parseInt(quantity.getText().toString()), categorySpinner.getSelectedItemPosition() + 1);
                    } catch (Exception e) {
                        Toast.makeText(AddEditCake.this, "Fields Cannot be Left Empty", Toast.LENGTH_SHORT).show();
                    }

                    boolean success = databaseHelper.updateCake(cake);
                    if (success) {
                        Toast.makeText(AddEditCake.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                        moveToDash();
                    } else {
                        Toast.makeText(AddEditCake.this, "Error Updating Data", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Cake cake = null;
                    try {
                        cake = new Cake(-1, cakeName.getText().toString(), cakeDesc.getText().toString(), imgName.getText().toString(), Double.parseDouble(price.getText().toString()), Integer.parseInt(quantity.getText().toString()), categorySpinner.getSelectedItemPosition() + 1);
                    } catch (Exception e) {
                        Toast.makeText(AddEditCake.this, "Fields Cannot be Left Empty", Toast.LENGTH_SHORT).show();
                    }


                    boolean success = databaseHelper.insertCake(cake);
                    if (success) {
                        Toast.makeText(AddEditCake.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddEditCake.this, "Error Inserting Data", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }

    private void moveToDash() {
        Intent intent = new Intent(AddEditCake.this, AdminDash.class);
        startActivity(intent);
    }
}