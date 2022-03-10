package gachon.mpclass.pearth;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

public class searchActivity extends AppCompatActivity {


    boolean boo = false;
    postfragment Postfragment;
    nosearchfragment Nosearchfragment;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_search);
        getSupportActionBar().setTitle("검색");

        Postfragment = new postfragment();


        getSupportFragmentManager().beginTransaction().replace(R.id.container, Postfragment).commit();


    }

//    public void onFragmentChanged(int index) {
//        if (index == 0) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, Nosearchfragment).commit();
//        }
//
//        if (index == 1) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, Postfragment).commit();
//        }
//
//
//    }
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


        if (id == R.id.action_back) {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}