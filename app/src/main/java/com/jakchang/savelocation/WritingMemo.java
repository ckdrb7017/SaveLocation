package com.jakchang.savelocation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.jakchang.savelocation.Entity.MemoEntity;
import com.jakchang.savelocation.Repository.AppDatabase;
import com.jakchang.savelocation.Utils.DataHolder;
import com.jakchang.savelocation.databinding.ActivityWritememoBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WritingMemo extends AppCompatActivity {
    ActivityWritememoBinding binding;
    final int MAIN_IMAGE = 1000, SUB1_IMAGE = 1001,SUB2_IMAGE = 1002, SUB3_IMAGE = 1003;
    String[] uri;
    Intent intent;
    String date_text,year,month,day;
    String selectedYear,selectedMonth,selectedDay;
    Date currentTime;
    MemoEntity memoEntity;
    DataHolder dataHolder;
    String lat,lng;
    AppDatabase db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_writememo);
        binding.setActivity(this);

        Display display = getWindowManager().getDefaultDisplay();
        Point point =  new Point();
        display.getSize(point);
        binding.linearLayout2.getLayoutParams().height = point.y/8;
        binding.titleLayout.getLayoutParams().height = point.y/17;
        binding.text.getLayoutParams().height = point.y/2;

        uri = new String[4];
        dateInit();
        binding.date.setPaintFlags(binding.date.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        binding.imageView0.setClipToOutline(true);
        binding.imageView1.setClipToOutline(true);
        binding.imageView2.setClipToOutline(true);
        binding.imageView3.setClipToOutline(true);
        lat = (String) dataHolder.getDataHolder("lat");
        lng = (String) dataHolder.getDataHolder("lng");
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/air.ttf");
        binding.text.setTypeface(typeface);

        for(int i=0;i<4;i++) uri[i]="";

    }


    public void dateInit(){
        currentTime = Calendar.getInstance().getTime();
        date_text= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTime);
        year = date_text.split("-")[0];
        month = date_text.split("-")[1];
        day = date_text.split("-")[2];
        binding.date.setText(year+"-"+month+"-"+day);

    }

    public void onDateClicked(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedYear = ""+year;
                selectedMonth = ""+(month+1);
                if(dayOfMonth<10) selectedDay= "0"+dayOfMonth;
                else selectedDay= ""+dayOfMonth;
                binding.date.setText(selectedYear+"-"+selectedMonth+"-"+selectedDay);
            }
        },Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day));

        datePickerDialog.setMessage("메시지");
        datePickerDialog.show();
    }

    public void onImageClicked(View view){
        int requestCode=0;
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        if (view.getId() == R.id.imageView0) {
            requestCode=MAIN_IMAGE;
        } else if (view.getId() == R.id.imageView1) {
            requestCode=SUB1_IMAGE;
        } else if (view.getId() == R.id.imageView2) {
            requestCode=SUB2_IMAGE;
        } else if (view.getId() == R.id.imageView3) {
            requestCode=SUB3_IMAGE;
        }
        startActivityForResult(intent.createChooser(intent, "이미지 선택"), requestCode);
    }
    public void onCancelClicked(View v){
        finish();
    }
    public void onInsertClicked(View view){
        if(uri[0].equals("")){
            Toast.makeText(getApplicationContext(),"첫번째 이미지를 등록해주세요.", Toast.LENGTH_LONG).show();
            return;
        }
        Log.d("TAG",binding.date.getText().toString());
        memoEntity = new MemoEntity();
        memoEntity.setLatitude(lat);
        memoEntity.setLongitude(lng);
        memoEntity.setTag(binding.taglist.getSelectedItem().toString());
        memoEntity.setDate(binding.date.getText().toString());
        memoEntity.setUri1(uri[0]);
        memoEntity.setUri2(uri[1]);
        memoEntity.setUri3(uri[2]);
        memoEntity.setUri4(uri[3]);
        memoEntity.setTitle(binding.title.getText().toString());
        memoEntity.setText(binding.text.getText().toString());
        memoEntity.setFontType("0");
        memoEntity.setIsDeleted("true");
        //dbHelper.insert(memoEntity);
        GetData getData = new GetData();
        getData.execute();
        setResult(3001);
        Toast.makeText(getApplicationContext(),"메모가 등록되었습니다.",Toast.LENGTH_LONG).show();
        finish();
    }
    private class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
        @Override
        protected String doInBackground(String... params) {
            db = AppDatabase.getInstance(getApplicationContext());
            db.MemoDao().insertMemo(memoEntity);
            return "";
        }

    }//GetData

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==MAIN_IMAGE && resultCode== Activity.RESULT_OK){
            Uri selectImageUri = data.getData();
            getImage(binding.imageView0,selectImageUri.toString());
            uri[0]= selectImageUri.toString();
        }//if - Main
        else if(requestCode==SUB1_IMAGE && resultCode== Activity.RESULT_OK){
            Uri selectImageUri = data.getData();
            getImage(binding.imageView1,selectImageUri.toString());
            uri[1]= selectImageUri.toString();
        }//else if - Sub2
        else if(requestCode==SUB2_IMAGE && resultCode== Activity.RESULT_OK){
            Uri selectImageUri = data.getData();
            getImage(binding.imageView2,selectImageUri.toString());
            uri[2]= selectImageUri.toString();
        }//else if - Sub3
        else if(requestCode==SUB3_IMAGE && resultCode== Activity.RESULT_OK){
            Uri selectImageUri = data.getData();
            getImage(binding.imageView3,selectImageUri.toString());
            uri[3]= selectImageUri.toString();
        }
    }


    public void getImage(ImageView imageView, String uri){
        Glide.with(this)
                .load(uri)
                .centerCrop()
                .override(300,300)
                .error(R.drawable.no_image)
                .into(imageView);
    }


}
