package com.example.moment.activity.gmap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.example.moment.R;
import com.example.moment.model.Board;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.URI;
import java.net.URL;

public class DetailGoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter {

    private GoogleMap mMap;
    Board dto;
//    MarkerOptions marker;
    RequestManager requestManager;
    Marker marker;
    boolean isShowWindow;
    boolean markerClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Intent intent = getIntent();
        dto = intent.getParcelableExtra("dto");

        Log.i("intent dto ===>",dto.toString());
        final LatLng current = new LatLng(dto.getB_latitude(), dto.getB_longitude());
//        marker = new MarkerOptions().position(current).title(dto.getB_local());

//        mMap.addMarker(marker);
        marker = mMap.addMarker(new MarkerOptions().position(current).title("마커를 누르세요"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(current, 15);
        mMap.moveCamera(cameraUpdate);
        mMap.setInfoWindowAdapter(this);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker mar) {
                    marker.showInfoWindow();
                    isShowWindow = marker.isInfoWindowShown();
                return false;
            }
        });
    }


    @Override
    public View getInfoWindow(Marker mar) {
        View v = null;
        try {
            // Getting view from the layout file info_window_layout
            v = getLayoutInflater().inflate(R.layout.google_map_image_marker, null);
            // Getting reference to the TextView to set latitude
            final ImageView imageView = v.findViewById(R.id.detailMapImageView);
            Glide
                    .with(v)
                    .load(dto.getB_imgpath())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (isShowWindow) {
                                isShowWindow = false;
                                marker.showInfoWindow();
                                return true;
                            }
                            isShowWindow = true;
                            return false;
                        }
                    })
                    .error(R.drawable.camera_icon)
                    .into(imageView);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return v;
    }

    @Override
    public View getInfoContents(Marker mar) {
        return null;
    }
}
