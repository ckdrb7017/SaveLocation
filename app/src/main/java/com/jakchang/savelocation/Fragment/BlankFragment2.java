package com.jakchang.savelocation.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakchang.savelocation.Adapter.ListRecyclerViewAdapter;
import com.jakchang.savelocation.MemoModel;
import com.jakchang.savelocation.R;
import com.jakchang.savelocation.Utils.RecyclerDecoration;

import java.io.InputStream;
import java.util.ArrayList;

public class BlankFragment2 extends Fragment {

    View v;
    private RecyclerView mRecyclerView;
    private ListRecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<MemoModel> listItems;
    Context mContext;
    Bitmap bitmap;
    private ArrayList<MemoModel> mArrayList;

    public BlankFragment2(){}

    public static BlankFragment2 getInstance(){
        return BlankFragment2.Fragment2Holder.INSTANCE;
    }

    private static class  Fragment2Holder{
        private static final BlankFragment2 INSTANCE = new BlankFragment2();
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listItems =new ArrayList<MemoModel>();
        for(int i=0;i<10;i++) {

            try{
                InputStream is = mContext.getContentResolver().openInputStream(Uri.parse("content://media/external/images/media/1163"));
                bitmap = BitmapFactory.decodeStream(is);
                //bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(),height,true);
            }catch(Exception e){
                e.printStackTrace();
            }

            listItems.add(new MemoModel(i,"37.5520829","127.0793734",bitmap," 주소 : ","위치","여행","2019-11-27",null,null,null,null,null));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment02,container,false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapter = new ListRecyclerViewAdapter(getContext(),listItems);
        mRecyclerView.setAdapter(recyclerViewAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(mRecyclerView.getContext(),new LinearLayoutManager(mContext).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        RecyclerDecoration spaceDecoration = new RecyclerDecoration(20);
        mRecyclerView.addItemDecoration(spaceDecoration);


        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
    public void search(){
        listItems.clear();

        listItems.add(new MemoModel(0,"37.5520829","127.0793734",bitmap," 주소 : ","위치","여행","2019-11-27",null,null,null,null,null));
        listItems.add(new MemoModel(0,"37.5520829","127.0793734",bitmap," 주소 : ","위치","여행","2019-11-27",null,null,null,null,null));
        listItems.add(new MemoModel(0,"37.5520829","127.0793734",bitmap," 주소 : ","위치","여행","2019-11-27",null,null,null,null,null));
        recyclerViewAdapter.notifyDataSetChanged();

    }

}
