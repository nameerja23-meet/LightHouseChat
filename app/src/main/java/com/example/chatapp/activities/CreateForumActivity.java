package com.example.chatapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.databinding.ActivityCreateForumBinding;
import com.example.chatapp.utilities.Constants;
import com.example.chatapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateForumActivity extends AppCompatActivity {
    private ActivityCreateForumBinding binding;
    private PreferenceManager preferenceManager;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateForumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        database = FirebaseFirestore.getInstance();
        setListeners();

    }

//        submitPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String postTitle = postTitleEditText.getText().toString();
//                String postContent = postContentEditText.getText().toString();
//                upLoadData(postTitle, postContent);
//                Intent i = new Intent(CreateForumActivity.this, ForumDisplayActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
//    }


    private void showToast(String message) {
            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
        }

    private void createPost(){
        {
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            HashMap<String, Object> post = new HashMap<>();
            post.put(Constants.KEY_TITLE, binding.inputTitle.getText().toString());
            post.put(Constants.KEY_CONTENT, binding.inputContent.getText().toString());
            post.put(Constants.KEY_TIMESTAMP, new Date());
            post.put(Constants.KEY_TIMESTAMP, Constants.KEY_IMAGE);
            database.collection(Constants.KEY_COLLECTION_POSTS)
                    .add(post)
                    .addOnSuccessListener(documentReference -> {
                        preferenceManager.putString(Constants.KEY_TITLE, binding.inputTitle.getText().toString());
                        preferenceManager.putString(Constants.KEY_CONTENT, binding.inputContent.getText().toString());
                    })
                    .addOnFailureListener(exception -> {
                        showToast(exception.getMessage());
                    });
        }
    }

    private void setListeners(){
        binding.buttonSubmitPost.setOnClickListener(v -> createPost());
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }
}
