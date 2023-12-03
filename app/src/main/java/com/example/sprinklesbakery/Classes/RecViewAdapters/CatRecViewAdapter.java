package com.example.sprinklesbakery.Classes.RecViewAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprinklesbakery.Activities.Admin.AddEditCat;
import com.example.sprinklesbakery.Classes.Category;
import com.example.sprinklesbakery.Database.DatabaseHelper;
import com.example.sprinklesbakery.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CatRecViewAdapter extends RecyclerView.Adapter<CatRecViewAdapter.ViewHolder> {

    List<Category> categories = new ArrayList<>();
    Context context;
    public CatRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_cat_item, parent, false);
        ViewHolder holder = new ViewHolder(view).linkAdapter(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category =  categories.get(position);

        holder.catName.setText(categories.get(position).getCatName());
        holder.catDesc.setText(categories.get(position).getCatDesc());
        holder.category = category;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout catCard;
        FloatingActionButton btnEdit;
        TextView catName, catDesc;
        Category category;
        CatRecViewAdapter adapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catCard = itemView.findViewById(R.id.category_card);
            catName = itemView.findViewById(R.id.cat_name);
            catDesc = itemView.findViewById(R.id.cat_desc);
            btnEdit = itemView.findViewById(R.id.ic_edit);

            btnEdit.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    boolean success = databaseHelper.deleteCategory(category);
                    if (success){
                        Toast.makeText(context, category.getCatName() + " Deleted Successfully", Toast.LENGTH_SHORT).show();
                        adapter.categories.remove(getAdapterPosition());
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(context, category.getCatName() + " Delete Failed", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddEditCat.class);
                    intent.putExtra("id", category.getId());
                    context.startActivity(intent);
                }
            });

        }

        public ViewHolder linkAdapter(CatRecViewAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }
}
