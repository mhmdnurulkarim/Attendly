package com.oci.presensi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.oci.presensi.databinding.ActivitySplashBinding;
import com.oci.presensi.util.PreferenceUtils;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (isUserLoggedIn()) {
                goToPage(HomeActivity.class);
            } else {
                goToPage(LoginActivity.class);
            }
        }, 2000);

    }

    private void goToPage(Class<?> destinationActivity) {
        Intent intent = new Intent(SplashActivity.this, destinationActivity);
        startActivity(intent);
        finish();
    }

    private boolean isUserLoggedIn() {
        return PreferenceUtils.getIdAkun(this) != 0;
    }
}