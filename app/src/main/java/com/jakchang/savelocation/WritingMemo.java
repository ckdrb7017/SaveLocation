package com.jakchang.savelocation;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.jakchang.savelocation.databinding.ActivityWritememoBinding;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class WritingMemo extends AppCompatActivity {
    ActivityWritememoBinding binding;
    final int MAIN_IMAGE = 1000, SUB1_IMAGE = 1001,SUB2_IMAGE = 1002, SUB3_IMAGE = 1003, SUB4_IMAGE = 1004;
    Bitmap main_img,sub1_img,sub2_img,sub3_img,sub4_img;
    Uri[] uri;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_writememo);
        binding.setActivity(this);
        uri = new Uri[4];
    }

    public void onButtonClicked(View view) {
        if (view.getId() == R.id.insertBtn) {

            for(int i=0;i<4;i++){
                if(uri[i]!=null){
                    String key="imageView"+i;
                    DataHolder.putDataHolder(key,uri[i]);

                }else{

                }
            }
           intent = new Intent(getApplicationContext(),ImageViewPage.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.cancelBtn) {

        }
    }
    public void onImageClicked(View view){
        if(view.getId()==R.id.imageView0){
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent.createChooser(intent,"select Image"),MAIN_IMAGE);
        } else if(view.getId()==R.id.imageView1){
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent.createChooser(intent,"select Image"),SUB1_IMAGE);
        } else if(view.getId()==R.id.imageView2){
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent.createChooser(intent,"select Image"),SUB2_IMAGE);
        } else if(view.getId()==R.id.imageView3){
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent.createChooser(intent,"select Image"),SUB3_IMAGE);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int width = binding.imageView1.getWidth();
        int height = binding.imageView1.getHeight();

        if(requestCode==MAIN_IMAGE && resultCode== Activity.RESULT_OK){
            Uri selectImageUri = data.getData();

            try {
                InputStream is = getContentResolver().openInputStream(selectImageUri);
                main_img = BitmapFactory.decodeStream(is);
                main_img = Bitmap.createScaledBitmap(main_img, width, height,true);
                binding.imageView0.setImageBitmap(main_img);
                uri[0]= selectImageUri;//getRealPathFromURI(selectImageUri);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }//if - Main

        else if(requestCode==SUB1_IMAGE && resultCode== Activity.RESULT_OK){
            Uri selectImageUri = data.getData();

            try {
                InputStream is = getContentResolver().openInputStream(selectImageUri);
                sub1_img = BitmapFactory.decodeStream(is);
                sub1_img = Bitmap.createScaledBitmap(sub1_img, width,height,true);
                binding.imageView1.setImageBitmap(sub1_img);
                uri[1]= selectImageUri;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }//else if - Sub2
        else if(requestCode==SUB2_IMAGE && resultCode== Activity.RESULT_OK){
            Uri selectImageUri = data.getData();

            try {
                InputStream is = getContentResolver().openInputStream(selectImageUri);
                sub2_img = BitmapFactory.decodeStream(is);
                sub2_img = Bitmap.createScaledBitmap(sub2_img, width,height,true);
                binding.imageView2.setImageBitmap(sub2_img);
                uri[2]= selectImageUri;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }//else if - Sub3
        else if(requestCode==SUB3_IMAGE && resultCode== Activity.RESULT_OK){
            Uri selectImageUri = data.getData();
            try {
                InputStream is = getContentResolver().openInputStream(selectImageUri);
                sub3_img = BitmapFactory.decodeStream(is);
                sub3_img = Bitmap.createScaledBitmap(sub3_img, width,height,true);
                binding.imageView3.setImageBitmap(sub3_img);
                uri[3]= selectImageUri;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
