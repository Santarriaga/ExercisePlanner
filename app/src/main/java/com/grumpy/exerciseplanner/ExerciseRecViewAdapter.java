package com.grumpy.exerciseplanner;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.grumpy.exerciseplanner.DatabaseFiles.Exercise;

import java.util.ArrayList;

public class ExerciseRecViewAdapter extends RecyclerView.Adapter<ExerciseRecViewAdapter.ViewHolder>{
    private static final String TAG = "TrainingAdapter";
    public static final String EXERCISE_KEY = "key";

    private ArrayList<Exercise> exercises = new ArrayList<>();
    private Context context;

    //constructor
    public ExerciseRecViewAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder: Started");

        //bind information to items
        holder.name.setText(exercises.get(position).getName());
        Glide.with(context)
                .asBitmap()
                .load(exercises.get(position).getImgUrl())
                .into(holder.image);

        //add event listener for when card is pressed
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExerciseDetailsActivity.class);
                intent.putExtra(EXERCISE_KEY, exercises.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public void setExercises(ArrayList<Exercise> exercises){
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private MaterialCardView parent;
        private ImageView image;
        private TextView name,description;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            image = itemView.findViewById(R.id.image);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            name = itemView.findViewById(R.id.txtName);
        }
    }
}
