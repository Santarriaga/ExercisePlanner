package com.grumpy.exerciseplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.grumpy.exerciseplanner.DatabaseFiles.Exercise;
import com.grumpy.exerciseplanner.DatabaseFiles.ExerciseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements CategoriesAdapter.GetCategory{
    private static final String TAG = "mainActivity";

    @Override
    public void onGetCategoryResult(String category) {
        ArrayList<Exercise> categoryExercises = (ArrayList<Exercise>) ExerciseDatabase.getInstance(this).exerciseDao().getItemsByCategory(category);
        if(categoryExercises != null){
            adapter.setExercises(categoryExercises);
        }
    }

    @Override
    public void getAll() {
        asyncTask();
    }

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;
    private RecyclerView recyclerView, categoriesRecView;
    private EditText searchBox;

    private ArrayList<Exercise> allExercises;
    private ExerciseRecViewAdapter adapter;
    private CategoriesAdapter categoriesAdapter;

    //For database thread
    private ExecutorService executors = Executors.newFixedThreadPool(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



        initViews();

        //toolbar
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.drawer_open, R.string.drawer_close);

        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this,R.color.blue));
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //navigation drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Toast.makeText(MainActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.plans:
                        Intent intent = new Intent(MainActivity.this, PlansActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.about:
                        Toast.makeText(MainActivity.this, "About Selected", Toast.LENGTH_SHORT).show();
                            break;
                    default:
                        break;
                }

                return false;
            }
        });

        //Recycler view
        adapter = new ExerciseRecViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        ArrayList<String> categories = new ArrayList<String>(Arrays.asList("ic_all","ic_meditation","ic_cardio",
                "ic_sports","ic_calisthenics","ic_arm","ic_machines","ic_weightlifting"));

        categoriesAdapter = new CategoriesAdapter(this);
        categoriesRecView.setAdapter(categoriesAdapter);
        categoriesRecView.setLayoutManager(new LinearLayoutManager(this,recyclerView.HORIZONTAL,false));
        categoriesAdapter.setCategories(categories);

        asyncTask();

        //Search box
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                initializeSearch();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // TODO: 11/3/2020 fix async task 
    public void asyncTask() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // your async code goes here.
                try {
                    Thread.sleep(1_000);

                    // create message and pass any object here doesn't matter
                    // for a simple example I have used a simple string
                    allExercises =(ArrayList<Exercise>) ExerciseDatabase.getInstance(MainActivity.this).exerciseDao().getAllExercises();

                    String msg = "Database retrieved!";
                    doSomethingOnUi(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        executors.submit(runnable);
    }
    private void doSomethingOnUi(Object response) {
        Handler uiThread = new Handler(Looper.getMainLooper());
        uiThread.post(new Runnable() {
            @Override
            public void run() {
                // now update your UI here
                adapter.setExercises(allExercises);
            }
        });
    }


    private void initializeSearch(){
        if(!searchBox.getText().toString().equals("")){
            //get items
           String name = "%" + searchBox.getText().toString() + "%";
           //database search
            ArrayList<Exercise> items = (ArrayList<Exercise>) ExerciseDatabase.getInstance(this).exerciseDao().searchForExercise(name);
           if(items != null){
               adapter.setExercises(items);
           }
        }
        else{
            adapter.setExercises(allExercises);
        }
    }

    private void initViews() {
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationDrawer);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recView);
        categoriesRecView = findViewById(R.id.categories);
        searchBox = findViewById(R.id.searchBar);
    }
}