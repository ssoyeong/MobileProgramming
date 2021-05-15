package gachon.mpclass.pearth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Userpost extends AppCompatActivity {
    Recordfragemnt recordfragment;
    Sharefragment sharefragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpost);
        recordfragment=new Recordfragemnt();
        sharefragment=new Sharefragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.container, recordfragment);
        fragmentTransaction.commit();
        Button record=(Button)findViewById(R.id.record);
        Button share=(Button)findViewById(R.id.share);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction().replace(R.id.container,recordfragment).commit();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container,sharefragment).commit();
            }
        });
    }
}