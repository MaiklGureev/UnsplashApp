package com.gureev.unsplashapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import com.gureev.unsplashapp.Adapters.PhotosAdapter;
import com.gureev.unsplashapp.R;
import com.gureev.unsplashapp.Types.Collection;
import com.gureev.unsplashapp.Types.Photo;
import com.gureev.unsplashapp.Types.SearchResults;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionActivity extends AppCompatActivity {

    Integer currentPage = 1;
    String id;
    RecyclerView recyclerView;
    Button previous;
    Button next;
    Callback<List<Photo>> callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Collection");
        setContentView(R.layout.activity_collection);

        recyclerView = findViewById(R.id.collection_photos_list);
        previous = findViewById(R.id.previous_page_collection_button);
        next = findViewById(R.id.next_page_collection_button);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            id = arguments.getString("id");
            if (arguments.getInt("page") != 0) currentPage = arguments.getInt("page");
        }

        setTitle("Collection, page " + currentPage);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage != 1) {
                    Intent myIntent = new Intent();
                    currentPage--;
                    myIntent.putExtra("page", currentPage);
                    myIntent.putExtra("id", id);
                    CollectionActivity.this.onNewIntent(myIntent);
                }
                onBackPressed();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(CollectionActivity.this, CollectionActivity.class);
                currentPage++;
                myIntent.putExtra("page", currentPage);
                myIntent.putExtra("id", id);
                CollectionActivity.this.startActivity(myIntent);

            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loadPhotos();
                MainActivity.dataService.getCollectionPhotos(id, currentPage, 10).enqueue(callback);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }

    public void loadPhotos() {

        callback = new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                List<Photo> photoList = response.body();
                PhotosAdapter.OnPhotoClickedListener photoClickListener;

                photoClickListener = new PhotosAdapter.OnPhotoClickedListener() {
                    @Override
                    public void photoClicked(Photo photo, ImageView imageView) {
                        Intent myIntent = new Intent(CollectionActivity.this, PhotoPropertiesActivity.class);
                        myIntent.putExtra("width", photo.getWidth().toString());
                        myIntent.putExtra("height", photo.getHeight().toString());
                        myIntent.putExtra("description", photo.getDescription());
                        myIntent.putExtra("photo_link", photo.getUrls().getRaw());
                        CollectionActivity.this.startActivity(myIntent);
                    }
                };
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(CollectionActivity.this);
                recyclerView.setLayoutManager(mLayoutManager);

                PhotosAdapter photosAdapter = new PhotosAdapter(photoList, CollectionActivity.this, photoClickListener);
                recyclerView.setAdapter(photosAdapter);
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d("Collection photos", "Failure " + t.getMessage());

            }
        };
    }


}
