package com.jakchang.savelocation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.jakchang.savelocation.Adapter.ViewPagerAdapter;
import com.jakchang.savelocation.Utils.DataHolder;

import java.util.ArrayList;

public class ImageViewPage extends AppCompatActivity {
    Intent intent;
    ArrayList<Bitmap>  bitmap;
    ArrayList<Bitmap>  resultMap;
    Bitmap temp;
    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
    int width,height;
    Uri[] uri;
    String[] path;
    PhotoViewAttacher attacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageviewpage);
        ImageView imageView = findViewById(R.id.extendedImg);
        bitmap = new ArrayList<Bitmap>();
        uri = new Uri[4];
        attacher = new PhotoViewAttacher(imageView);

        for(int i=0; i<4;i++) {
            String key="imageView"+i;
            if(DataHolder.getDataHolder(key)!=null) {
                uri[i] = (Uri) DataHolder.popDataHolder(key);
                Log.d("TAG","key : " +key);
            }else{
                Log.d("TAG","NUll");
            }
        }


        try {
            for(int i=0; i<4;i++) {
                if(uri[i]!=null) {
                    bitmap.add(MediaStore.Images.Media.getBitmap(getContentResolver(), uri[i]));
                    Log.d("TAG","bitmap : " +bitmap.size());
                }
            }

        }catch (Exception e){

        }
        //int count = Integer.parseInt(intent.getStringExtra("index"));


        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(this,bitmap,0);
        viewPager.setAdapter(viewPagerAdapter);


    }


}
