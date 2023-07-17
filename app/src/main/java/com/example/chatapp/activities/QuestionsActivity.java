package com.example.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.chatapp.R;
import com.example.chatapp.databinding.ActivityQuestionsBinding;
import com.example.chatapp.utilities.PreferenceManager;

public class QuestionsActivity extends AppCompatActivity {

    private ActivityQuestionsBinding binding;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionsBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners(){
        binding.buttonSubmit.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UsersActivity.class)));
    }


}