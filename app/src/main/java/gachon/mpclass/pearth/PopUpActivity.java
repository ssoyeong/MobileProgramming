package gachon.mpclass.pearth;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


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

    Store store;
    ArrayList<Store> stores;

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




        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListViewFragment.class);
                setResult(Activity.RESULT_OK, intent);
                finish();
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
