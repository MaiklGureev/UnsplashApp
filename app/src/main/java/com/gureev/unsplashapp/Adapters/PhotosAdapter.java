package com.gureev.unsplashapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.gureev.unsplashapp.R;
import com.gureev.unsplashapp.Types.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder>{
    private List<Photo> photoList;
    private Context mContext;
    private OnPhotoClickedListener mListener;

    public PhotosAdapter(List<Photo> photos, Context context, OnPhotoClickedListener listener) {
        photoList = photos;
        mContext = context;
        mListener = listener;
    }

    public void setmListener(OnPhotoClickedListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Photo photo = photoList.get(position);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.photoClicked(photoList.get(holder.getAdapterPosition()), (ImageView)v);

            }
        });

        // create Picasso.Builder object
        Picasso.Builder picassoBuilder = new Picasso.Builder(mContext);

        // Picasso.Builder creates the Picasso object to do the actual requests
        Picasso picasso = picassoBuilder.build();

        picasso.load(photo.getUrls().getRegular())
                .resize(300, 300)
                .centerCrop()
                .into(holder.imageView);
    }

    public void addPhotos(List<Photo> photos){
        int lastCount = getItemCount();
        photoList.addAll(photos);
        notifyItemRangeInserted(lastCount, photos.size());
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imgview);
        }
    }



    public interface OnPhotoClickedListener {
        void photoClicked(Photo photo, ImageView imageView);
    }
}
