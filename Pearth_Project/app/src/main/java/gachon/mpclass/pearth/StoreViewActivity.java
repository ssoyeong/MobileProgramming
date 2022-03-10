package gachon.mpclass.pearth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class StoreViewActivity extends FragmentActivity implements OnMapReadyCallback {

    ImageButton back_btn;
    TextView store_name;
    TextView store_addr;
    Store store;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_view);

        store_name = (TextView) findViewById(R.id.store_name);
        store_addr = (TextView) findViewById(R.id.store_addr);
        back_btn = (ImageButton) findViewById(R.id.back_btn);

        Intent data = getIntent();
        if (data != null) {
            Bundle bundle = data.getExtras();
            store = bundle.getParcelable("store");
            store_name.setText(store.getName() + "이(가) 선택되었습니다.");
            store_addr.setText(store.getAddr());
        }

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListViewFragment.class);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        Double store_latitude = Double.valueOf(store.getLat());
        Double store_longitude = Double.valueOf(store.getLongt());

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(store_latitude, store_longitude), 14);
        googleMap.animateCamera(cameraUpdate);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(store_latitude, store_longitude)).title(store.getName()).snippet(store.getType()));

    }
}