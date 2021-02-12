package com.grumpy.exerciseplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.grumpy.exerciseplanner.DatabaseFiles.ExerciseDatabase;
import com.grumpy.exerciseplanner.DatabaseFiles.Plan;

import java.util.ArrayList;

public class PlansActivity extends AppCompatActivity implements PlanAdapter.RemovePlan {
    public static final String TAG ="PlansActivity";

    @Override
    public void onRemovePlanResult(Plan plan) {
        ExerciseDatabase.getInstance(this).planDao().deleteById(plan.getExerciseId());
        Toast.makeText(this, "Removed from plan", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());
    }

    private TextView mondayEdit,tuesdayEdit, wednesdayEdit, thursdayEdit, fridayEdit, saturdayEdit,sundayEdit;
    private RecyclerView mondayRecView,tuesdayRecView, wednesdayRecView, thursdayRecView, fridayRecView, saturdayRecView,sundayRecView;
    private RelativeLayout noPlanRelLayout;
    private NestedScrollView nestedScrollView;
    private Button btnAddPlan;

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    private PlanAdapter mondayAdapter, tuesdayAdapter, wednesdayAdapter, thursdayAdapter, fridayAdapter,
            saturdayAdapter, sundayAdapter;

    private ArrayList<Plan> allPlans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_plans);

        initViews();
        allPlans = (ArrayList<Plan>) ExerciseDatabase.getInstance(this).planDao().getAllPlans();

        if(null != allPlans) {
            if (allPlans.size() > 0) {
                noPlanRelLayout.setVisibility(View.GONE);
                nestedScrollView.setVisibility(View.VISIBLE);

                initRecViews();

            } else {
                noPlanRelLayout.setVisibility(View.VISIBLE);
                nestedScrollView.setVisibility(View.GONE);
                btnAddPlan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PlansActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }
        }
        else{
            noPlanRelLayout.setVisibility(View.VISIBLE);
            nestedScrollView.setVisibility(View.GONE);
            btnAddPlan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PlansActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    }
                });
        }

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
                        Intent intent = new Intent(PlansActivity.this, MainActivity.class);
                        startActivity(intent);

                        break;
                    case R.id.plans:
                        Toast.makeText(PlansActivity.this, "Plans Selected", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.about:
                        Toast.makeText(PlansActivity.this, "About Selected", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

                return false;
            }
        });

    }


    private void initRecViews(){
        Log.d(TAG, "initRecView: started");
        mondayAdapter = new PlanAdapter(this);
        mondayRecView.setAdapter(mondayAdapter);
        mondayRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mondayAdapter.setPlans(getPlansByDay("Monday"));

        tuesdayAdapter = new PlanAdapter(this);
        tuesdayRecView.setAdapter(tuesdayAdapter);
        tuesdayRecView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false));
        tuesdayAdapter.setPlans(getPlansByDay("Tuesday"));

        wednesdayAdapter = new PlanAdapter(this);
        wednesdayRecView.setAdapter(wednesdayAdapter);
        wednesdayRecView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false));
        wednesdayAdapter.setPlans(getPlansByDay("Wednesday"));


        thursdayAdapter = new PlanAdapter(this);
        thursdayRecView.setAdapter(thursdayAdapter);
        thursdayRecView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false));
        wednesdayAdapter.setPlans(getPlansByDay("Thursday"));

        fridayAdapter = new PlanAdapter(this);
        fridayRecView.setAdapter(fridayAdapter);
        fridayRecView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false));
        fridayAdapter.setPlans(getPlansByDay("Friday"));

        saturdayAdapter = new PlanAdapter(this);
        saturdayRecView.setAdapter(saturdayAdapter);
        saturdayRecView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false));
        saturdayAdapter.setPlans(getPlansByDay("Saturday"));

        sundayAdapter = new PlanAdapter(this);
        sundayRecView.setAdapter(sundayAdapter);
        sundayRecView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false));
        sundayAdapter.setPlans(getPlansByDay("Sunday"));
    }

    // TODO: 11/10/2020 add  different thread
    private ArrayList<Plan> getPlansByDay(String day){
        ArrayList<Plan> plans = new ArrayList<>();
        for (Plan p : allPlans){
            if(p.getDay().equalsIgnoreCase(day)){
                plans.add(p);
            }
        }
        return plans;
    }

    private void initViews(){
        mondayEdit = findViewById(R.id.mondayEdit);
        tuesdayEdit = findViewById(R.id.tuesdayEdit);
        wednesdayEdit = findViewById(R.id.wednesdayEdit);
        thursdayEdit = findViewById(R.id.thursdayEdit);
        fridayEdit = findViewById(R.id.fridayEdit);
        saturdayEdit = findViewById(R.id.saturdayEdit);
        sundayEdit = findViewById(R.id.sundayEdit);

        mondayRecView = findViewById(R.id.mondayRecView);
        tuesdayRecView = findViewById(R.id.tuesdayRecView);
        wednesdayRecView = findViewById(R.id.wednesdayRecView);
        thursdayRecView = findViewById(R.id.thursdayRecView);
        fridayRecView = findViewById(R.id.fridayRecView);
        saturdayRecView = findViewById(R.id.saturdayRecView);
        sundayRecView = findViewById(R.id.sundayRecView);

        noPlanRelLayout = findViewById(R.id.noPlanRelLayout);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        btnAddPlan = findViewById(R.id.btnAddPan);

        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationDrawer);
        toolbar = findViewById(R.id.toolbar);
    }



}