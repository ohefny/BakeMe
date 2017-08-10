package com.example.bethechange.nanobakingapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BeTheChange on 9/8/2017.
 */

public class Step {
    @SerializedName("id")
    private int mId;
    @SerializedName("description")
    private String mFullDescription;
    @SerializedName("shortDescription")
    private String mSnippetDescription;
    @SerializedName("thumbnailURL")
    private String mThumbnailImg;
    @SerializedName("videoURL")
    private String mVideoLink;


    int recipeID;
    public Step() {
    }
    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getFullDescription() {
        return mFullDescription;
    }

    public void setFullDescription(String mFullDescription) {
        this.mFullDescription = mFullDescription;
    }


    public String getSnippetDescription() {
        return mSnippetDescription;
    }

    public void setSnippetDescription(String mSnippetDescription) {
        this.mSnippetDescription = mSnippetDescription;
    }

    public String getThumbnailImg() {
        return mThumbnailImg;
    }

    public void setThumbnailImg(String mThumbnailImg) {
        this.mThumbnailImg = mThumbnailImg;
    }

    public String getVideoLink() {
        return mVideoLink;
    }

    public void setVideoLink(String mVideoLink) {
        this.mVideoLink = mVideoLink;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }
}