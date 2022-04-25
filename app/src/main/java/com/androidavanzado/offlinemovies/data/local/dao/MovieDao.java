package com.androidavanzado.offlinemovies.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.androidavanzado.offlinemovies.data.local.entity.MovieEntity;

import java.util.List;

public interface MovieDao {
    @Query("SELECT * FROM movies")
    LiveData<List<MovieEntity>> loadPopularMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void SaveMovies(List<MovieEntity> movieEntityList);

}
