package com.bvbach.app_xem_phim_bui_van_bach;

import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton, registerButton;
    private TextureView videoBackground;
    private MediaPlayer mediaPlayer;

    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        videoBackground = findViewById(R.id.videoBackground);

        videoBackground.setSurfaceTextureListener(this);

        // Xử lý đăng nhập
        loginButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
                // Đăng nhập thành công
                Toast.makeText(MainActivity.this, "Đăng nhập thành công! Đang load dữ liệu phim !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
                startActivity(intent);
            } else {
                // Đăng nhập thất bại
                Toast.makeText(MainActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(view -> {
            // Xử lý đăng ký
        });
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        playBackgroundVideo(new Surface(surface));
    }

    private void playBackgroundVideo(Surface surface) {
        mediaPlayer = MediaPlayer.create(this, Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.background_login));
        mediaPlayer.setSurface(surface);
        mediaPlayer.setLooping(true); // Lặp lại video
        mediaPlayer.setVolume(0f, 0f); // Tắt tiếng
        mediaPlayer.start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {}

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {}
}
