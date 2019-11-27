package com.jakchang.savelocation.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jakchang.savelocation.CustomInfoWindowAdapter;
import com.jakchang.savelocation.DataHolder;
import com.jakchang.savelocation.GpsTracker;
import com.jakchang.savelocation.R;

public class BlankFragment1 extends Fragment implements OnMapReadyCallback {
    View mView;
    MapView mMapView;
    GoogleMap mGoogleMap;
    double lat=0, lng=0;
    DataHolder dataHolder;
    public BlankFragment1(){}
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


        LatLng myLocation = new LatLng(lat, lng);
        mGoogleMap.addMarker(new MarkerOptions().position(myLocation).title("MyLocation"));
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


}
