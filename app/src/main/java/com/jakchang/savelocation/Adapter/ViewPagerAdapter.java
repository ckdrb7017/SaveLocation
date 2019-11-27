package com.jakchang.savelocation.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.jakchang.savelocation.R;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    ArrayList<Bitmap> bitmaps;
    private LayoutInflater inflater;
    private Context context;
    private int count;


    public ViewPagerAdapter(Context context, ArrayList<Bitmap> bitmaps, int count){
        this.context = context;
        this.bitmaps = bitmaps;
        this.count = count;
    }


    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //count =bitmaps.size();
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.imageslide,container,false);
        ImageView imageView = (ImageView)v.findViewById(R.id.slideImg);
        imageView.setImageBitmap(bitmaps.get((position+count)%4));
        Log.d("TAG","position : "+position +"count : "+count);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}
