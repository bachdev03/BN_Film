package com.bvbach.app_xem_phim_bui_van_bach;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText oldPasswordEditText, newPasswordEditText, confirmPasswordEditText;
    private Button changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPasswordEditText = findViewById(R.id.oldPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        changePasswordButton.setOnClickListener(v -> {
            String oldPassword = oldPasswordEditText.getText().toString().trim();
            String newPassword = newPasswordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            // Lấy mật khẩu hiện tại từ SharedPreferences
            SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
            String savedPassword = preferences.getString("password", "");

            if (!oldPassword.equals(savedPassword)) {
                Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, "Mật khẩu mới không khớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lưu mật khẩu mới vào SharedPreferences
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("password", newPassword);
            editor.apply();

            Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
