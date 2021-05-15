package com.jakchang.savelocation.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakchang.savelocation.Adapter.ListRecyclerViewAdapter;
import com.jakchang.savelocation.Entity.MemoEntity;
import com.jakchang.savelocation.Interface.Callback;
import com.jakchang.savelocation.Interface.ItemClickListener;
import com.jakchang.savelocation.R;
import com.jakchang.savelocation.Repository.AppDatabase;
import com.jakchang.savelocation.Utils.DataHolder;
import com.jakchang.savelocation.Utils.Dialog;
import com.jakchang.savelocation.Utils.RecyclerDecoration;
import com.jakchang.savelocation.ViewMemo;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ListFragment extends Fragment implements ItemClickListener {


    private RecyclerView mRecyclerView;
    private ListRecyclerViewAdapter recyclerViewAdapter;
    private List<MemoEntity> listItems;
    static ListFragment listFragment;
    Context mContext;
    DataHolder dataHolder;
    String tag,fromDate,toDate;
    AppDatabase db;
    public ListFragment(){}
    public ListFragment(Context context){this.mContext=context;}

    public static ListFragment getInstance(Context context){
        if(listFragment ==null) listFragment = new ListFragment(context);
        return listFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("TAG","onCreateView Fragment2");
        listItems =new ArrayList<MemoEntity>();
        tag ="전체";
        fromDate = (String)dataHolder.popDataHolder("fromDate");
        toDate = (String)dataHolder.popDataHolder("toDate");
        db= AppDatabase.getInstance(mContext);
        listItems=db.MemoDao().selectAll(fromDate,toDate);

        View view = inflater.inflate(R.layout.activity_fragment02,container,false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapter = new ListRecyclerViewAdapter(getContext(),listItems,this);
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
        Log.d("TAG","onViewCreated Fragment2");

    }
    public void search(String tTag, String tFromDate, String tToDate){
        tag=tTag.equals("")?tag:tTag;
        fromDate = tFromDate.equals("")?fromDate:tFromDate;
        toDate =tToDate.equals("")?toDate:tToDate;
        if(tag.equals("전체")) {
            listItems = db.MemoDao().selectAll(fromDate, toDate);
        }else{
            listItems = db.MemoDao().selectAllByTag(tag,fromDate, toDate);
        }
        recyclerViewAdapter = new ListRecyclerViewAdapter(getContext(),listItems,this);
        mRecyclerView.setAdapter(recyclerViewAdapter);

        //recyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(MemoEntity memoEntity) {
        //Toast.makeText(getApplicationContext(), "" + mListItem.get(position).getId(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getContext(), ViewMemo.class);
        intent.putExtra("id",memoEntity.getId());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent,2222);
    }

    @Override
    public void onItemLongClick(MemoEntity memoEntity) {
        Callback callback = new Callback() {
            @Override
            public void success() {
                search(tag,fromDate,toDate);
            }
            @Override
            public void failure() {

            }
        };
        Dialog dialog = new Dialog(getContext());
        dialog.deleteDialog(memoEntity.getId(),callback);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            search(tag,fromDate,toDate);
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity) mContext = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG","onDestroy Fragment2");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("TAG","onDetach Fragment2");
    }
}
