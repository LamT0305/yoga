package com.example.yoga;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class EditClass extends AppCompatActivity {
    String id;
    Spinner dayOfWeek;
    EditText description, classType, price, duration, capacity, teacherName;
    TextView timeCourse;
    DatabaseHelper DB;
    Button updateBtn, deleteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dayOfWeek = findViewById(R.id.inputDay);
        description = findViewById(R.id.inputDescription);
        classType = findViewById(R.id.inputType);
        price = findViewById(R.id.inputPrice);
        duration = findViewById(R.id.inputDuration);
        capacity = findViewById(R.id.inputCapacity);
        teacherName = findViewById(R.id.inputTeacher);
        timeCourse = findViewById(R.id.inputTime);
        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        DB = new DatabaseHelper(EditClass.this);

        showDropdownList(dayOfWeek);
        timeCourse.setOnClickListener(v -> showTimePickerDialog(timeCourse));
        getAndSetIntentData();


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedDay = dayOfWeek.getSelectedItem().toString();
                String timeOfCourse = timeCourse.getText().toString();
                int _capacity = Integer.parseInt(capacity.getText().toString());
                int _duration = Integer.parseInt(duration.getText().toString());
                double _price = Double.parseDouble(price.getText().toString());
                String _classType = classType.getText().toString();
                String teacher = teacherName.getText().toString();
                String _description = description.getText().toString();
                
                boolean checkUpdateClass = DB.updateClass(Integer.parseInt(id), selectedDay, timeOfCourse, _capacity, _duration, _price, _classType, teacher, _description);
                if (checkUpdateClass){
                    Toast.makeText(EditClass.this, "Updated successfully!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EditClass.this, "Failed to update! "+id, Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = Integer.parseInt(id);
                boolean checkDeleteData = DB.deleteClass( position);
                if (checkDeleteData){
                    Toast.makeText(EditClass.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(EditClass.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void showTimePickerDialog(TextView timeEditText) {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Initialize the TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (TimePicker view, int selectedHour, int selectedMinute) -> {
                    // Format the selected time and set it to EditText
                    String time = String.format("%02d:%02d", selectedHour, selectedMinute);

                    timeEditText.setText(time);
                }, hour, minute, true); // Set true for 24-hour format, false for AM/PM format

        // Show the TimePickerDialog
        timePickerDialog.show();
    }

    private void getAndSetIntentData(){
        if (getIntent().hasExtra("dayOfWeek") &&
                getIntent().hasExtra("timeCourse") &&
                getIntent().hasExtra("capacity") &&
                getIntent().hasExtra("duration") &&
                getIntent().hasExtra("price") &&
                getIntent().hasExtra("classType") &&
                getIntent().hasExtra("teacher") &&
                getIntent().hasExtra("description") &&
                getIntent().hasExtra("position"))
        {
            String _selectedDay = getIntent().getStringExtra("dayOfWeek");
            String _timeCourse = getIntent().getStringExtra("timeCourse");
            String _capacity = getIntent().getStringExtra("capacity");
            String _duration = getIntent().getStringExtra("duration");
            String _price = getIntent().getStringExtra("price");
            String _classType = getIntent().getStringExtra("classType");
            String _teacher = getIntent().getStringExtra("teacher");
            String _description = getIntent().getStringExtra("description");

            setSelectedDay(dayOfWeek, _selectedDay);
            timeCourse.setText(_timeCourse);
            capacity.setText(_capacity);
            duration.setText(_duration);
            price.setText(_price);
            classType.setText(_classType);
            teacherName.setText(_teacher);
            description.setText(_description);
            id = getIntent().getStringExtra("position");
        }else {
            Toast.makeText(this, "Data error!", Toast.LENGTH_SHORT).show();
        }
    }


    private void showDropdownList(Spinner spinner){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_week, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Set a listener to handle selections
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                parent.getItemAtPosition(position);
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing if nothing is selected
            }
        });
    }

    private void setSelectedDay(Spinner spinner, String value){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_week, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        int position = adapter.getPosition(value);
        if (position >= 0) {
            spinner.setSelection(position);
        }
    }


}