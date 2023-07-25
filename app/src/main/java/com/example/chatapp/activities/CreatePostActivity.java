    package com.example.chatapp.activities;

    import android.os.Bundle;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;

    import com.example.chatapp.databinding.ActivityCreateForumBinding;
    import com.example.chatapp.utilities.Constants;
    import com.example.chatapp.utilities.PreferenceManager;
    import com.google.firebase.firestore.FirebaseFirestore;

    import java.util.Date;
    import java.util.HashMap;

    public class CreatePostActivity extends AppCompatActivity {
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
                post.put(Constants.KEY_IMAGE, Constants.KEY_IMAGE);
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
            binding.buttonSubmitPost.setOnClickListener(v -> {
                createPost();
                onBackPressed();
            });
            binding.imageBack.setOnClickListener(v -> onBackPressed());
        }
    }