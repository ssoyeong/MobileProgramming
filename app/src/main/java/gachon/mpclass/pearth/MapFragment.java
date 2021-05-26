package gachon.mpclass.pearth;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    View rootView;
    MapView mapView;
    String Case = "";

    public MapFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        Case = ((MapActivity)getActivity()).Case;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) rootView.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(this.getActivity());



        CameraUpdate cameraUpdate = null;
        if(Case.equals("GPS")){

            GpsTracker gpsTracker = new GpsTracker(getContext());
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14);
        }
        else if(Case.equals("List")) {

            cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(37.502431, 127.111466), 14);
        }
        googleMap.animateCamera(cameraUpdate);


        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.45816, 127.126547))
                .title("에티컬테이블").snippet("채식 전문 음식점"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.464159, 127.140789))
                .title("뜰안채").snippet("채식 뷔페 음식점"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.49344, 127.12277))
                .title("걸구쟁이네").snippet("한식당"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.50872, 127.08157))
                .title("스윗솔").snippet("비건 채식 레스토랑"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.50129, 127.10353))
                .title("블렌드랩").snippet("카페"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.50817, 127.1069))
                .title("씨젬므주르").snippet("비건 프렌치 레스토랑"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.51308, 127.09642))
                .title("제로비건").snippet("채식 전문식당"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.49582, 127.12215))
                .title("해피비건").snippet("화장품 산업"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.52199, 127.04464))
                .title("닥터비건").snippet("비건 채식 레스토랑"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.51508, 127.04876))
                .title("비건이삼").snippet("비건 베이커리"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.48567, 127.12386))
                .title("채식코스요리전문점&굴").snippet("한식당"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.4769, 127.04938))
                .title("러빙헛카페").snippet("비건 채식 레스토랑"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.47698, 127.04734))
                .title("베지 그린").snippet("비건 채식 레스토랑"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.51845, 127.038))
                .title("스타일비건").snippet("음식점"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.55355, 127.13361))
                .title("평상시").snippet("카페"));

    }
}


