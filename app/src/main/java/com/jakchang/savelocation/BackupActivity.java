package com.jakchang.savelocation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BackupActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);
        textView = (TextView)findViewById(R.id.backupText);
        String str = "백업하기\n"+
                "1.설정 열기\n" +
                "2.계정 및 백업 선택\n"+
                "3.백업 및 복원 선택\n"+
                "4.데이터 백업 클릭\n"+
                "5.구글계정 클릭 후 백업\n\n"+
                "복원하기\n"+
                "1.자동복원 클릭\n"+
                "2.플레이스토어에서 앱 재설치";
        textView.setText(str);
    }


    //확인 버튼 클릭
    public void mOnClose(View v){
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG","11111");
    }
}
