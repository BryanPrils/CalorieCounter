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
    private ItemClickListener mListener;
    //private Context mContext;

    public FoodAdapter(@NonNull FirestoreRecyclerOptions<Food> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodHolder holder, int position, @NonNull Food model) {
        holder.textViewTitle.setText(model.getName());
        holder.textViewCalories.setText(String.valueOf(model.getCalories()));
        //Picasso.with(mContext).load(model.getImagePath()).into(holder.ImageViewFood);


    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new FoodHolder(view);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.mListener = listener;
    }


    class FoodHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewCalories;
        //ImageView ImageViewFood;

        private FoodHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tv_title);
            //textViewDescription = itemView.findViewById(R.id.tv_description);
            //ImageViewFood = itemView.findViewById(R.id.image_food);
            textViewCalories = itemView.findViewById(R.id.tv_calories);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && mListener != null) {
                        mListener.OnItemClick(getSnapshots().getSnapshot(position), position);
                    }

                }
            });
        }
    }

}
