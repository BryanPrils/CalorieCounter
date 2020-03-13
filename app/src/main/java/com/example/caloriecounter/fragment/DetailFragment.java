package com.example.caloriecounter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.caloriecounter.R;

public class DetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail_fragment, container, false);

        Bundle bundle = getArguments();
        TextView textView = view.findViewById(R.id.tv_description);
        ImageView imageView = view.findViewById(R.id.image_view_food);
        String description = "";


        if (bundle != null) {
            description = getArguments().getString("description");
        }

        textView.setText(description);

        return view;
    }
}
