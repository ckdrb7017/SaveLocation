package com.jakchang.savelocation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jakchang.savelocation.MemoModel;
import com.jakchang.savelocation.R;
import com.jakchang.savelocation.ViewMemo;

import java.util.List;

public class RecommandRecyclerViewAdapter extends RecyclerView.Adapter<RecommandRecyclerViewAdapter.MyViewHolder> {

    Context mContext;
   // List<MemoModel> mListItem;
    List<MemoModel> mListItem;

    int position;
    Uri uri;

    public RecommandRecyclerViewAdapter(Context mContext, List<MemoModel> mListItem){
        this.mContext = mContext;
        this.mListItem = mListItem;
    }

    @Override
    public RecommandRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent,false);
        final RecommandRecyclerViewAdapter.MyViewHolder vHolder = new RecommandRecyclerViewAdapter.MyViewHolder(v);

        vHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = vHolder.getAdapterPosition();
                Intent intent = new Intent(mContext, ViewMemo.class);



                mContext.startActivity(intent);


            }
        });



        return vHolder;
    }


    @Override
    public void onBindViewHolder(RecommandRecyclerViewAdapter.MyViewHolder holder, int position) {
        //ListItem listItem = mListItem.get(position);

        holder.date.setText(mListItem.get(position).getDate());
        holder.tag.setText(mListItem.get(position).getTag());
        holder.address.setText(mListItem.get(position).getAddress());

        uri = Uri.parse(mListItem.get(position).getUri1());
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            holder.imageView.setImageBitmap(bitmap);

        }catch(Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public int getPosition(){
        return position;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView date;
        private TextView tag;
        private TextView address;



        public MyViewHolder(View view) {
            super(view);


            imageView = (ImageView)view.findViewById(R.id.imageView);
            date = (TextView)view.findViewById(R.id.date);
            tag = (TextView)view.findViewById(R.id.tag);
            address = (TextView)view.findViewById(R.id.address);

        }


    }//class MyViewHolder


}//class RecyclerViewAdapte