package gachon.mpclass.pearth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {

    public static Context context;

    // Fragments
    ListViewFragment listViewFragment;
    MapFragment mapFragment;
   // SearchFragment searchFragment;
    FavoriteFragment favoriteFragment;

    // Buttons
    Button list_btn;
    Button map_btn;
   // Button search_btn;
    Button favorite_btn;
    Button findAdr_btn;

    TextView loadingMessage;
    ArrayList<Store> stores = new ArrayList<>();
    String SIGUN = "";
    String DONG = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        context = this;

//        Toolbar toolbar = findViewById(R.id.my_toolbar);
//        //toolbar.setTitleTextColor(Color.parseColor("#ffff33"));
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
//


        map_btn = (Button) findViewById(R.id.map_btn);
        list_btn = (Button) findViewById(R.id.list_btn);
        // search_btn = (Button) findViewById(R.id.search_btn);
        findAdr_btn = (Button) findViewById(R.id.findAdr_btn);
        favorite_btn=(Button) findViewById(R.id.favorite_btn);

        loadingMessage = (TextView) findViewById(R.id.loadingMessage);

        listViewFragment = new ListViewFragment();
        mapFragment = new MapFragment();
       // searchFragment = new SearchFragment();
        favoriteFragment = new FavoriteFragment();
//
//        Intent data = getIntent();
//        if(data != null) {
//            Bundle bundle = data.getExtras();
//            SIGUN = bundle.getString("SIGUN");
//            DONG = bundle.getString(("DONG"));
//            String location = SIGUN + " " + DONG;
//            findAdr_btn.setText(location);
//
////            stores = bundle.getParcelableArrayList("all_stores");
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                    GetApi parser = new GetApi();
//                    stores = parser.getAllXmlData(SIGUN, DONG);
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            loadingMessage.setText("");
//                            list_btn.performClick();
//                        }
//                    });
//                }
//            }).start();
//        }
//
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingMessage.setText("");
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("stores_key", stores);
                bundle.putString("SIGUN", SIGUN);
                bundle.putString("DONG", DONG);
                 mapFragment.setArguments(bundle);
                 getSupportFragmentManager().beginTransaction().replace(R.id.container, mapFragment).commit();
                map_btn.setBackgroundColor(Color.parseColor("#F2F2F2"));
                list_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
                //search_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
                favorite_btn.setBackgroundColor(Color.parseColor("#00ff0000"));

            }
        });


        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingMessage.setText("");
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("stores_key", stores);
                bundle.putString("SIGUN", SIGUN);
                bundle.putString("DONG", DONG);
                listViewFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, listViewFragment).commit();
                map_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
                list_btn.setBackgroundColor(Color.parseColor("#F2F2F2"));
                //search_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
                favorite_btn.setBackgroundColor(Color.parseColor("#00ff0000"));

            }
        });


        favorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingMessage.setText("");
                getSupportFragmentManager().beginTransaction().replace(R.id.container, favoriteFragment).commit();
                map_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
                list_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
                //search_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
                favorite_btn.setBackgroundColor(Color.parseColor("#F2F2F2"));
            }
        });

    }


}