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
        Button search = (Button) findViewById(R.id.search);
        EditText keyword = (EditText) findViewById(R.id.keyword);

        Nosearchfragment = new nosearchfragment();
        G.keyword = keyword.getText().toString();
        key = G.keyword;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, Nosearchfragment).commit();
        Postfragment = new postfragment();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    G.keyword = keyword.getText().toString();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, Postfragment).commit();
                    keyword.setText("");

            }
        });


    }

    public void onFragmentChanged(int index) {
        if (index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, Nosearchfragment).commit();
        }

        if (index == 1) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, Postfragment).commit();
    }


}}