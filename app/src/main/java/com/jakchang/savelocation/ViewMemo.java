package com.jakchang.savelocation;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.jakchang.savelocation.databinding.ActivityViewmemoBinding;


public class ViewMemo extends AppCompatActivity {
    ActivityViewmemoBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viewmemo);
        binding.setActivity(this);

        String[] withList = getResources().getStringArray(R.array.tag_spinner);
        int spinnerIndex = 0;
        for(int i =0; i < withList.length;i++) {
            if("".equals(withList[i]))
                spinnerIndex = i;
        }
        //binding.withlist.setSelection(spinnerIndex);

    }

}
