package com.example.caloriecounter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecounter.ItemClickListener;
import com.example.caloriecounter.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import model.Food;

public class FoodAdapter extends FirestoreRecyclerAdapter<Food, FoodAdapter.FoodHolder> {
    private ItemClickListener listener;

    public FoodAdapter(@NonNull FirestoreRecyclerOptions<Food> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodHolder holder, int position, @NonNull Food model) {
        holder.textViewTitle.setText(model.getName());
        holder.textViewDescription.setText(model.getDescription());
        holder.textViewCalories.setText(String.valueOf(model.getCalories()));


    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new FoodHolder(view);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    class FoodHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewCalories;

        private FoodHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tv_title);
            textViewDescription = itemView.findViewById(R.id.tv_description);
            textViewCalories = itemView.findViewById(R.id.tv_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.OnItemClick(getSnapshots().getSnapshot(position), position);
                    }

                }
            });
        }
    }
}
