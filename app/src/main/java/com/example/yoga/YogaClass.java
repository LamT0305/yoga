package com.example.yoga;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class YogaClass extends AppCompatActivity {

    EditText searchInput;
    TextView searchBtn, addClassBtn;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    DatabaseHelper DB;

    ArrayList<String> classId, dayOfWeek, description, capacity, price, teacher, classTime, classType, duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_yoga_class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        searchInput = findViewById(R.id.searchInput);
        searchBtn = findViewById(R.id.searchBtn);
        addClassBtn = findViewById(R.id.addClassBtn);

        teacher = new ArrayList<>();
        classTime = new ArrayList<>();
        classType = new ArrayList<>();
        duration = new ArrayList<>();
        dayOfWeek = new ArrayList<>();
        description = new ArrayList<>();
        capacity = new ArrayList<>();
        price = new ArrayList<>();
        classId = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        DB = new DatabaseHelper(YogaClass.this);
        adapter = new CustomAdapter(YogaClass.this, this, dayOfWeek, description, capacity, price, teacher, classType, classTime, duration, classId);


         // set up custom adapter
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(YogaClass.this));

        // display class into recyclerView
        displayClass();


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(YogaClass.this, "Searching!", Toast.LENGTH_SHORT).show();
            }
        });

        addClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YogaClass.this, AddClass.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("YogaClass", "Activity resumed, refreshing data.");

        // Clear lists and reload data
        clearClassDataLists();
        displayClass();

        // Notify adapter about data changes
        adapter.notifyDataSetChanged();
        Log.d("YogaClass", "Data refreshed in onResume.");
    }




    private void displayClass(){
        Cursor cs = DB.getAllClasses();
        if (cs.getCount() == 0){
            Toast.makeText(this, "No class available!", Toast.LENGTH_SHORT).show();
        }else{
            while (cs.moveToNext()){
                dayOfWeek.add(cs.getString(1));
                capacity.add(cs.getString(3));
                price.add(cs.getString(5));
                teacher.add(cs.getString(7));
                classType.add(cs.getString(6));
                classTime.add(cs.getString(2));
                duration.add(cs.getString(4));
                description.add(cs.getString(8));
                classId.add(cs.getString(0));
            }
            Toast.makeText(this, teacher.get(0), Toast.LENGTH_SHORT).show();

        }
    }

    private void clearClassDataLists() {
        dayOfWeek.clear();
        capacity.clear();
        price.clear();
        teacher.clear();
        classType.clear();
        classTime.clear();
        duration.clear();
        description.clear();
        classId.clear();
    }

}