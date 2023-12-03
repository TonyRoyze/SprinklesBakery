package com.example.sprinklesbakery.Classes.RecViewAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprinklesbakery.Activities.Admin.AddEditUser;
import com.example.sprinklesbakery.Classes.User;
import com.example.sprinklesbakery.Database.DatabaseHelper;
import com.example.sprinklesbakery.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class UserRecViewAdapter extends RecyclerView.Adapter<UserRecViewAdapter.ViewHolder> {

    List<User> users = new ArrayList<>();
    Context context;
    public UserRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_user_item, parent, false);
        ViewHolder holder = new ViewHolder(view).linkAdapter(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user =  users.get(position);

        holder.userName.setText(users.get(position).getName());
        holder.userRole.setText(users.get(position).getRole());
        holder.userUserName.setText(users.get(position).getUsername());
        holder.userEmail.setText(users.get(position).getEmail());
        holder.user = user;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout userCard;
        FloatingActionButton btnEdit;
        TextView userName, userRole, userUserName, userEmail;
        User user;
        UserRecViewAdapter adapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userCard = itemView.findViewById(R.id.user_card);
            userName = itemView.findViewById(R.id.user_name);
            userRole = itemView.findViewById(R.id.user_role);
            userUserName = itemView.findViewById(R.id.user_username);
            userEmail = itemView.findViewById(R.id.user_email);
            btnEdit = itemView.findViewById(R.id.ic_edit);

            btnEdit.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    boolean success = databaseHelper.deleteUser(user);
                    if (success){
                        Toast.makeText(context, user.getName() + " Deleted Successfully", Toast.LENGTH_SHORT).show();
                        adapter.users.remove(getAdapterPosition());
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(context, user.getName() + " Delete Failed", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddEditUser.class);
                    intent.putExtra("id", user.getId());
                    context.startActivity(intent);
                }
            });

        }

        public ViewHolder linkAdapter(UserRecViewAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }
}
