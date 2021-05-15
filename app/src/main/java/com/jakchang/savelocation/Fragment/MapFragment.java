package com.jakchang.savelocation.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jakchang.savelocation.Adapter.CustomInfoWindowAdapter;
import com.jakchang.savelocation.Entity.MemoEntity;
import com.jakchang.savelocation.R;
import com.jakchang.savelocation.Repository.AppDatabase;
import com.jakchang.savelocation.Utils.DataHolder;
import com.jakchang.savelocation.Utils.GpsTracker;
import com.jakchang.savelocation.ViewMemo;
import com.jakchang.savelocation.WritingMemo;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    View mView;
    MapView mMapView;
    GoogleMap mGoogleMap;
    double lat=0, lng=0;
    Context mContext;
    MarkerOptions markerOptions;
    Marker marker;
    List<MemoEntity> listItems;
    DataHolder dataHolder;
    AppDatabase db;
    String tag,fromDate,toDate;
    String markerLat,markerLng,markerTitle;
    int markerId;
    private static final int WRITING_RESULT_CODE=3001;

    public MapFragment(){}
    public MapFragment(Context context){this.mContext=context;}
    public static MapFragment getInstance(){
        return Fragment1Holder.INSTANCE;
    }


    private static class  Fragment1Holder{
        private static final MapFragment INSTANCE = new MapFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fromDate = (String)dataHolder.popDataHolder("fromDate");
        toDate = (String)dataHolder.popDataHolder("toDate");
        tag = (String)dataHolder.popDataHolder("tag");

        /*
        memoViewModel = ViewModelProviders.of(this).get(MemoViewModel.class);
        memoViewModel.init(mContext,fromDate,toDate);
        memoViewModel.memoList().observe(this, new Observer<List<MemoEntity>>() {
            @Override
            public void onChanged(List<MemoEntity> memoEntities) {
                listItems = memoViewModel.memoList().getValue();

            }
        });*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_fragment01, container, false);

        return mView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.map);
        if(mMapView!=null) {
            mMapView.onCreate(savedInstanceState);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        db= AppDatabase.getInstance(mContext);

        listItems=db.MemoDao().selectAll(fromDate,toDate);
        mGoogleMap = googleMap;
        GpsTracker gps = new GpsTracker(getContext());
        lat = gps.getLatitude();
        lng= gps.getLongitude();

        for(MemoEntity entity:listItems){
            Double latitude = Double.parseDouble(entity.getLatitude());
            Double longitude = Double.parseDouble(entity.getLongitude());
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude,longitude))
                    .title(entity.getDate())
                    .snippet(entity.getTitle())
                    .icon(getMarkerIcon("#F4B183")));
        }

        LatLng myLocation = new LatLng(lat,lng);
        dataHolder.putDataHolder("lat", lat+"");
        dataHolder.putDataHolder("lng",lng+"");

        markerOptions = new MarkerOptions();
        markerOptions.position(myLocation)
                .title("내 위치")
                .icon(getMarkerIcon("#3993FF"));

        marker= mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,15));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(17));

        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);

        mGoogleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(this.getContext()));
        mGoogleMap.setMyLocationEnabled(true);


        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if(marker.getPosition().latitude!=lat&&marker.getPosition().longitude!=lng) {
                    Intent intent = new Intent(getContext(), ViewMemo.class);

                    markerLat = marker.getPosition().latitude + "";
                    markerLng = marker.getPosition().longitude + "";
                    markerTitle = marker.getTitle();


                   for (MemoEntity item : listItems) {
                       Log.d("TAG",markerLat+","+item.getLatitude());
                       Log.d("TAG",markerLng+","+item.getLongitude());

                       if (item.getLatitude().equals(markerLat) && item.getLongitude().equals(markerLng)) {
                           markerId = item.getId();
                           Log.d("TAG",""+markerId);
                           break;
                       }
                    }

                    intent.putExtra("id", markerId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent,3333);

                }
            }
        });

        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                marker.remove();
                markerOptions.title("내 위치");
                Double latitude = point.latitude; // 위도
                Double longitude = point.longitude; // 경도
                lat = point.latitude;
                lng = point.longitude;

                //markerOptions.snippet(getCurrentAddress(latitude,longitude));
                markerOptions.position(new LatLng(latitude, longitude));
                marker =  mGoogleMap.addMarker(markerOptions);

            }
        });

    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();

    }
    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void change(String mTag,String from,String to){
        mGoogleMap.clear();
        tag=mTag;
        fromDate = from;
        toDate=to;
        if(mTag.equals("전체")) {
            listItems = db.MemoDao().selectAll(fromDate, toDate);
        }else{
            listItems = db.MemoDao().selectAllByTag(tag,fromDate, toDate);
        }

        Log.d("Tag",""+listItems.size());
        for(MemoEntity entity:listItems){
            markerOptions.title("위치");

            Double latitude = Double.parseDouble(entity.getLatitude());
            Double longitude = Double.parseDouble(entity.getLongitude());
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude,longitude))
                    .title(entity.getDate())
                    .snippet(entity.getTitle())
                    .icon(getMarkerIcon("#F4B183")));

        }

    }

    public void insert(){
        dataHolder.putDataHolder("lat", lat+"");
        dataHolder.putDataHolder("lng",lng+"");
        Intent intent = new Intent(mContext, WritingMemo.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent,3001);
    }


    public String getCurrentAddress( double latitude, double longitude) {
        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(getContext(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(getContext(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(getContext(), "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }

    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case WRITING_RESULT_CODE:
                change(tag,fromDate,toDate);
                break;
        }


        switch (resultCode) {
            case RESULT_OK:
                change(tag,fromDate,toDate);
                break;
        }
    }
}
