package com.jakchang.savelocation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakchang.savelocation.Adapter.ListRecyclerViewAdapter;
import com.jakchang.savelocation.Entity.MemoEntity;
import com.jakchang.savelocation.Interface.Callback;
import com.jakchang.savelocation.Interface.ItemClickListener;
import com.jakchang.savelocation.Repository.AppDatabase;
import com.jakchang.savelocation.Utils.Dialog;
import com.jakchang.savelocation.Utils.RecyclerDecoration;

import java.util.List;

public class GarbageActivity extends AppCompatActivity implements ItemClickListener {

    private RecyclerView mRecyclerView;
    private ListRecyclerViewAdapter recyclerViewAdapter;
    private List<MemoEntity> listItems;
    AppDatabase db;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garbage);

        context=this;
        db= AppDatabase.getInstance(this);
        listItems=  db.MemoDao().selectDeleted();

        mRecyclerView = findViewById(R.id.garbage);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new ListRecyclerViewAdapter(this,listItems,this);
        mRecyclerView.setAdapter(recyclerViewAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(mRecyclerView.getContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        RecyclerDecoration spaceDecoration = new RecyclerDecoration(20);
        mRecyclerView.addItemDecoration(spaceDecoration);

    }

    @Override
    public void onItemClick(MemoEntity memoEntity) {
        Intent intent = new Intent(this, ViewMemo.class);
        intent.putExtra("id",memoEntity.getId());
        intent.putExtra("delete",3333);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(MemoEntity memoEntity) {
        Callback callback = new Callback() {
            @Override
            public void success() {
                listItems=  db.MemoDao().selectDeleted();
                recyclerViewAdapter = new ListRecyclerViewAdapter(context,listItems,(ItemClickListener)context);
                mRecyclerView.setAdapter(recyclerViewAdapter);
            }
            @Override
            public void failure() {

            }
        };
        Dialog dialog = new Dialog(this);
        dialog.updateDialog(memoEntity.getId(),callback);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

}
