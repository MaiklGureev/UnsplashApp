package com.gureev.unsplashapp.Tools;

import com.gureev.unsplashapp.Types.Collection;
import com.gureev.unsplashapp.Types.Photo;
import com.gureev.unsplashapp.Types.SearchResults;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UnsplashInterface {
    @GET("photos/{id}")
    Call<Photo> getPhoto(@Path("id") String id, @Query("w") Integer width, @Query("h") Integer height);

    @GET("photos")
    Call<List<Photo>> getPhotos(@Query("page") Integer page, @Query("per_page") Integer perPage, @Query("order_by") String orderBy);

    @GET("photos/curated")
    Call<List<Photo>> getCuratedPhotos(@Query("page") Integer page, @Query("per_page") Integer perPage, @Query("order_by") String orderBy);

    @GET("photos/random")
    Call<List<Photo>> getRandomPhoto(@Query("collections") String collections, @Query("featured") Boolean featured, @Query("username") String username, @Query("query") String query, @Query("orientation") String orientation, @Query("count") Integer count);

    @GET("photos/random")
    Call<List<Photo>> getRandomPhotos(@Query("collections") String collections, @Query("featured") boolean featured, @Query("username") String username, @Query("query") String query, @Query("w") Integer width, @Query("h") Integer height, @Query("orientation") String orientation, @Query("count") Integer count);

    @GET("photos/{id}/download")
    Call<Download> getPhotoDownloadLink(@Path("id") String id);

    @GET("search/photos")
    Call<SearchResults> searchPhotos(@Query("query") String query, @Query("page") Integer page, @Query("per_page") Integer perPage, @Query("collections") String collections, @Query("orientation") String orientation);

    @GET("collections/{id}/photos")
    Call<List<Photo>> getCollectionPhotos(@Path("id") String id, @Query("page") Integer page, @Query("per_page") Integer perPage);

    @GET("collections")
    Call<List<Collection>> getCollections(@Query("page") Integer page, @Query("per_page") Integer per_page);
}
