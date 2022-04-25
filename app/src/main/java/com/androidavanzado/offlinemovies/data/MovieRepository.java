package com.androidavanzado.offlinemovies.data;

import com.androidavanzado.offlinemovies.data.remote.ApiConstants;
import com.androidavanzado.offlinemovies.data.remote.MovieApiService;
import com.androidavanzado.offlinemovies.data.remote.RequestInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private final MovieApiService movieApiService;

    public MovieRepository() {
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
