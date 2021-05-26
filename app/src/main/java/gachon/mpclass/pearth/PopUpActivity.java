package gachon.mpclass.pearth;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class PopUpActivity extends Activity {

    TextView store_name;
    TextView store_type;
    TextView store_addr;
    TextView store_tel;
    Button map_btn;
    Button call_btn;
    Button ok_btn;
    Button favorite_btn;

    private String name;
    private String address;
    private String telephone;
    private String type;
    private int index;

    Store store;
    ArrayList<Store> storeList;
    ArrayList<String> favorites;

    private FirebaseAuth firebaseAuth;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("Users");
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        uid = user.getUid();
        Log.d("uid", uid);

        store_name = (TextView) findViewById(R.id.store_name);
        store_type = (TextView) findViewById(R.id.store_type);
        store_addr = (TextView) findViewById(R.id.store_addr);
        store_tel = (TextView) findViewById(R.id.store_tel);

        map_btn = (Button) findViewById(R.id.map_btn);
        call_btn = (Button) findViewById(R.id.call_btn);
        favorite_btn = (Button) findViewById(R.id.favorite_btn);
        ok_btn = (Button) findViewById(R.id.ok_btn);

        storeList = new ArrayList<Store>();
        storeList.add(new Store("걸구쟁이네", "37.464159", "127.12277", "한식당", "서울특별시 송파구 문정동 송파대로 111", "02-401-4320"));
        storeList.add(new Store("스윗솔", "37.50872", "127.08157", "비건 채식 레스토랑", "서울특별시 송파구 잠실동 225번지 자연빌라 2층 201호", "070-8888-3816"));
        storeList.add(new Store("블렌드랩", "37.50129", "127.10353", "카페", "서울특별시 송파구 석촌동 257 1층", "070-4922-2700"));
        storeList.add(new Store("씨젬므주르", "37.50817", "127.1069", "비건 프렌치 레스토랑", "서울특별시 송파구 송파동 백제고분로41길 25", "050713474142"));
        storeList.add(new Store("제로비건", "37.51308", "127.09642", "채식 전문식당", "서울특별시 송파구 잠실3동 올림픽로 240", "02-2143-1609"));
        storeList.add(new Store("해피비건", "37.49582", "127.12215", "화장품 산업", "서울특별시 송파구 가락동 송파대로28길 43", "070-8800-7766"));
        storeList.add(new Store("채식코스요리전문점&굴", "37.48567", "127.12386", "한식당", "서울특별시 송파구 문정동 61-13", "02-400-9052"));
        storeList.add(new Store("닥터비건", "37.52199", "127.04464", "비건 채식 레스토랑", "서울특별시 강남구 청담동 17-7", "02-543-2030"));
        storeList.add(new Store("비건이삼", "37.51508", "127.04876", "비건 베이커리", "서울특별시 강남구 삼성동 26-33", "050713634460"));
        storeList.add(new Store("러빙헛카페", "37.4769", "127.04938", "비건 채식 레스토랑", "서울특별시 강남구 개포동 개포로22길 35", "02-576-2158"));
        storeList.add(new Store("베지 그린", "37.47698", "127.04734", "비건 채식 레스토랑", "서울특별시 강남구 개포동 개포로20길 24-10", "02-577-6316"));
        storeList.add(new Store("스타일비건", "37.51845", "127.038", "음식점", "서울특별시 강남구 논현동 108", "1800-2361"));
        storeList.add(new Store("평상시", "37.55355", "127.13361", "카페", "서울특별시 강동구 암사동 458-67", "070-7655-0005"));
        storeList.add(new Store("에티컬테이블", "37.45816", "127.126547", "채식 전문 음식점", "경기도 성남시 복정동 685-11 KR 2층", "050-7132-7625"));
        storeList.add(new Store("뜰안채", "37.464159", "127.140789", "채식 뷔페 음식점", "경기도 성남시 수정구 복정동", "031-759-4670"));

        Intent data = getIntent();
        if (data != null) {

            Bundle bundle = data.getExtras();
            store = bundle.getParcelable("store");
            name = bundle.getString("name");
            address = bundle.getString("address");
            telephone = bundle.getString("telephone");
            type = bundle.getString("type");

            store_name.setText(store.getName());
            store_addr.setText(store.getAddr());
            store_tel.setText(store.getTel());
            store_type.setText(store.getType());
        }

        for (Store s : storeList){
            if(s.getName().equals(name)){
                index = storeList.indexOf(s);
            }
        }

        favorites = new ArrayList<>();
        conditionRef.child(uid).child("favorite").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot data : snapshot.getChildren()) {

                    if(!favorites.contains(data.getKey())) {
                        favorites.add(data.getKey());
                    }
                }

                System.out.println(favorites);
                if(favorites.contains(name)){
                    favorite_btn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_black_24dp, 0, 0);
                }
                else{
                    favorite_btn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_border_black_24dp, 0, 0);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListViewFragment.class);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });


        call_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String phone = store_tel.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(("tel:" + phone)));
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "전화 걸기", Toast.LENGTH_LONG).show();
            }
        });


        map_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), StoreViewActivity.class);
                Bundle mybundle = new Bundle();
                mybundle.putParcelable("store", store);
                intent.putExtras(mybundle);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "지도 보기", Toast.LENGTH_LONG).show();
            }
        });


        favorite_btn.setOnClickListener(new View.OnClickListener(){

            int flag = 0;
            @Override
            public void onClick(View view){
                conditionRef.child(uid).child("favorite").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        favorites.clear();
                        for(DataSnapshot data : snapshot.getChildren()) {

                            if(!favorites.contains(data.getKey())) {
                                favorites.add(data.getKey());
                            }
                        }

                        if(!favorites.contains(name)) {

                            favorite_btn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_black_24dp, 0, 0);
                            Toast.makeText(PopUpActivity.this, "즐겨찾기 추가", Toast.LENGTH_LONG).show();
                            conditionRef.child(uid).child("favorite").child(name).setValue(index);
                        }
                        else{

                            favorite_btn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_border_black_24dp, 0, 0);
                            Toast.makeText(PopUpActivity.this, "즐겨찾기 해제", Toast.LENGTH_LONG).show();
                            conditionRef.child(uid).child("favorite").child(name).removeValue();
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
