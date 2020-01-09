package com.gureev.unsplashapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.gureev.unsplashapp.R;
import com.gureev.unsplashapp.Types.Photo;
import com.gureev.unsplashapp.Tools.UnsplashClient;
import com.gureev.unsplashapp.Tools.UnsplashInterface;
import com.squareup.picasso.Picasso;


import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    private ImageView imageView;
    private Button search, collections;

    private Intent myIntent;


    public static UnsplashInterface dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.button_search);
        imageView = findViewById(R.id.image_photo_of_day);
        collections = findViewById(R.id.button_collections);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        collections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ListCollectionsActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });


        dataService = UnsplashClient.getUnsplashClient().create(UnsplashInterface.class);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loadPhoto();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();


    }

    private void loadPhoto() {
        Callback<List<Photo>> callback = new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                final List<Photo> photos = response.body();
                Picasso.Builder picassoBuilder = new Picasso.Builder(MainActivity.this);
                Picasso picasso = picassoBuilder.build();
                picasso.load(photos.get(0).getUrls().getRegular())
                        .resize(400, 400)
                        .centerCrop()
                        .into(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myIntent = new Intent(MainActivity.this, PhotoPropertiesActivity.class);
                        myIntent.putExtra("width", photos.get(0).getWidth().toString());
                        myIntent.putExtra("height", photos.get(0).getHeight().toString());
                        myIntent.putExtra("description", photos.get(0).getDescription());
                        myIntent.putExtra("photo_link", photos.get(0).getUrls().getRaw());
                        MainActivity.this.startActivity(myIntent);
                    }
                });


                Log.d("Photos", "Photo width " + photos.get(0).getWidth());
                Log.d("Photos", "Photo height " + photos.get(0).getHeight());
                Log.d("Photos", "Photo description " + photos.get(0).getDescription());
                Log.d("Photos", "Photo photo_link " + photos.get(0).getUrls().getRaw());

            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d("Photos", "Failure " + t.getMessage());
            }

        };
        dataService.getRandomPhoto(null, null, null, null, null, 1).enqueue(callback);
    }


}
