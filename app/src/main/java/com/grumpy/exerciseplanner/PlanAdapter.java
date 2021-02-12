package com.grumpy.exerciseplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.grumpy.exerciseplanner.DatabaseFiles.ExerciseDatabase;
import com.grumpy.exerciseplanner.DatabaseFiles.Plan;

import java.util.ArrayList;

public class PlanAdapter extends  RecyclerView.Adapter<PlanAdapter.ViewHolder>{


    public interface RemovePlan{
        void onRemovePlanResult(Plan plan);
    }
    private RemovePlan removePlan;


    private ArrayList<Plan> plans = new ArrayList<>();
    private Context context;




    //CONSTRUCTOR
    public PlanAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // TODO: 11/10/2020 move outside main thread
        Exercise exercise = ExerciseDatabase.getInstance(context).exerciseDao().getItemById(plans.get(position).getExerciseId());

        //set information to item
        holder.txtName.setText(exercise.getName());
        holder.txtDescription.setText(exercise.getShortDescription());
        holder.txtTime.setText(String.valueOf(plans.get(position).getMinutes()));
        Glide.with(context)
                .asBitmap()
                .load(exercise.getImgUrl())
                .into(holder.image);
        if(plans.get(position).isAccomplished()){
            holder.emptyCircle.setVisibility(View.GONE);
            holder.checkedCircle.setVisibility(View.VISIBLE);
        }
        else{
            holder.emptyCircle.setVisibility(View.VISIBLE);
            holder.checkedCircle.setVisibility(View.GONE);
        }

        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Remove")
                        .setMessage("Are you sure you want to delete "+ exercise.getName())
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try{
                                   removePlan = (RemovePlan) context;
                                   removePlan.onRemovePlanResult(plans.get(position));
                                }catch (ClassCastException e){
                                    e.printStackTrace();
                                }
                            }
                        });
                builder.create().show();

                return true;
            }
        });



    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public void setPlans(ArrayList<Plan> plans) {
        this.plans = plans;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTime, txtName, txtDescription;
        private MaterialCardView parent;
        private ImageView image, emptyCircle, checkedCircle;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            txtTime = itemView.findViewById(R.id.txtTime);
            txtName = itemView.findViewById(R.id.txtName);
            txtDescription = itemView.findViewById(R.id.txtTimeDescription);
            parent = itemView.findViewById(R.id.parent);
            image = itemView.findViewById(R.id.trainingImage);
            emptyCircle = itemView.findViewById(R.id.emptyCircle);
            checkedCircle = itemView.findViewById(R.id.checkedCircle);
        }
    }
}
