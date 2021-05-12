package gachon.mpclass.pearth;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class PopUpActivity extends Activity {

    private
    TextView store_name;
    TextView store_type;
    TextView store_addr;
    TextView store_tel;
    Button map_btn;
    Button call_btn;
    Button ok_btn;
    Button favorite_btn;
    String SIGUN = "";
    String DONG = "";

    String name;
    String address;
    String telephone;
    String type;

    Store store;
    ArrayList<Store> stores;
    Store bundleStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);


        store_name = (TextView) findViewById(R.id.store_name);
        store_type = (TextView) findViewById(R.id.store_type);
        store_addr = (TextView) findViewById(R.id.store_addr);
        store_tel = (TextView) findViewById(R.id.store_tel);



        map_btn = (Button) findViewById(R.id.map_btn);
        call_btn = (Button) findViewById(R.id.call_btn);
        favorite_btn = (Button) findViewById(R.id.favorite_btn);
        ok_btn = (Button) findViewById(R.id.ok_btn);

        stores = new ArrayList<Store>();

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





        stores = new ArrayList<Store>();
//        stores.add(new Store("걸구쟁이네", "37.464159", "127.12277", "한식당", "서울특별시 송파구 문정동 송파대로 111", "02-401-4320"));
//        stores.add(new Store("스윗솔", "37.50872", "127.08157", "비건 채식 레스토랑", "서울특별시 송파구 잠실동 225번지 자연빌라 2층 201호", "070-8888-3816"));
//        stores.add(new Store("블렌드랩", "37.50129", "127.10353", "카페", "서울특별시 송파구 석촌동 257 1층", "070-4922-2700"));
//        stores.add(new Store("씨젬므주르", "37.50817", "127.1069", "비건 프렌치 레스토랑", "서울특별시 송파구 송파동 백제고분로41길 25", "050713474142"));
//        stores.add(new Store("제로비건", "37.51308", "127.09642", "채식 전문식당", "서울특별시 송파구 잠실3동 올림픽로 240", "02-2143-1609"));
//        stores.add(new Store("해피비건", "37.49582", "127.12215", "화장품 산업", "서울특별시 송파구 가락동 송파대로28길 43", "070-8800-7766"));
//        stores.add(new Store("닥터비건", "37.52199", "127.04464", "비건 채식 레스토랑", "서울특별시 강남구 청담동 17-7", "02-543-2030"));
//        stores.add(new Store("비건이삼", "37.51508", "127.04876", "비건 베이커리", "서울특별시 강남구 삼성동 26-33", "050713634460"));






        favorite_btn.setOnClickListener(new View.OnClickListener(){
            int flag = 0;
            @Override
            public void onClick(View view){





                for (Store str : stores) {
                    if (str.getName().equals(store.getName())) {
                        if (store.getType() == null || str.getType() == null) {
                            if (str.getAddr() == null || store.getAddr() == null) {
                                flag = 1;
                                break;
                            } else if (str.getAddr() != null && store.getAddr() != null) {
                                if (str.getAddr().equals(store.getAddr())) {
                                    flag = 1;
                                    break;
                                } else
                                    flag = 0;
                            }
                        } else if (str.getType() != null && store.getType() != null) {
                            if (str.getType().equals(store.getType())) {
                                if (str.getAddr() == null || store.getAddr() == null) {
                                    flag = 1;
                                    break;
                                } else if (str.getAddr() != null && store.getAddr() != null) {
                                    if (str.getAddr().equals(store.getAddr())) {
                                        flag = 1;
                                        break;
                                    } else
                                        flag = 0;
                                }
                            } else
                                flag = 0;
                        }
                    } else {
                        flag = 0;
                    }
                }







                if(flag == 0){
                    favorite_btn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_black_24dp, 0, 0);
                    stores.add(store);
                    Toast.makeText(PopUpActivity.this, "즐겨찾기 추가", Toast.LENGTH_LONG).show();

                }
                else if(flag == 1){
                    stores.remove(store);
                    favorite_btn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_border_black_24dp, 0, 0);
                    Toast.makeText(PopUpActivity.this, "즐겨찾기 해제", Toast.LENGTH_LONG).show();


                }



//                bundleStore = new Store();
//                bundleStore.setStores(store);
                Intent intent = new Intent(PopUpActivity.this, FavoriteFragment.class);

                intent.putExtra("store", store);
                intent.putExtra("flag", flag);
//                startActivity(intent);
// todo: favoritefragment로 연결 어려움. 파베 써야할 듯,,,,,

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
