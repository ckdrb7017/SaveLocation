package com.jakchang.savelocation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.jakchang.savelocation.Entity.MemoEntity;
import com.jakchang.savelocation.Interface.Callback;
import com.jakchang.savelocation.Repository.AppDatabase;
import com.jakchang.savelocation.Utils.DataHolder;
import com.jakchang.savelocation.Utils.Dialog;
import com.jakchang.savelocation.databinding.ActivityViewmemoBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ViewMemo extends AppCompatActivity {
    ActivityViewmemoBinding binding;
    final int MAIN_IMAGE = 1000, SUB1_IMAGE = 1001,SUB2_IMAGE = 1002, SUB3_IMAGE = 1003;
    MemoEntity memoEntity ;
    Intent intent;
    String date_text,year,month,day;
    String selectedYear,selectedMonth,selectedDay;
    Date currentTime;
    String[] uri;
    int id,flag=0;
    Resources res;
    String[] resList;
    public static AppDatabase db;
    ArrayAdapter spinnerAdapter;
    Typeface typeface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viewmemo);
        binding.setActivity(this);
        Intent intent =getIntent();

        if(intent.getIntExtra("delete",0)!=0) binding.buttonLayout.setVisibility(View.INVISIBLE);
        Display display = getWindowManager().getDefaultDisplay();
        Point point =  new Point();
        display.getSize(point);
        binding.linearLayout2.getLayoutParams().height = point.y/8;
        binding.titleLayout.getLayoutParams().height = point.y/17;
        binding.text.getLayoutParams().height = point.y/2;

        intent = getIntent();
        uri = new String[4];
        id = intent.getIntExtra("id",1);

        binding.imageView0.setClipToOutline(true);
        binding.imageView1.setClipToOutline(true);
        binding.imageView2.setClipToOutline(true);
        binding.imageView3.setClipToOutline(true);

        db = AppDatabase.getInstance(this);
        memoEntity = db.MemoDao().selectOne(id);

        uri[0]=memoEntity.getUri1();
        uri[1]=memoEntity.getUri2();
        uri[2]=memoEntity.getUri3();
        uri[3]=memoEntity.getUri4();

        res = getResources();
        resList = res.getStringArray(R.array.tag_spinner);
        for(int i=0;i<resList.length;i++){
            if(resList[i].equals(memoEntity.getTag())){
                binding.taglist.setSelection(i);
                break;
            }
        }




        binding.date.setPaintFlags(binding.date.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        binding.date.setText(memoEntity.getDate());
        year =  binding.date.getText().toString().split("-")[0];
        month = binding.date.getText().toString().split("-")[1];
        day = binding.date.getText().toString().split("-")[2];
        binding.date.setText(year+"-"+month+"-"+day);

        getImage(binding.imageView0,memoEntity.getUri1());
        getImage(binding.imageView1,memoEntity.getUri2());
        getImage(binding.imageView2,memoEntity.getUri3());
        getImage(binding.imageView3,memoEntity.getUri4());
        binding.title.setText(memoEntity.getTitle());
        binding.text.setText(memoEntity.getText());
        typeface = Typeface.createFromAsset(getAssets(), memoEntity.getFontType());
        binding.title.setTypeface(typeface);
        binding.text.setTypeface(typeface);


        spinnerAdapter= new ArrayAdapter(this, R.layout.spinner_item, getResources().getStringArray(R.array.font_spinner));
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.fontlist.setAdapter(spinnerAdapter);
        binding.fontlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                typeface = Typeface.createFromAsset(getAssets(), "font/"+binding.fontlist.getItemAtPosition(position)+".ttf");
                binding.title.setTypeface(typeface);
                binding.text.setTypeface(typeface);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        resList = res.getStringArray(R.array.font_spinner);
        for(int i=0;i<resList.length;i++){
            if(memoEntity.getFontType().equals("font/"+resList[i]+".ttf")){
                binding.fontlist.setSelection(i);
                break;
            }
        }

        setEnableFalse();
        dateInit();
    }

    public void onDeleteClicked(View v){

        Callback callback = new Callback() {
            @Override
            public void success() {
                setResult(RESULT_OK);
                finish();
            }
            @Override
            public void failure() {
            }
        };
        Dialog dialog = new Dialog(this);
        dialog.deleteDialog(memoEntity.getId(),callback);

    }

    public void onModifyClicked(View v){
        setEnableTrue();
    }

    public void onCancelClicked(View v){
        setEnableFalse();
    }

    public void onUpdateClicked(View v){
        setEnableFalse();

        memoEntity.setDate(binding.date.getText().toString());
        memoEntity.setTag(binding.taglist.getSelectedItem().toString());
        memoEntity.setUri1(uri[0]);
        memoEntity.setUri2(uri[1]);
        memoEntity.setUri3(uri[2]);
        memoEntity.setUri4(uri[3]);
        memoEntity.setTitle(binding.title.getText().toString());
        memoEntity.setText(binding.text.getText().toString());
        memoEntity.setFontType("font/"+binding.fontlist.getSelectedItem().toString()+".ttf");

        db.MemoDao().updateMemo(memoEntity);
        Toast.makeText(getApplicationContext(),"수정이 완료됐습니다.",Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }


    public void setEnableFalse(){
        binding.taglist.setEnabled(false);
        binding.date.setEnabled(false);
        binding.title.setEnabled(false);
        binding.text.setEnabled(false);
        binding.fontlist.setEnabled(false);
        binding.deleteBtn.setVisibility(View.VISIBLE);
        binding.modifyBtn.setVisibility(View.VISIBLE);
        binding.cancelBtn.setVisibility(View.GONE);
        binding.updateBtn.setVisibility(View.GONE);
        flag=0;
    }
    public void setEnableTrue(){
        binding.taglist.setEnabled(true);
        binding.date.setEnabled(true);
        binding.title.setEnabled(true);
        binding.text.setEnabled(true);
        binding.fontlist.setEnabled(true);
        binding.deleteBtn.setVisibility(View.GONE);
        binding.modifyBtn.setVisibility(View.GONE);
        binding.cancelBtn.setVisibility(View.VISIBLE);
        binding.updateBtn.setVisibility(View.VISIBLE);
        flag=1;
    }

    public void dateInit(){
        currentTime = Calendar.getInstance().getTime();
        date_text= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTime);

    }

    public void onDateClicked(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedYear = ""+year;
                if(month<10) selectedMonth= "0"+(month+1);
                else selectedMonth= ""+(month+1);
                if(dayOfMonth<10) selectedDay= "0"+dayOfMonth;
                else selectedDay= ""+dayOfMonth;
                binding.date.setText(selectedYear+"-"+selectedMonth+"-"+selectedDay);
            }
        },Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day));

        datePickerDialog.setMessage("날짜");
        datePickerDialog.show();
    }

    public void onImageClicked(View view){
        if(flag==1) {
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
        }else{
            for(int i=0;i<4;i++){
                if(!uri[i].equals("")){
                    String key="imageView"+i;
                    DataHolder.putDataHolder(key,Uri.parse(uri[i]));

                }else{

                }
            }
            intent = new Intent(getApplicationContext(),ImageViewPage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
    }

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
                .override(500,500)
                .error(R.drawable.no_image)
                .into(imageView);
    }

}
