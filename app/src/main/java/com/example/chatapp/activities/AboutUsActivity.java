package com.example.chatapp.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.databinding.ActivityAboutUsBinding;
import com.example.chatapp.utilities.Constants;
import com.example.chatapp.utilities.PreferenceManager;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Base64;

public class AboutUsActivity extends AppCompatActivity {

    private ActivityAboutUsBinding binding;
    private PreferenceManager preferenceManager;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            preferenceManager = new PreferenceManager(getApplicationContext());
            loadUserDetails();
            setListeners();
        }


    private void setListeners() {
        binding.imageBack.setOnClickListener(view -> onBackPressed());
    }
    private void loadUserDetails() {
        binding.textUsername.setText(preferenceManager.getString(Constants.KEY_USERNAME));
        byte[] bytes = Base64.decodeBase64(preferenceManager.getString(Constants.KEY_IMAGE));
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        //binding.imageProfile.setImageBitmap(bitmap);
    }
}