package com.example.chatapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.chatapp.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.chatapp.R;
import com.example.chatapp.activities.AIChatActivity;
import com.example.chatapp.activities.AboutUsActivity;
import com.example.chatapp.activities.ForumDisplayActivity;
import com.example.chatapp.activities.QuestionsActivity;
import com.example.chatapp.activities.SignInActivity;
import com.example.chatapp.databinding.ActivityHomeBinding;
import com.example.chatapp.utilities.Constants;
import com.example.chatapp.utilities.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Base64;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import android.view.MenuItem;


import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private PreferenceManager preferenceManager;
    private BottomNavigationView bottomNavigationView;
    public static final int HOME_NAV_ID = R.id.home_naviBar;
    public static final int BACK_NAV_ID = R.id.back_naviBar;
    public static final int ABOUT_US_NAV_ID = R.id.about_us_naviBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());
        bottomNavigationView = findViewById(R.id.NavigationBar);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item clicks here


                if (item.getItemId()==HOME_NAV_ID){
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
               else  if (item.getItemId()==BACK_NAV_ID){
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                }
            else{
                    startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                }
                return true; // Return true to indicate the event is consumed
            }
        });

        loadUserDetails();
        getToken();
        setListeners();
    }

    private void setListeners() {
        binding.imageSignOut.setOnClickListener(v -> signOut());
        binding.buttonChat.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QuestionsActivity.class)));
        binding.buttonAboutUs.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AboutUsActivity.class)));
        binding.buttonForum.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ForumDisplayActivity.class)));
        binding.buttonAI.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AIChatActivity.class)));
    }

    private void loadUserDetails() {
        binding.textName.setText(preferenceManager.getString(Constants.KEY_USERNAME));
        byte[] bytes = Base64.decodeBase64(preferenceManager.getString(Constants.KEY_IMAGE));
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        binding.imageProfile.setImageBitmap(bitmap);
    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS).document(
                preferenceManager.getString(Constants.KEY_USER_ID)
        );
        documentReference.update(Constants.KEY_FCM_TOKEN, token)
                .addOnFailureListener(e -> showToast("Unable to update token"));
    }

    private void signOut() {
        showToast("Signing Out...");
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(
                        preferenceManager.getString(Constants.KEY_USER_ID)
                );
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {
                    preferenceManager.clear();
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> showToast("Unable to sign out"));
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
