package com.androidavanzado.offlinemovies.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidavanzado.offlinemovies.R;
import com.androidavanzado.offlinemovies.data.local.entity.MovieEntity;
import com.androidavanzado.offlinemovies.data.network.Resource;
import com.androidavanzado.offlinemovies.viewmodel.MovieViewModel;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class MovieFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;

    MovieRecyclerViewAdapter movieAdapter;
    List<MovieEntity> movieList;
    MovieViewModel movieViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MovieFragment newInstance(int columnCount) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        movieViewModel = new ViewModelProvider(getActivity())
                .get(MovieViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            movieAdapter = new MovieRecyclerViewAdapter(getActivity(), movieList);
            recyclerView.setAdapter(movieAdapter);

            loadMovies();
        }
        return view;
    }

    private void loadMovies() {
        movieViewModel.getPopularMovies().observe(getActivity(), listResource -> {
            movieList = listResource.data;
            movieAdapter.setData(movieList);
        });
    }
}