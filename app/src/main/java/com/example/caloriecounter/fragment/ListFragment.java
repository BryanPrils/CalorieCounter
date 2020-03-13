package com.example.caloriecounter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecounter.ItemClickListener;
import com.example.caloriecounter.R;
import com.example.caloriecounter.activity.DetailActivity;
import com.example.caloriecounter.activity.NewFoodActivity;
import com.example.caloriecounter.adapter.FoodAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import model.Food;

public class ListFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference foodRef = db.collection("food");
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private FoodAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpRecyclerView();

        FloatingActionButton button = getView().findViewById(R.id.button_add_food);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity().getBaseContext(), NewFoodActivity.class));
            }
        });
    }


    private void setUpRecyclerView() {

        Query query = foodRef.orderBy("name", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Food> options = new FirestoreRecyclerOptions.Builder<Food>()
                .setQuery(query, Food.class)
                .build();


        adapter = new FoodAdapter(options);

        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnItemClick(DocumentSnapshot documentSnapshot, int position) {
                Food food = documentSnapshot.toObject(Food.class);

                DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detail);
                if (detailFragment != null && detailFragment.isVisible()) {
                    // Visible: send bundle
                    DetailFragment newFragment = new DetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("description", food.getDescription());
                    bundle.putString("imagePath", food.getImagePath());
                    bundle.putString("name", food.getName());
                    bundle.putInt("calories", food.getCalories());
                    newFragment.setArguments(bundle);

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(detailFragment.getId(), newFragment);
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                } else {
                    // Not visible: start as intent
                    Intent intent = new Intent(getActivity().getBaseContext(), DetailActivity.class);
                    intent.putExtra("description", food.getDescription());
                    intent.putExtra("imagePath", food.getImagePath());
                    intent.putExtra("name", food.getName());
                    intent.putExtra("calories", food.getCalories());
                    getActivity().startActivity(intent);
                }


            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
