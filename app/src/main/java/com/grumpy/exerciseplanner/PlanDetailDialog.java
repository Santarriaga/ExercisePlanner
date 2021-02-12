package com.grumpy.exerciseplanner;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.grumpy.exerciseplanner.DatabaseFiles.Exercise;
import com.grumpy.exerciseplanner.DatabaseFiles.ExerciseDatabase;
import com.grumpy.exerciseplanner.DatabaseFiles.Plan;

import static com.grumpy.exerciseplanner.ExerciseDetailsActivity.TRAINING_KEY;

public class PlanDetailDialog extends DialogFragment {

    private static final String TAG = "PlanDialog";
    private Button btnDismiss, btnAdd;
    private TextView txtName;
    private EditText edtTxtMinutes;
    private Spinner spinnerDay;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_plan_details, null);
        initViews(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Enter Details");
        Bundle bundle = getArguments();

        if(bundle != null){
            int exerciseId = bundle.getInt(TRAINING_KEY);
            final Exercise exercise = ExerciseDatabase.getInstance(getActivity()).exerciseDao().getItemById(exerciseId);
            if(exercise != null){
                txtName.setText(exercise.getName());
                btnDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String day = spinnerDay.getSelectedItem().toString();
                        int minutes = Integer.valueOf(edtTxtMinutes.getText().toString());
                        // TODO: 11/10/2020 move database part out of main thread
                        Plan plan = new Plan(exercise.getId(),minutes,day,false);
                        ExerciseDatabase.getInstance(getActivity()).planDao().insert(plan);

                        Intent intent = new Intent(getActivity(), PlansActivity.class);
                        startActivity(intent);
                    }
                });
            }

        }
        else {
            Log.d(TAG, "onCreate: Nothing in bundle ");
        }


        return builder.create();
    }


    private void initViews(View view){
        btnDismiss = view.findViewById(R.id.btnDismiss);
        btnAdd = view.findViewById(R.id.btnAdd);
        txtName = view.findViewById(R.id.txtName);
        edtTxtMinutes =view.findViewById(R.id.edtMinutes);
        spinnerDay = view.findViewById(R.id.spinnerDays);

    }
}
