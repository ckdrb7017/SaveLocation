package com.jakchang.savelocation.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jakchang.savelocation.Entity.MemoEntity;
import com.jakchang.savelocation.Interface.ItemClickListener;
import com.jakchang.savelocation.R;

import java.util.List;

public class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<MemoEntity> mListItem;
    ItemClickListener itemClickListener;
    int position;
    int width,height;

    public ListRecyclerViewAdapter(Context mContext, List<MemoEntity> mListItem, ItemClickListener itemClickListener){
        this.mContext = mContext;
        this.mListItem = mListItem;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent,false);
        final MyViewHolder vHolder = new MyViewHolder(v);


        vHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                position = vHolder.getAdapterPosition();
                itemClickListener.onItemClick(mListItem.get(position));

            }
        });

        vHolder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                position = vHolder.getAdapterPosition();
                itemClickListener.onItemLongClick(mListItem.get(position));

                return true;
            }
        });



        return vHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //ListItem listItem = mListItem.get(position);

        holder.date.setText(mListItem.get(position).getDate());
        holder.tag.setText(mListItem.get(position).getTag());
        holder.title.setText(mListItem.get(position).getTitle());
        holder.imageView.setClipToOutline(true);
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), mListItem.get(position).getFontType());
        holder.title.setTypeface(typeface);

        //bitmap = Bitmap.createScaledBitmap(mListItem.get(position).getMainImg(), 250,250,true);
        Glide.with(mContext)
                .load(mListItem.get(position).getUri1())
                .centerCrop()
                .error(R.drawable.no_image)
                .into(holder.imageView);


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
        private TextView title;



        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.imageView);
            date = (TextView)view.findViewById(R.id.date);
            tag = (TextView)view.findViewById(R.id.tag);
            title = (TextView)view.findViewById(R.id.title);

        }


    }//class MyViewHolder


}//class RecyclerViewAdapte