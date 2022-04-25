package com.androidavanzado.offlinemovies.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.androidavanzado.offlinemovies.common.MyApp;
import com.androidavanzado.offlinemovies.data.local.MovieRoomDatabase;
import com.androidavanzado.offlinemovies.data.local.dao.MovieDao;
import com.androidavanzado.offlinemovies.data.local.entity.MovieEntity;
import com.androidavanzado.offlinemovies.data.network.NetworkBoundResource;
import com.androidavanzado.offlinemovies.data.network.Resource;
import com.androidavanzado.offlinemovies.data.remote.ApiConstants;
import com.androidavanzado.offlinemovies.data.remote.MovieApiService;
import com.androidavanzado.offlinemovies.data.remote.RequestInterceptor;
import com.androidavanzado.offlinemovies.data.remote.model.MoviesResponse;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
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

    public LiveData<Resource<List<MovieEntity>>> getPopularMovies() {
        // Pimero: Tipo q devuelve room
        // Segundo: El tipo que devuelve la API con retrofit
        return new NetworkBoundResource<List<MovieEntity>, MoviesResponse>(){

            @Override
            protected void saveCallResult(@NonNull MoviesResponse item) {
                movieDao.SaveMovies(item.getResults());
            }

            @NonNull
            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                // Los datos que dispongamos en la base de datos local
                return movieDao.loadPopularMovies();
            }

            @NonNull
            @Override
            protected Call<MoviesResponse> createCall() {
                // Los datos a la api externa
                return movieApiService.loadPupularMovies();
            }
        }.getAsLiveData();
    }

}
