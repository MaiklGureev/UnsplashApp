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

import com.gureev.unsplashapp.Adapters.CollectionAdapter;
import com.gureev.unsplashapp.R;
import com.gureev.unsplashapp.Types.Collection;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCollectionsActivity extends AppCompatActivity {

    public int currentPage = 1;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("List collections, page " + currentPage);
        setContentView(R.layout.activity_list_collections);

        recyclerView = findViewById(R.id.collection_list);
        Button previous = findViewById(R.id.previous_button);
        Button next = findViewById(R.id.next_button);
        Bundle arguments = getIntent().getExtras();

        if (arguments != null) {
            currentPage = arguments.getInt("page");
            setTitle("List collections, page " + currentPage);
        }

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage != 1) {
                    Intent myIntent = new Intent();
                    currentPage--;
                    myIntent.putExtra("page", currentPage);
                    ListCollectionsActivity.this.onNewIntent(myIntent);
                }
                onBackPressed();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ListCollectionsActivity.this, ListCollectionsActivity.class);
                currentPage++;
                myIntent.putExtra("page", currentPage);
                ListCollectionsActivity.this.startActivity(myIntent);
            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loadCollections();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void loadCollections() {
        Callback<List<Collection>> callback = new Callback<List<Collection>>() {

            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {

                List<Collection> collectionList = response.body();

                CollectionAdapter.OnCollectionClickedListener onCollectionClickedListener = new CollectionAdapter.OnCollectionClickedListener() {
                    @Override
                    public void collectionClicked(Collection collection) {
                        Intent myIntent = new Intent(ListCollectionsActivity.this, CollectionActivity.class);
                        myIntent.putExtra("id", collection.getId());
                        ListCollectionsActivity.this.startActivity(myIntent);
                    }
                };

                LinearLayoutManager mLayoutManager = new LinearLayoutManager(ListCollectionsActivity.this);
                recyclerView.setLayoutManager(mLayoutManager);

                CollectionAdapter collectionAdapter = new CollectionAdapter(collectionList, onCollectionClickedListener);
                recyclerView.setAdapter(collectionAdapter);


                Log.d("Collection", "Count " + collectionList.size());
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                Log.d("Collection", "Failure " + t.getMessage());
            }
        };
        MainActivity.dataService.getCollections(currentPage, 10).enqueue(callback);
    }
}
