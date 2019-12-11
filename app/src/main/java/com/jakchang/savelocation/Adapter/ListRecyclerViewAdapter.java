package com.jakchang.savelocation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.jakchang.savelocation.MemoModel;
import com.jakchang.savelocation.R;
import com.jakchang.savelocation.ViewMemo;

import java.util.List;

public class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<MemoModel> mListItem;

    int position;
    Uri uri;
    Bitmap bitmap;
    int width,height;

    public ListRecyclerViewAdapter(Context mContext, List<MemoModel> mListItem){
        this.mContext = mContext;
        this.mListItem = mListItem;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent,false);
        final MyViewHolder vHolder = new ListRecyclerViewAdapter.MyViewHolder(v);

        width = v.findViewById(R.id.imageView).getWidth();
        height = v.findViewById(R.id.imageView).getHeight();

        vHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = vHolder.getAdapterPosition();

                Toast.makeText(mContext.getApplicationContext(), "" + mListItem.get(position).getId(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, ViewMemo.class);
                mContext.startActivity(intent);

            }
        });



        return vHolder;
    }


    @Override
    public void onBindViewHolder(ListRecyclerViewAdapter.MyViewHolder holder, int position) {
        //ListItem listItem = mListItem.get(position);

        holder.date.setText(mListItem.get(position).getDate());
        holder.tag.setText(mListItem.get(position).getTag());
        holder.address.setText(mListItem.get(position).getAddress());
        holder.imageView.setClipToOutline(true);
        bitmap = Bitmap.createScaledBitmap(mListItem.get(position).getMainImg(), 250,250,true);
        holder.imageView.setImageBitmap(bitmap);

    }


    @Override
    public int getItemCount() {
        return mListItem.size();
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