package com.example.myreadingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myreadingapp.databinding.ActivityDetailBinding;
import com.example.myreadingapp.databinding.ActivityMainBinding;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;

import java.io.IOException;
import java.io.InputStream;

import io.grpc.okhttp.OkHttpChannelBuilder;
import io.grpc.okhttp.OkHttpServerBuilder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String link = getIntent().getStringExtra("pdfLink");

        ShowPdf(link);
    }

    private void ShowPdf(String link) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(link).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                binding.progress.setVisibility(View.GONE);
                Toast.makeText(DetailsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.pdfview.fromStream(inputStream)
                                // Enable horizontal swiping
//                                .swipeHorizontal(true)
                                // Snap pages to screen boundaries
                                .pageSnap(true)
                                // Add spacing between pages (optional)
                                .autoSpacing(true)
                                // Enable single-page flinging
                                .pageFling(true)
                                // Configure initial rendering and page fitting

                                .onLoad(nbPages -> {
                                    binding.progress.setVisibility(View.GONE);
                                })
                                .load();
                    }
                });
            }
        });
    }
}
