package com.bvbach.app_xem_phim_bui_van_bach;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private List<Movie> originalMovieList; // Danh sách gốc để reset
    private EditText searchEditText;
    private ImageButton searchButton;
    private Button btnHome, btnProfile;

    private static final String API_KEY = "ec01da87da049ffc3fbd687ac6f1b508"; // Thay bằng khóa API của bạn
    private static final String LANGUAGE = "en-US"; // Ngôn ngữ phim
    private static final int PAGE = 1; // Trang phim

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        recyclerView = findViewById(R.id.movieRecyclerView);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        movieList = new ArrayList<>();
        originalMovieList = new ArrayList<>();

        // Khởi tạo Retrofit và gọi API TMDB để lấy danh sách phim
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<MovieResponse> call = apiService.getPopularMovies(API_KEY, LANGUAGE, PAGE);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    originalMovieList.addAll(response.body().getResults());
                    movieList.addAll(originalMovieList);
                    movieAdapter = new MovieAdapter(movieList, MovieListActivity.this);
                    recyclerView.setAdapter(movieAdapter);
                    Log.d("APIResponse", response.body().toString());
                } else {
                    Toast.makeText(MovieListActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e("APIError", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MovieListActivity.this, "Failed to load movies", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện khi nhấn nút tìm kiếm
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        // Nút Trang Chủ (Hiện tại giữ nguyên)
        Button btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(v -> {
            // Giữ nguyên trang hiện tại
        });

        // Nút Profile - Chuyển đến ProfileActivity
        Button btnProfile = findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MovieListActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    // Hàm xử lý tìm kiếm
    private void performSearch() {
        String query = searchEditText.getText().toString().trim();
        movieList.clear(); // Xóa danh sách hiện tại

        if (query.isEmpty()) {
            movieList.addAll(originalMovieList); // Nếu ô tìm kiếm trống, hiển thị lại danh sách đầy đủ
        } else {
            for (Movie movie : originalMovieList) {
                if (movie.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    movieList.add(movie);
                }
            }
        }

        movieAdapter.notifyDataSetChanged(); // Cập nhật lại RecyclerView
    }
}
