package com.gureev.unsplashapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gureev.unsplashapp.R;
import com.gureev.unsplashapp.Types.Collection;

import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionHolder> {

    private final List<Collection> collections;
    private OnCollectionClickedListener onCollectionClickedListener;

    public CollectionAdapter(List<Collection> collections, OnCollectionClickedListener onCollectionClickedListener) {
        this.collections = collections;
        this.onCollectionClickedListener = onCollectionClickedListener;
    }

    @NonNull
    @Override
    public CollectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection, parent, false);
        return new CollectionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CollectionHolder holder, int position) {
        holder.bind(collections.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCollectionClickedListener.collectionClicked(collections.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return collections.size();
    }


    static final class CollectionHolder extends RecyclerView.ViewHolder {

        private final TextView id;
        private final TextView title;
        private final TextView description;
        private final TextView total_photos;


        public CollectionHolder(@NonNull View view) {
            super(view);
            id = view.findViewById(R.id.textView_id);
            title = view.findViewById(R.id.textView_title);
            description = view.findViewById(R.id.textView_description);
            total_photos = view.findViewById(R.id.textView_total_photos);
        }

        private void bind(@NonNull Collection Collection) {
            id.setText(Collection.getId());
            title.setText(Collection.getTitle());
            total_photos.setText("Total photos:"+Collection.getTotal_photos());
            description.setText("Description: "+Collection.getDescription());
        }
    }

    public interface OnCollectionClickedListener {
        void collectionClicked(Collection collection);
    }
}

