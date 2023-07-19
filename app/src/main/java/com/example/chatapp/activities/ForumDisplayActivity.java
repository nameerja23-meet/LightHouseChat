    package com.example.chatapp.activities;

    import android.os.Bundle;
    import android.view.View;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.chatapp.R;
    import com.example.chatapp.adapters.PostAdapter;
    import com.example.chatapp.databinding.ActivityDisplayBinding;
    import com.example.chatapp.models.Post;
    import com.example.chatapp.models.User;
    import com.example.chatapp.utilities.Constants;
    import com.example.chatapp.utilities.PreferenceManager;
    import com.google.firebase.firestore.CollectionReference;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.QueryDocumentSnapshot;
    import com.google.firebase.firestore.QuerySnapshot;

    import java.util.ArrayList;
    import java.util.List;

    public class ForumDisplayActivity extends AppCompatActivity {

        private ActivityDisplayBinding binding;
        private PreferenceManager preferenceManager;
        private PostAdapter adapter;
        private List<Post> posts;
        private FirebaseFirestore database;
        private CollectionReference postsCollection;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityDisplayBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            posts = new ArrayList<>();
            adapter = new PostAdapter(posts);
            binding.postsRecyclerView.setAdapter(adapter);

            // Initialize Firestore
            database = FirebaseFirestore.getInstance();
            postsCollection = database.collection("Documents");
            // Retrieve user posts
            retrievePosts();
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
//        private void retrievePosts() {
//            postsCollection.get().addOnCompleteListener(task -> {
//                if (task.isSuccessful() && task.getResult() != null) {
//                    List<Post> posts = new ArrayList<>();
//                    QuerySnapshot querySnapshot = task.getResult();
//                    if (querySnapshot != null) {
//                        for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
//                            Post post = new Post();
//                            //post.title =
//                        }
//                    }
//                    adapter.setPosts(posts);
//                } else {
//                    showToast("Failed to retrieve posts");
//                }
//            });
//        }
        private void showErrorMessage() {
            binding.textErrorMessage.setText(String.format("%$, ","No posts available"));
        }
    }
