package com.jakchang.savelocation.Fragment;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jakchang.savelocation.Adapter.CustomInfoWindowAdapter;
import com.jakchang.savelocation.MemoModel;
import com.jakchang.savelocation.R;
import com.jakchang.savelocation.Utils.GpsTracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BlankFragment1 extends Fragment implements OnMapReadyCallback {
    View mView;
    MapView mMapView;
    GoogleMap mGoogleMap;
    double lat=0, lng=0;
    Context mContext;
    MarkerOptions markerOptions;
    Marker marker;
    ArrayList<MemoModel> items;
    public BlankFragment1(){}
    public BlankFragment1(Context context){this.mContext=context;}
    public static BlankFragment1 getInstance(){
        return Fragment1Holder.INSTANCE;
    }


    private static class  Fragment1Holder{
        private static final BlankFragment1 INSTANCE = new BlankFragment1();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



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

        mGoogleMap = googleMap;
        GpsTracker gps = new GpsTracker(getContext());
        lat = gps.getLatitude();
        lng= gps.getLongitude();
        /*
        for(MemoModel item : items) {
            LatLng location = new LatLng(Double.parseDouble(item.getLatitude()),Double.parseDouble(item.getLongitude()));
            //mGoogleMap.addMarker(new MarkerOptions().position(location).title(item.getName()).snippet("종류 : "+item.getKind()+"\n"+"거리 :"+item.getDistance()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }*/


        LatLng myLocation = new LatLng(lat, lng);
        markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(lat, lng))
                .title("내 위치");

        marker= mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,15));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(17));

        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);

        mGoogleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(this.getContext()));
        mGoogleMap.setMyLocationEnabled(true);

 /*
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if(marker.getPosition().latitude!=mylat&&marker.getPosition().longitude!=mylng) {
                  //  Intent intent = new Intent(getContext(), SeeSellerInfo.class);
                    markerLat = marker.getPosition().latitude + "";
                    markerLng = marker.getPosition().longitude + "";
                    markerTitle = marker.getTitle();
                    markerUserId = "";

                   //for (ListItem item : listItems) {
                    //    if (item.getLat().equals(markerLat) && item.getLng().equals(markerLng) && item.getName().equals(markerTitle)) {
                    //        markerUserId = item.getUser_id();
                    //    }
                   // }

                   // intent.putExtra("user_id", markerUserId);
                  //  startActivity(intent);

                }
            }
        });*/

        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                marker.remove();
                markerOptions.title("위치");
                Double latitude = point.latitude; // 위도
                Double longitude = point.longitude; // 경도
                // 마커의 스니펫(간단한 텍스트) 설정
                markerOptions.snippet(getCurrentAddress(latitude,longitude));
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

    public void change(){
        mGoogleMap.clear();
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

}
