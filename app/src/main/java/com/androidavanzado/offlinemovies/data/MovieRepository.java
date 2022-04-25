package com.androidavanzado.offlinemovies.data;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.androidavanzado.offlinemovies.common.MyApp;
import com.androidavanzado.offlinemovies.data.local.MovieRoomDatabase;
import com.androidavanzado.offlinemovies.data.local.dao.MovieDao;
import com.androidavanzado.offlinemovies.data.remote.ApiConstants;
import com.androidavanzado.offlinemovies.data.remote.MovieApiService;
import com.androidavanzado.offlinemovies.data.remote.RequestInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private final MovieApiService movieApiService;
    private final MovieDao movieDao;

    public MovieRepository() {
        // datos locales
        MovieRoomDatabase movieRoomDatabase = Room.databaseBuilder(
                MyApp.getContext(),
                MovieRoomDatabase.class,
                "db_movies"
        ).build();
        movieDao = movieRoomDatabase.getMovieDao();

        // Request interceptor
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addNetworkInterceptor(new RequestInterceptor());
        OkHttpClient client = okHttpClientBuilder.build();

        // Remote retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieApiService = retrofit.create(MovieApiService.class);
    }

}
