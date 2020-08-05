package com.example.moment.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.moment.R;
import com.example.moment.activity.board.DetailActivity;
import com.example.moment.common.Common;
import com.example.moment.model.Board;
import com.example.moment.trancefer.AsyncMarkerLoad;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearListFragment extends Fragment implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.InfoWindowAdapter {

    GoogleMap mMap = null;
    Marker marker;

    private Marker currentMarker = null;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Location location;

    Location mCurrentLocatiion;
    LatLng currentPosition;
    LatLng currentLatLng;

    ArrayList<Board> array;
    boolean isShowWindow = false;
    public NearListFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_board_near_list, container, false);
        final View mapFragmentView = rootView.findViewById(R.id.nearListMap);
        mapFragmentView.setVisibility(View.INVISIBLE);

        Button button = rootView.findViewById(R.id.nearList_MapViewBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.nearListMap);
                mapFragmentView.setVisibility(View.VISIBLE);
                // 맵이 사용할 준비가 되면 onMapReady 함수를 자동으로 호출된다
                mapFragment.getMapAsync(NearListFragment.this);
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final LatLng current = new LatLng(20, 20);
//        marker = new MarkerOptions().position(current).title(dto.getB_local());
//        mMap.addMarker(marker);
        marker = mMap.addMarker(new MarkerOptions().position(current).title("내위치 보기"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(current, 20);
        mMap.moveCamera(cameraUpdate);
        mMap.setMyLocationEnabled(true);
        mFusedLocationClient.requestLocationUpdates(locationRequest, getLocationCallback(), Looper.myLooper());

        mMap.setInfoWindowAdapter(this);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker mar) {
                mar.showInfoWindow();
                isShowWindow = mar.isInfoWindowShown();

                return false;
            }
        });
        //이미지 클릭시 디테일 창으로 이동
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                JSONObject jsonObject = (JSONObject)marker.getTag();
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                try {
                    Log.i("marker------b_no",jsonObject.getString("b_no"));
                    intent.putExtra("b_no",jsonObject.getInt("b_no"));
                    intent.putExtra("b_pano", "N");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivity(intent);
            }
        });


    }

    public LocationCallback getLocationCallback(){
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                List<Location> locationList = locationResult.getLocations();

                if (locationList.size() > 0) {
                    location = locationList.get(locationList.size() - 1);
                    //location = locationList.get(0);
                    currentPosition
                            = new LatLng(location.getLatitude(), location.getLongitude());

                    String markerTitle = getCurrentAddress(currentPosition);
                    String markerSnippet = "위도:" + String.valueOf(location.getLatitude())
                            + " 경도:" + String.valueOf(location.getLongitude());

                    //현재 위치에 마커 생성하고 이동
                    setCurrentLocation(location, markerTitle, markerSnippet);

                    mCurrentLocatiion = location;
                    //내주변 마커 생성
                    getMarkerNearby(mMap);
                }
            }
        };
        return locationCallback;
    }


    public String getCurrentAddress(LatLng latlng) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(getContext(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return null;
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(getContext(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return null;
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(getContext(), "주소 미발견", Toast.LENGTH_LONG).show();
            return null;

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0);
        }

    }

    //현재 위치 마커찍기
    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {

        if (currentMarker != null) currentMarker.remove();

        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLatLng, 15);

        mMap.moveCamera(cameraUpdate);

    }

    //내주변 마커 가져와 찍기기
    public void getMarkerNearby(GoogleMap googleMap){
        String url = Common.SERVER_URL+"/getMarkerNearbyAction.mo";
        //현재위치의 좌표 String 으로 변환
        String currentLatitude = String.valueOf(location.getLatitude());
        String currentLongitude = String.valueOf(location.getLongitude());
        //주위의 좌표및 자료 요청
       try{
           array = new AsyncMarkerLoad(getActivity()).execute(url,currentLatitude,currentLongitude).get();
       }catch (Exception e){
           e.printStackTrace();
       }
        JSONObject jsonObject;
        for(int i = 0; i < array.size(); i++){
            jsonObject = new JSONObject();
            try {
                jsonObject.put("b_imgpath",array.get(i).getB_imgpath());
                jsonObject.put("b_no",array.get(i).getB_no());
            }catch (Exception e){
                e.printStackTrace();
            }
            googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(array.get(i).getB_latitude(),array.get(i).getB_longitude())))
//                        .setTitle(String.valueOf(array.get(i).getB_no()))
                        .setTag(jsonObject);

        }
   }

    @Override
    public View getInfoWindow(final Marker mar) {
        View v = null;
        ImageView mapImageView = null;
        JSONObject jsonObject = null;

        try {
            // Getting view from the layout file info_window_layout
            v = getLayoutInflater().inflate(R.layout.google_map_image_marker, null);
            // Getting reference to the TextView to set latitude
            mapImageView = v.findViewById(R.id.detailMapImageView);

            jsonObject = (JSONObject)mar.getTag();

            Glide
                    .with(v)
                    .load(jsonObject.getString("b_imgpath"))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (isShowWindow) {
                                isShowWindow = false;
                                mar.showInfoWindow();
                                return true;
                            }
                            isShowWindow = true;
                            return false;
                        }
                    })
                    .error(R.drawable.camera_icon)
                    .into(mapImageView);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }




}





