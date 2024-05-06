package com.example.myreadingapp.Recycler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myreadingapp.DetailsActivity;
import com.example.myreadingapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    Context context;
    ArrayList<Model> namebooklist;

    public Adapter(Context context, ArrayList<Model> namebooklist) {
        this.context = context;
        this.namebooklist = namebooklist;
    }

    public void  setFilteredList(java.util.List<Model> filteredList){
        this.namebooklist = (ArrayList<Model>) filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Model model = namebooklist.get(position);
        holder.namebook.setText(model.getNamebook());


        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("namebook", model.getNamebook());
            intent.putExtra("img", model.getImg());
            intent.putExtra("pdfLink", model.getPdfLink());
           ;


            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return namebooklist.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView namebook;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            namebook = itemView.findViewById(R.id.namebook);
        }
    }
}
