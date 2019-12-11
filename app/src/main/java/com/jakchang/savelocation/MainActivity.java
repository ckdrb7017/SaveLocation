package com.jakchang.savelocation;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jakchang.savelocation.Fragment.BlankFragment1;
import com.jakchang.savelocation.Fragment.BlankFragment2;
import com.jakchang.savelocation.Fragment.HomeFragment;
import com.jakchang.savelocation.Utils.DataHolder;
import com.jakchang.savelocation.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int MULTIPLE_PERMISSIONS = 101;



    ActivityMainBinding binding;
    BlankFragment1 blankFragment1;
    BlankFragment2 blankFragment2;
    HomeFragment homeFragment;
    double latitude, longitude;
    DataHolder dataHolder;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    final int HOME =1,MAP=2,LIST=3,RECOMM=4;
    String date_text,year,month,day;
    String selectedYear,selectedMonth,selectedDay;
    Date currentTime;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        binding.fab.setImageResource(R.drawable.home_clicked);
        binding.fab1.setImageResource(R.drawable.plusbutton);
        binding.fromDate.setPaintFlags(binding.fromDate.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        binding.toDate.setPaintFlags(binding.fromDate.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        checkPermissions();
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        } else {
            checkRunTimePermission();
        }
        flag = HOME;

        dateInit();

        changeFragment(homeFragment.getInstance());
    }

}