package com.androidavanzado.offlinemovies.UI;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidavanzado.offlinemovies.R;
import com.androidavanzado.offlinemovies.data.local.entity.MovieEntity;
import com.androidavanzado.offlinemovies.data.remote.ApiConstants;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MovieEntity}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private final List<MovieEntity> mValues;
    private final Context mContext;

    public MovieRecyclerViewAdapter(Context ctx, List<MovieEntity> items) {
        mValues = items;
        mContext = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // return new ViewHolder(FragmentMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Glide.with(mContext)
                .load(ApiConstants.IMAGE_API_PREFIX + holder.mItem.getPosterPath())
                .into(holder.imageViewCover);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public MovieEntity mItem;
        public ImageView imageViewCover;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageViewCover = view.findViewById(R.id.imageViewCover);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.getTitle() + "'";
        }
    }
}