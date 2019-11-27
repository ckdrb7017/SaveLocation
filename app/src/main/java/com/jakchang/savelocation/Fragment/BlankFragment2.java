package com.jakchang.savelocation.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BlankFragment2 extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getContext(),"Blank 2",Toast.LENGTH_LONG).show();

    }
}
