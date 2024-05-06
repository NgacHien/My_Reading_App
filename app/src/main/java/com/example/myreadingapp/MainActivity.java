package com.example.myreadingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myreadingapp.Recycler.Adapter;
import com.example.myreadingapp.Recycler.Model;
import com.example.myreadingapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;


    FirebaseFirestore firestore;
    ArrayList<Model> namebookList;

    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();
        namebookList = new ArrayList<>();
        adapter = new Adapter(this, namebookList);
        binding.rcv.setAdapter(adapter);
        binding.rcv.setLayoutManager(new LinearLayoutManager(this));


        LoadData();

        binding.searchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterList(s.toString());
            }
        });
    }


    private void filterList(String text) {
        java.util.List<Model> filteredList = new ArrayList<>();
        for (Model item : namebookList) {
            if (item.getNamebook().toLowerCase().contains(text.toLowerCase())) {

                filteredList.add(item);
            }
        }


        if (filteredList.isEmpty()) {
            binding.data.setVisibility(View.VISIBLE);
        } else {
            adapter.setFilteredList(filteredList);
            binding.data.setVisibility(View.GONE);
        }
    }


    private void LoadData() {
        namebookList.clear();
        firestore.collection("Name book").orderBy("id", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Book Loaded", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (DocumentChange dc : Objects.requireNonNull(value).getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            Model newbook = dc.getDocument().toObject(Model.class);
                            if (newbook != null) {
                                namebookList.add(newbook);
                            } else {
                                Log.w("Error", "Can't load book");
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

}
//    private void LoadBookCover(String img) {
//        StorageReference imgRef = FirebaseStorage.getInstance().getReference().child("BookCover").child(img);
//        imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                ImageView imgBook  = findViewById(R.id.imgbook);
//                Glide.with(MainActivity.this)
//                        .load(uri)
//                        .into(imgBook);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("Error", "Can't load image" );
//            }
//        });
//    }
