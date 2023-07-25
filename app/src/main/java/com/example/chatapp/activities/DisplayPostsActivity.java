    package com.example.chatapp.activities;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;

    import androidx.appcompat.app.AppCompatActivity;

    import com.example.chatapp.adapters.PostAdapter;
    import com.example.chatapp.databinding.ActivityDisplayBinding;
    import com.example.chatapp.models.Post;
    import com.example.chatapp.utilities.Constants;
    import com.example.chatapp.utilities.PreferenceManager;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.QueryDocumentSnapshot;


    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    import java.util.Locale;



    public class DisplayPostsActivity extends AppCompatActivity {

        private ActivityDisplayBinding binding;
        private FirebaseFirestore database;
        private PreferenceManager preferenceManager;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityDisplayBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            // Initialize Firestore
            database = FirebaseFirestore.getInstance();
            preferenceManager = new PreferenceManager(getApplicationContext());
            binding.textUsername.setText(preferenceManager.getString(Constants.KEY_USERNAME));
            // Retrieve user posts
            retrievePosts();
            setListeners();
        }


        private void retrievePosts() {
            database.collection(Constants.KEY_COLLECTION_POSTS)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            List<Post> posts = new ArrayList<>();
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                Post post = new Post();
                                post.username = queryDocumentSnapshot.getString(Constants.KEY_USERNAME);
                                post.title = queryDocumentSnapshot.getString(Constants.KEY_TITLE);
                                post.content = queryDocumentSnapshot.getString(Constants.KEY_CONTENT);
                                post.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                                post.dateTime = getReadableDateTime(queryDocumentSnapshot.getDate(Constants.KEY_TIMESTAMP));
                                post.dateObject = queryDocumentSnapshot.getDate(Constants.KEY_TIMESTAMP);

                                posts.add(post);
                            }
                            if (posts.size()>0){
                                PostAdapter postAdapter = new PostAdapter(posts);
                                binding.postsRecyclerView.setAdapter(postAdapter);
                                binding.postsRecyclerView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            showErrorMessage();
                        }
                    });
        }

        private String getReadableDateTime(Date date) {
            return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
        }

        private void showErrorMessage() {
            binding.textErrorMessage.setText(String.format("%$, ","No posts available"));
        }
        private void setListeners(){
            binding.imageBack.setOnClickListener(v -> onBackPressed());
            binding.fabNewChat.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CreatePostActivity.class)));
        }
    }
