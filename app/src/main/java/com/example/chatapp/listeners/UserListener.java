package com.example.chatapp.listeners;

import com.example.chatapp.models.User;

public interface UserListener {
    default void onUserClicked(User user){

    }

}
