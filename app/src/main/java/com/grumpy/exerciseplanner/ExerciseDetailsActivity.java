package com.grumpy.exerciseplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.grumpy.exerciseplanner.DatabaseFiles.Exercise;
import com.grumpy.exerciseplanner.DatabaseFiles.ExerciseDatabase;

import static com.grumpy.exerciseplanner.ExerciseRecViewAdapter.EXERCISE_KEY;

public class ExerciseDetailsActivity extends AppCompatActivity {

    private static final String TAG = " ExerciseDetailsActivity" ;
    public static final String TRAINING_KEY = "exercise";
    private Button btnAddPlan;
    private TextView txtTrainingName;
    private TextView txtDescription;
    private ImageView imgTraining;

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    private Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_exercise_details);

        initViews();
        setUpDrawer();

        //drawer navigation

        //get intent from mainActivity
        Intent intent = getIntent();
        if(intent != null){
            int id = intent.getIntExtra(EXERCISE_KEY,0);
            exercise = ExerciseDatabase.getInstance(ExerciseDetailsActivity.this).exerciseDao().getItemById(id);
            if(exercise != null){
                txtTrainingName.setText(exercise.getName());
                txtDescription.setText(exercise.getLongDescription());

                Glide.with(this)
                        .asBitmap()
                        .load(exercise.getImgUrl())
                        .into(imgTraining);

                btnAddPlan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PlanDetailDialog dialog = new PlanDetailDialog();
                        Bundle bundle = new Bundle();
                        bundle.putInt(TRAINING_KEY, id);
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "Plan Detail Dialog");
                    }
                });
            }

        }

    }

    private void setUpDrawer(){
        //toolbar
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.blue));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //navigation drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent intent = new Intent(ExerciseDetailsActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.plans:
                        Intent intent2 = new Intent(ExerciseDetailsActivity.this, PlansActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.about:
                        Toast.makeText(ExerciseDetailsActivity.this, "About Selected", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
    }


    private void initViews() {
        btnAddPlan = findViewById(R.id.btnAddPlan);
        txtTrainingName = findViewById(R.id.txtTraining);
        txtDescription = findViewById(R.id.txtDescription);
        imgTraining = findViewById(R.id.imgTraining);

        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationDrawer);
        toolbar = findViewById(R.id.toolbar);
    }
}