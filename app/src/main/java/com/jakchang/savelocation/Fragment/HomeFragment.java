package com.jakchang.savelocation.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

import com.jakchang.savelocation.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HomeFragment extends Fragment {


    View mView;
    EditText guideText;
    Switch aSwitch;
    int flag=1;
    String path ;
    String guide ="";

    public HomeFragment(){}
    public static HomeFragment getInstance(){
        return LazyHolder.INSTANCE;
    }
    private static class  LazyHolder{
        private static final HomeFragment INSTANCE = new HomeFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mView==null) {
            mView = inflater.inflate(R.layout.activity_homefragment, container, false);
        }
        guideText = (EditText) mView.findViewById(R.id.guideText);
        aSwitch= (Switch) mView.findViewById(R.id.switch1);
        guideText.setEnabled(false);


        File file = new File(mView.getContext().getFilesDir().getPath() + "/memo");
        path = mView.getContext().getFilesDir().getPath()+"/memo/memo.txt";
        file.mkdir();
        String getText = readFile(path);

        if(!getText.equals("\n")){
            guideText.setText(getText);
            //writeFile(path);
        }

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    guideText.setEnabled(true);
                    flag=0;
                }else{
                    guideText.setEnabled(false);
                    writeFile(path);
                    flag=1;
                }
            }
        });


        return mView;
    }

    String readFile(String path) {
        String readStr = "";
        String str;

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((str = br.readLine()) != null) {
                readStr += str;
                readStr += "\n";
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readStr;
    }

    void writeFile(String path) {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, false));
            bw.write(guideText.getText().toString());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG","onDestroy Home");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("TAG","onDetach Home");
    }
}
