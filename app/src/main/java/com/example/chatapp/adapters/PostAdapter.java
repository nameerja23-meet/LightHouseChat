package com.example.chatapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.databinding.ItemContainerPostBinding;
import com.example.chatapp.models.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
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
        }
    }
}
