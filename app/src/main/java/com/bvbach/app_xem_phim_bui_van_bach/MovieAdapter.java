package com.bvbach.app_xem_phim_bui_van_bach;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.movieTitle.setText(movie.getTitle());

        // Load ảnh poster bằng Glide, sử dụng posterPath từ TMDB
        String posterUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath(); // Định dạng URL ảnh poster của TMDB
        Log.d("PosterUrl", posterUrl);
        Glide.with(context)
                .load(posterUrl)
                .placeholder(R.drawable.load) // Ảnh mặc định nếu load thất bại
                .into(holder.moviePoster);



        // Bắt sự kiện khi click vào item
        holder.itemView.setOnClickListener(v -> {
            // Truyền movieId và các thông tin cần thiết sang MovieDetailActivity
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("movieId", movie.getId()); // Truyền ID của phim
            intent.putExtra("title", movie.getTitle()); // Truyền tiêu đề của phim
            intent.putExtra("posterPath", movie.getPosterPath()); // Truyền posterPath của phim
            intent.putExtra("description", movie.getDescription()); // Truyền mô tả phim (nếu có)
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void updateList(List<Movie> newList) {
        this.movieList = newList;
        notifyDataSetChanged();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        ImageView moviePoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            moviePoster = itemView.findViewById(R.id.moviePoster);
        }
    }
}
