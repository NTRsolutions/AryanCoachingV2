package com.example.rakesh.aryancoaching;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;

import com.squareup.picasso.Picasso;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.ref.WeakReference;

public class SaveImageHelper implements com.squareup.picasso.Target {
    private Context context;

    private WeakReference<AlertDialog.Builder> alertDialogWeakReference;
    private WeakReference<ContentResolver> contentResolverWeakReference;
    private String name;
    private String desc;
    public SaveImageHelper(Context context,AlertDialog.Builder alertDialog,ContentResolver contentResolver ,String name,String desc)
    {
        this.alertDialogWeakReference = new WeakReference<AlertDialog.Builder>(alertDialog);
        this.contentResolverWeakReference = new WeakReference<ContentResolver>(contentResolver);
        this.name = name;
        this.desc = desc;
        this.context = context;
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ContentResolver r = contentResolverWeakReference.get();
        AlertDialog.Builder dialog = alertDialogWeakReference.get();
        if(r != null)
            MediaStore.Images.Media.insertImage(r,bitmap,name,desc);


        // open gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivity(Intent.createChooser(intent,"View Picture"));


    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
