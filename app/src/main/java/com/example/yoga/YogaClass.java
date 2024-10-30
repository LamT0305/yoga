package com.example.yoga;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

    ArrayList<String> teacher, classTime, classType, duration;
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
        recyclerView = findViewById(R.id.recyclerView);
        DB = new DatabaseHelper(YogaClass.this);
        adapter = new CustomAdapter(YogaClass.this, this, teacher, classType, classTime, duration);


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

    private void displayClass(){
        Cursor cs = DB.getAllClasses();
        if (cs.getCount() == 0){
            Toast.makeText(this, "No class available!", Toast.LENGTH_SHORT).show();
        }else{
            while (cs.moveToNext()){
                teacher.add(cs.getString(7));
                classType.add(cs.getString(6));
                classTime.add(cs.getString(2));
                duration.add(cs.getString(4));
            }
            Toast.makeText(this, teacher.get(0), Toast.LENGTH_SHORT).show();

        }
    }

}