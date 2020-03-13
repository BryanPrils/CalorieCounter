package com.example.caloriecounter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecounter.R;
import com.example.caloriecounter.activity.MainActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

import model.UserDiary;

public class MyAdapter extends FirestoreRecyclerAdapter<UserDiary, MyAdapter.UserDiaryHolder> {

    public MyAdapter(@NonNull FirestoreRecyclerOptions<UserDiary> options) {
        super(options);
    }

    public static void calculateCalories(List<UserDiary> diaries) {
        int total = 0;
        for (int i = 0; i < diaries.size(); i++) {
            total += diaries.get(i).getCalories();
        }

        MainActivity.textViewTotalCal.setText("Total Calories" + " " + total);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserDiaryHolder holder, int position, @NonNull UserDiary model) {
        holder.textviewName.setText(model.getFoodname());
        holder.textViewcalories.setText(String.valueOf(model.getCalories()));
        MainActivity.diaries.add(new UserDiary(model.getEmail(), model.getDate(), model.getFoodname(), model.getCalories()));
        calculateCalories(MainActivity.diaries);

    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }


    @NonNull
    @Override
    public UserDiaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new UserDiaryHolder(v);
    }

    class UserDiaryHolder extends RecyclerView.ViewHolder {
        TextView textviewName;
        TextView textViewcalories;
        TextView textViewTotalCal;


        private UserDiaryHolder(@NonNull View itemView) {
            super(itemView);
            textviewName = itemView.findViewById(R.id.tv_name_main);
            textViewcalories = itemView.findViewById(R.id.tv_calories_main);
            textViewTotalCal = itemView.findViewById(R.id.tv_calories);
        }
    }
}
