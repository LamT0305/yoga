package com.example.yoga;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList class_id, dayOfWeek_id, description_id, capacity_id, price_id, teacher_id, classType_id, classTime_id, duration_id;
    private Activity activity;

    public CustomAdapter(Activity activity, Context context, ArrayList dayOfWeek_id, ArrayList description_id, ArrayList capacity_id, ArrayList price_id, ArrayList teacher_id, ArrayList classType_id, ArrayList classTime_id, ArrayList duration_id, ArrayList class_id) {
        this.context = context;
        this.dayOfWeek_id = dayOfWeek_id;
        this.description_id = description_id;
        this.capacity_id = capacity_id;
        this.price_id = price_id;
        this.teacher_id = teacher_id;
        this.classType_id = classType_id;
        this.classTime_id = classTime_id;
        this.duration_id = duration_id;
        this.activity = activity;
        this.class_id = class_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.teacher.setText(String.valueOf(teacher_id.get(position)));
        holder.classType.setText(String.valueOf(classType_id.get(position)));
        holder.classTime.setText(String.valueOf(classTime_id.get(position)));
        holder.duration.setText(String.valueOf(duration_id.get(position)));
        holder.classCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION){
                    Intent intent = new Intent(context, EditClass.class);
                    intent.putExtra("dayOfWeek", String.valueOf(dayOfWeek_id.get(currentPosition)));
                    intent.putExtra("timeCourse", String.valueOf(classTime_id.get(currentPosition)));
                    intent.putExtra("capacity", String.valueOf(capacity_id.get(currentPosition)));
                    intent.putExtra("duration", String.valueOf(duration_id.get(currentPosition)));
                    intent.putExtra("price", String.valueOf(price_id.get(currentPosition)));
                    intent.putExtra("classType", String.valueOf(classType_id.get(currentPosition)));
                    intent.putExtra("teacher", String.valueOf(teacher_id.get(currentPosition)));
                    intent.putExtra("description", String.valueOf(description_id.get(currentPosition)));
                    intent.putExtra("position", String.valueOf(class_id.get(currentPosition)));
                    activity.startActivityForResult(intent, 1);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return teacher_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView teacher, classType, classTime, duration;
        CardView classCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            teacher = itemView.findViewById(R.id.teachertxt);
            classTime = itemView.findViewById(R.id.timetxt);
            classType = itemView.findViewById(R.id.classTypetxt);
            duration = itemView.findViewById(R.id.durationtxt);
            classCard = itemView.findViewById(R.id.class_card);
        }
    }
}
