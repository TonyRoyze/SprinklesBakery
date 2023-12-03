package com.example.sprinklesbakery.Activities.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprinklesbakery.Classes.Category;
import com.example.sprinklesbakery.Database.DatabaseHelper;
import com.example.sprinklesbakery.R;

import java.util.List;

public class AddEditCat extends AppCompatActivity {

    private ImageView backIcon;
    private Button btnAdd;
    private EditText catName, catDesc;
    private int id;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_cat);

        backIcon = findViewById(R.id.ic_back_arrow);
        btnAdd = findViewById(R.id.btnAddCat);
        catName = findViewById(R.id.cat_name);
        catDesc = findViewById(R.id.cat_desc);

        DatabaseHelper databaseHelper = new DatabaseHelper(AddEditCat.this);
        List<Category> categories = databaseHelper.getAllCategories();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        if (id >= 0) {
            for (Category cat : categories) {
                if (cat.getId() == id) {
                    category = cat;
                }
            }
            catName.setText(category.getCatName());
            catDesc.setText(category.getCatDesc());
            btnAdd.setText("Update");
        }

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditCat.this, AdminDash.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(AddEditCat.this);
                if (id >= 0) {
                    Category category = null;
                    try {
                        category = new Category(id, catName.getText().toString(), catDesc.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(AddEditCat.this, "Fields Cannot be Left Empty", Toast.LENGTH_SHORT).show();
                    }

                    boolean success = databaseHelper.updateCategory(category);
                    if (success) {
                        Toast.makeText(AddEditCat.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                        moveToDash();
                    } else {
                        Toast.makeText(AddEditCat.this, "Error Updating Data", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Category category = null;
                    try {
                        category = new Category(-1, catName.getText().toString(), catDesc.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(AddEditCat.this, "Fields Cannot be Left Empty", Toast.LENGTH_SHORT).show();
                    }


                    boolean success = databaseHelper.insertCategory(category);
                    if (success) {
                        Toast.makeText(AddEditCat.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddEditCat.this, "Error Inserting Data", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void moveToDash() {
        Intent intent = new Intent(AddEditCat.this, AdminDash.class);
        startActivity(intent);
    }

}