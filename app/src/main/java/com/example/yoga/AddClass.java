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

import org.w3c.dom.Text;

import java.util.Calendar;

public class AddClass extends AppCompatActivity {
    Spinner dayOfWeek;
    EditText description, classType, price, duration, capacity;
    TextView timeCourse;
    Button addClassBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addClassBtn = findViewById(R.id._addClassBtn);
        classType = findViewById(R.id.inputType);
        price = findViewById(R.id.inputPrice);
        duration = findViewById(R.id.inputDuration);
        capacity = findViewById(R.id.inputCapacity);
        timeCourse = findViewById(R.id.inputTime);
        dayOfWeek = findViewById(R.id.inputDay);
        description = findViewById(R.id.inputDescription);


        // text overflow
        textOverFlow(); // done
        showDropdownList(dayOfWeek); //done

//        handle add button
        addClassBtn.setOnClickListener(v -> {
            if (description.getText().toString().trim().isEmpty()){
                description.setError("This field is required");
            }else if (capacity.getText().toString().trim().isEmpty()){
                capacity.setError("This field is required");
            }else if (duration.getText().toString().trim().isEmpty()){
                duration.setError("This field is required");
            } else if (price.getText().toString().trim().isEmpty()) {
                price.setError("This field is required");
            } else if (classType.getText().toString().trim().isEmpty()) {
                classType.setError("This field is required");
            }
        });

        // time picker

        timeCourse.setOnClickListener(v -> showTimePickerDialog(timeCourse));
    }

    private void textOverFlow(){
        description.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                description.setSingleLine();
                description.setHorizontallyScrolling(true);
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

}