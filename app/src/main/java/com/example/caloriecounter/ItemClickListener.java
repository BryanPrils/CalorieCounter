package com.example.caloriecounter;

import com.google.firebase.firestore.DocumentSnapshot;

public interface ItemClickListener {
    void OnItemClick(DocumentSnapshot documentSnapshot, int position);
}
