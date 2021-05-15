package gachon.mpclass.pearth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {

    public static Context context;

    // Fragments
    ListViewFragment listViewFragment;
    MapFragment mapFragment;
    FavoriteFragment favoriteFragment;

    // Buttons
    Button list_btn;
    Button map_btn;
    Button favorite_btn;

    TextView loadingMessage;
    ArrayList<Store> stores = new ArrayList<>();
    String SIGUN = "";
    String DONG = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        setContentView(R.layout.activity_map);
        context = this;

        getSupportActionBar().setTitle("추천 비건 음식점");
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("추천 비건 음식점");
        toolbar.setTitleTextColor(Color.BLACK);


        map_btn = (Button) findViewById(R.id.map_btn);
        list_btn = (Button) findViewById(R.id.list_btn);
        favorite_btn=(Button) findViewById(R.id.favorite_btn);

        loadingMessage = (TextView) findViewById(R.id.loadingMessage);

        listViewFragment = new ListViewFragment();
        mapFragment = new MapFragment();
        favoriteFragment = new FavoriteFragment();


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
                favorite_btn.setBackgroundColor(Color.parseColor("#F2F2F2"));
            }
        });

    }



    @Override
    public void onStart(){

        super.onStart();

        loadingMessage.setText("");
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("stores_key", stores);
        bundle.putString("SIGUN", SIGUN);
        bundle.putString("DONG", DONG);
        mapFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, mapFragment).commit();
        map_btn.setBackgroundColor(Color.parseColor("#F2F2F2"));
        list_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
        favorite_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
    }





    //액션버튼 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //액션바 숨기기
    private void hideActionBar () {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_record) {
            Intent homeIntent = new Intent(this, RecordActivity.class);
            startActivity(homeIntent);
        }
        if (id == R.id.action_analysis) {
            Intent settingIntent = new Intent(this, Analysis.class);
            startActivity(settingIntent);
        }
        if (id == R.id.action_share) {
            Intent shareIntent = new Intent(this, Shareboard.class);
            startActivity(shareIntent);
        }
        if (id == R.id.action_plant) {
            Intent plantIntent = new Intent(this, GrowingPlantActivity.class);
            startActivity(plantIntent);
        }
        if(id==R.id.action_checklist)
        {
            Intent intent=new Intent(this,CheckListActivity.class);
//            String uid = firebaseAuth.getCurrentUser().getUid();
//            intent.putExtra("uid",uid);
            startActivity(intent);
        }
        if(id==R.id.action_UserProfile)
        {
            Intent intent=new Intent(this,UserProfileActivity.class);
//            String uid = firebaseAuth.getCurrentUser().getUid();
//            intent.putExtra("uid",uid);
            startActivity(intent);
        }
        if(id==R.id.action_post){
            Intent PostIntent = new Intent(this, Userpost.class);
            startActivity(PostIntent);
        }
        if(id==R.id.action_store) {
            Intent intent = new Intent(this, SetLocationActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_back) {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}