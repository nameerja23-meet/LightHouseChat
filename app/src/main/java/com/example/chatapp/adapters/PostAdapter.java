package com.example.chatapp.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.databinding.ItemContainerPostBinding;
import com.example.chatapp.models.Post;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Base64;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final List<Post> posts;


    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerPostBinding itemContainerPostBinding = ItemContainerPostBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new PostAdapter.PostViewHolder(itemContainerPostBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.setPost(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }



    class PostViewHolder extends RecyclerView.ViewHolder {
        ItemContainerPostBinding binding;

        public PostViewHolder(ItemContainerPostBinding itemContainerPostBinding) {
            super(itemContainerPostBinding.getRoot());
            binding = itemContainerPostBinding;

        }

        void setPost(Post post){
            binding.textTitle.setText(post.title);
            binding.textContent.setText(post.content);
            binding.textDateTime.setText(post.dateTime);
            binding.imageProfile.setImageBitmap(getUserImage(post.image));
        }
    }

    private Bitmap getUserImage(String encodedImage) {
        byte[] bytes = Base64.decodeBase64(encodedImage);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
