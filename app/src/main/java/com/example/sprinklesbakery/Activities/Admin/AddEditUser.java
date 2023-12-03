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

import com.example.sprinklesbakery.Classes.User;
import com.example.sprinklesbakery.Database.DatabaseHelper;
import com.example.sprinklesbakery.R;

import java.util.ArrayList;
import java.util.List;

public class AddEditUser extends AppCompatActivity {

    private ImageView backIcon;
    private Button btnAdd;
    private Spinner roleSpinner;
    private EditText userName, userUserName, userEmail, userPass, userRePass;
    private int id;
    private User user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_user);

        backIcon = findViewById(R.id.ic_back_arrow);
        btnAdd = findViewById(R.id.btnAddUser);
        userName = findViewById(R.id.user_name);
        roleSpinner = findViewById(R.id.user_role);
        userUserName = findViewById(R.id.user_username);
        userEmail = findViewById(R.id.user_email);
        userPass = findViewById(R.id.password);
        userRePass = findViewById(R.id.re_password);

        DatabaseHelper databaseHelper = new DatabaseHelper(AddEditUser.this);
        List<User> users = databaseHelper.getAllUsers();
        List<String> roles = new ArrayList<>();
        roles.add("Admin");
        roles.add("User");
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(this,R.layout.spinner_list, roles);
        roleSpinner.setAdapter(roleAdapter);


        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        if (id >= 0) {
            for (User u : users) {
                if (u.getId() == id) {
                    user = u;
                }
            }
            userName.setText(user.getName());
            userUserName.setText(user.getUsername());
            userEmail.setText(user.getEmail());
            userPass.setText(user.getPassword());
            userRePass.setText(user.getPassword());
            btnAdd.setText("Update");
        }

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditUser.this, AdminDash.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(AddEditUser.this);
                if (id >= 0) {
                    User user = null;

                    String pass = userPass.getText().toString();
                    String repass = userRePass.getText().toString();
                    if (pass.equals(repass)) {
                        user = new User(id, userName.getText().toString(), roleSpinner.getSelectedItem().toString(), userUserName.getText().toString(), userEmail.getText().toString(), userPass.getText().toString());

                        boolean success = databaseHelper.updateUser(user);
                        if (success) {
                            Toast.makeText(AddEditUser.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                            moveToDash();
                        } else {
                            Toast.makeText(AddEditUser.this, "Error Updating Data", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(AddEditUser.this, "Re-typed password does not match password", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    User user = null;
                    try {
                        String pass = userPass.getText().toString();
                        String repass = userRePass.getText().toString();
                        if (pass.equals(repass)) {
                            user = new User(-1, userName.getText().toString(), roleSpinner.getSelectedItem().toString(), userUserName.getText().toString(), userEmail.getText().toString(), userPass.getText().toString());

                            boolean success = databaseHelper.insertUser(user);
                            if (success) {
                                Toast.makeText(AddEditUser.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddEditUser.this, "Error Inserting Data", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(AddEditUser.this, "Re-typed password does not match password", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(AddEditUser.this, "Fields Cannot be Left Empty", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
    
    private void moveToDash() {
        Intent intent = new Intent(AddEditUser.this, AdminDash.class);
        startActivity(intent);
    }
}