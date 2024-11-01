package com.example.yoga;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycler extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper DB;
    CustomAdapter adapter;
    ArrayList<String> classId, dayOfWeek, description, capacity, price, teacher, classTime, classType, duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        classId = new ArrayList<>();
        teacher = new ArrayList<>();
        classTime = new ArrayList<>();
        classType = new ArrayList<>();
        duration = new ArrayList<>();
        dayOfWeek = new ArrayList<>();
        description = new ArrayList<>();
        capacity = new ArrayList<>();
        price = new ArrayList<>();
        recyclerView = findViewById(R.id.rcv);
        DB = new DatabaseHelper(this);
        adapter = new CustomAdapter(recycler.this, this, dayOfWeek, description, capacity, price, teacher, classTime, classType, duration, classId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recycler.this));
        displayClass();
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
}