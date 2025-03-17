package com.bvbach.app_xem_phim_bui_van_bach;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView movieTitle, movieDescription;
    private ImageView moviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Ánh xạ các thành phần UI
        movieTitle = findViewById(R.id.movieTitle);
        movieDescription = findViewById(R.id.movieDescription);
        moviePoster = findViewById(R.id.moviePoster);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String posterPath = intent.getStringExtra("posterPath");

        // Hiển thị thông tin lên UI
        movieTitle.setText(title);
        movieDescription.setText(description);

        // Load ảnh poster sử dụng Glide
        String posterUrl = "https://image.tmdb.org/t/p/w500" + posterPath; // Dựng lại URL của poster
        Glide.with(this)
                .load(posterUrl)
                .placeholder(R.drawable.load)  // Ảnh mặc định khi chưa load xong
                .into(moviePoster);
        Log.d("PosterUrl", posterUrl);
    }
}
