package com.example.sprinklesbakery.Activities.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprinklesbakery.Activities.Admin.AdminDash;
import com.example.sprinklesbakery.Classes.User;
import com.example.sprinklesbakery.Database.DatabaseHelper;
import com.example.sprinklesbakery.R;

import java.util.List;

public class LoginPage extends AppCompatActivity {
    private Button btnLogin;
    private EditText userEmail, userPass;
    private TextView regLink;
    private int id;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        btnLogin = findViewById(R.id.btnAddUser);
        userEmail = findViewById(R.id.user_email);
        userPass = findViewById(R.id.password);
        regLink = findViewById(R.id.register_link);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String email = userEmail.getText().toString();
                    String pass = userPass.getText().toString();
                    boolean loginSuccess = false;

                    DatabaseHelper databaseHelper = new DatabaseHelper(LoginPage.this);
                    List<User> users = databaseHelper.getAllUsers();
                    for (User u : users) {
                        if (u.getEmail().equals(email) && u.getPassword().equals(pass)) {
                            Intent intent;
                            loginSuccess = true;
                            if (u.getRole().toString().equals("Admin")) {
                                intent = new Intent(LoginPage.this, AdminDash.class);
                            } else {
                                intent = new Intent(LoginPage.this, HomePage.class);
                            }
                            intent.putExtra("id", u.getId());
                            startActivity(intent);
                        }
                    }
                    if (!loginSuccess) {
                        Toast.makeText(LoginPage.this, "Entered Email or Password is Incorrect", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginPage.this, "Fields Cannot be Left Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        regLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(intent);
            }
        });
    }
}