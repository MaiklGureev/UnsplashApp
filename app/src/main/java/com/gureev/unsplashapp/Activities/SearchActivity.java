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
import com.gureev.unsplashapp.Types.Photo;
import com.gureev.unsplashapp.Types.SearchResults;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    String query = new String();
    Integer currentPage = 1;
    RecyclerView recyclerView;
    SearchView searchView;
    Button previous;
    Button next;
    Callback<SearchResults> callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Search");
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.search_list);
        searchView = findViewById(R.id.search_view);
        previous = findViewById(R.id.previous_button_search);
        next = findViewById(R.id.next_button_search);


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage != 1) {
                    Intent myIntent = new Intent("");
                    currentPage--;
                    myIntent.putExtra("page", currentPage);
                    myIntent.putExtra("query", query);
                    SearchActivity.this.onNewIntent(myIntent);
                }
                onBackPressed();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (query != "" || query == null) {
                    Intent myIntent = new Intent(SearchActivity.this, SearchActivity.class);
                    currentPage++;
                    myIntent.putExtra("page", currentPage);
                    myIntent.putExtra("query", query);
                    SearchActivity.this.startActivity(myIntent);
                }
            }
        });


        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            currentPage = arguments.getInt("page");
            query = arguments.getString("query");
            loadPhoto();
            MainActivity.dataService.searchPhotos(query, currentPage, 10, null, null).enqueue(callback);
            setTitle("Search, page " + currentPage);
            searchView.setQuery(query, true);
            searchView.clearFocus();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query2) {

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        loadPhoto();
                        MainActivity.dataService.searchPhotos(query, currentPage, 10, null, null).enqueue(callback);

                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();

                query = query2;
                currentPage = 1;
                setTitle("Search, page " + currentPage);
                if (query2 == "") setTitle("Search");
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });


    }


    public void loadPhoto() {
        callback = new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                SearchResults searchResultsList = response.body();
                PhotosAdapter.OnPhotoClickedListener photoClickListener;

                photoClickListener = new PhotosAdapter.OnPhotoClickedListener() {
                    @Override
                    public void photoClicked(Photo photo, ImageView imageView) {
                        Intent myIntent = new Intent(SearchActivity.this, PhotoPropertiesActivity.class);
                        myIntent.putExtra("width", photo.getWidth().toString());
                        myIntent.putExtra("height", photo.getHeight().toString());
                        myIntent.putExtra("description", photo.getDescription());
                        myIntent.putExtra("photo_link", photo.getUrls().getRaw());
                        SearchActivity.this.startActivity(myIntent);

                    }
                };
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(SearchActivity.this);
                recyclerView.setLayoutManager(mLayoutManager);

                PhotosAdapter photosAdapter = new PhotosAdapter(searchResultsList.getResults(), SearchActivity.this, photoClickListener);
                recyclerView.setAdapter(photosAdapter);

            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                Log.d("searchResultsList", "Failure " + t.getMessage());
            }

        };
    }
}
