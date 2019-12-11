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

    public void dateInit(){
        currentTime = Calendar.getInstance().getTime();
        date_text= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTime);

        year = date_text.split("-")[0];
        month = date_text.split("-")[1];
        day = date_text.split("-")[2];
        binding.fromDate.setText(year+"-"+month+"-01");
        binding.toDate.setText(date_text);
        Log.d("TAG",year+" : "+month+" : "+day);
    }


    public void onFromDateClicked(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedYear = ""+year;
                selectedMonth = ""+(month+1);
                if(dayOfMonth<10) selectedDay= "0"+dayOfMonth;
                else selectedDay= ""+dayOfMonth;
                binding.fromDate.setText(selectedYear+"-"+selectedMonth+"-"+selectedDay);
            }
        },Integer.parseInt(year), Integer.parseInt(month)-1, 1);

        datePickerDialog.setMessage("메시지");
        datePickerDialog.show();

    }

    public void onToDateClicked(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedYear = ""+year;
                selectedMonth = ""+(month+1);
                if(dayOfMonth<10) selectedDay= "0"+dayOfMonth;
                else selectedDay= ""+dayOfMonth;
                binding.toDate.setText(selectedYear+"-"+selectedMonth+"-"+selectedDay);
            }
        },Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day));

        datePickerDialog.setMessage("메시지");
        datePickerDialog.show();
    }

    public void anim() {
        if (isFabOpen) {
            binding.fab1.startAnimation(fab_close);
            //binding.fab2.startAnimation(fab_close);
            binding.fab1.setClickable(false);
            //binding.fab2.setClickable(false);
            isFabOpen = false;
        } else {
            binding.fab1.startAnimation(fab_open);
            //binding.fab2.startAnimation(fab_open);
            binding.fab1.setClickable(true);
            //binding.fab2.setClickable(true);
            isFabOpen = true;
        }
    }


    public void onFabClicked(View view){
        binding.fab.setImageResource(R.drawable.home_clicked);

        changeFragment(homeFragment.getInstance());
        binding.mapBtn.setSelected(false);
        binding.listBtn.setSelected(false);
        binding.recommBtn.setSelected(false);
        closeFab();
        flag=HOME;
    }

    public void onFab1Clicked(View view){
        try{

            Intent intent = new Intent(this,WritingMemo.class);
            intent.putExtra("key","insert");
            startActivity(intent);

        }catch (Exception e){

        }
        //closeFab();
    }

    public void openFab(){
        if (isFabOpen) {
            binding.fab1.startAnimation(fab_close);
            //binding.fab2.startAnimation(fab_close);
            binding.fab1.setClickable(false);
            //binding.fab2.setClickable(false);
            isFabOpen = false;
        }
    }

    public void closeFab(){
        if(isFabOpen){
            binding.fab1.startAnimation(fab_close);
            //binding.fab2.startAnimation(fab_close);
            binding.fab1.setClickable(false);
            //binding.fab2.setClickable(false);
            isFabOpen = false;
        }
    }

    public void changeFragment(Fragment fragment) {
        //FragmentTransaction의 API를 사용하면 Fragment의 추가, 변경 ,제거 작업 가능
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //위에서 가져온 Transaction을 이용해 밑에 3가지 기능 가능
        //add() : Fragment 추가
        //remove() : Fragment 제거
        //replace() : Fragment 변경
        fragmentTransaction.replace(R.id.fragment_container, fragment);

        //Transaction 작업 후 마지막에 commit()를 호출 후 적용
        fragmentTransaction.commit();
    }

    public void onBlank1Clicked(View view){
            binding.fab.setImageResource(R.drawable.home_unclicked);

            dataHolder.getInstance().putDataHolder("lat", latitude);
            dataHolder.getInstance().putDataHolder("lng", longitude);
            changeFragment(new BlankFragment1(this));
            binding.mapBtn.setSelected(true);
            binding.listBtn.setSelected(false);
            binding.recommBtn.setSelected(false);
            openFab();
            anim();
            flag = MAP;

    }

    public void onBlank2Clicked(View view){
        binding.fab.setImageResource(R.drawable.home_unclicked);
        blankFragment2.getInstance().setmContext(this);
        changeFragment(blankFragment2.getInstance());
        binding.mapBtn.setSelected(false);
        binding.listBtn.setSelected(true);
        binding.recommBtn.setSelected(false);
        closeFab();
        flag = LIST;

    }


    public void onSearchClicked(View view){
        if(flag==HOME){

        }
        else if(flag==MAP){

        }
        else if(flag==LIST){
            //BlankFragment2 fragment = (BlankFragment2) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            blankFragment2.getInstance().search();
        }
        else if(flag==RECOMM){

        }
        //tf.testFunction();


    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;
            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if ( check_result ) {
                //위치 값을 가져올 수 있음;

            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){
        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            // 3.  위치 값을 가져올 수 있음
        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);

            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }


    }

    private boolean checkPermissions() {
        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(pm);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }






    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 권한을 설정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();

                        return;
                    }
                }
                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}