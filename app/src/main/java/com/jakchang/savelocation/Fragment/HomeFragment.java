package com.jakchang.savelocation.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.jakchang.savelocation.MainActivity;
import com.jakchang.savelocation.R;
import com.jakchang.savelocation.databinding.ActivityMainBinding;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HomeFragment extends Fragment {


    View mView;
    EditText guideText;
    Button editBtn;
    int flag=1;
    String path ;
    String guide ="나만의 노트 작성법! \n" +
            "지도를 클릭하면 내가 기록한 메모들을 지도로 볼수있고 내가 기록하고 싶은 위치에 메모할수 있다.\n" +
            "\n" +
            "목록을 클릭하면 내가 기록한 메모들을 리스트로 볼수 있다. 리스트를 클릭하면 세부적인 내용이 나온다.\n" +
            "\n" +
            "추천을 클릭하면 다른 사람들이 기록한 장소를 최다순으로 볼수 있다.";

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
        editBtn = (Button) mView.findViewById(R.id.editBtn);
        guideText.setEnabled(false);


        File file = new File(mView.getContext().getFilesDir().getPath() + "/memo");
        path = mView.getContext().getFilesDir().getPath()+"/memo/memo.txt";
        file.mkdir();
        String getText = readFile(path);

        if(getText.equals("")){
            guideText.setText(guide);
            writeFile(path);
        }else{
            guideText.setText(getText);
            Log.d("TAG",getText);

        }



        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0){
                    editBtn.setBackgroundColor(0xffffbb33);
                    editBtn.setText("편집 완료");
                    guideText.setEnabled(true);
                    flag=1;
                }else{
                    editBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.buttonborder));
                    editBtn.setText("편집");
                    guideText.setEnabled(false);
                    writeFile(path);
                    flag=0;

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

}
