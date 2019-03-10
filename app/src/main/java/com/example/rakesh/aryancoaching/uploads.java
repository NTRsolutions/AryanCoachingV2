package com.example.rakesh.aryancoaching;

import com.google.firebase.database.Exclude;

public class uploads {
    private String mName;
    private String mImageUrl;
    private String mKey;

    public uploads() {

    }

    public uploads(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";

        }
        mName = name;
        mImageUrl = imageUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imagUrl) {
        mImageUrl = imagUrl;
    }

    @Exclude
    public String getKey() {
        return mKey;

    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}
