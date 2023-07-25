package com.example.chatapp.models;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
        public String title, content, username, image, dateTime, posterId;
        public Date dateObject;
}
