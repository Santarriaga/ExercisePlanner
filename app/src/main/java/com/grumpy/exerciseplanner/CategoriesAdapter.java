package com.grumpy.exerciseplanner;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.grumpy.exerciseplanner.DatabaseFiles.Exercise;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    public interface GetCategory{
        void onGetCategoryResult(String category);
        void getAll();
    }
    private GetCategory getCategory;


    private ArrayList<String> categories = new ArrayList<>();
    private Context context;
    private int rowIndex = -1;

    public CategoriesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .asBitmap()
                .load(context.getResources().getIdentifier(categories.get(position).toString(), "drawable", context.getPackageName()))
                .into(holder.image);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categories.get(position).toString().equals("ic_all")){
                    rowIndex = position;
                    notifyDataSetChanged();
                    getCategory = (GetCategory) context;
                    getCategory.getAll();
                }
                else {
                    try{
                        rowIndex = position;
                        notifyDataSetChanged();
                        getCategory = (GetCategory) context;
                        getCategory.onGetCategoryResult(categories.get(position).toString());
                    }catch ( ClassCastException e){
                        e.printStackTrace();
                    }
                }


            }
        });

        if(rowIndex == position){
           holder.linearLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.gradient));
        }
        else {
            holder.linearLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.white));

        }


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(ArrayList<String> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private MaterialCardView parent;
        private ImageView image;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            image = itemView.findViewById(R.id.img);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
