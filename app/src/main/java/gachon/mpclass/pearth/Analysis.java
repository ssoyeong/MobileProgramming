package gachon.mpclass.pearth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Analysis extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventLstener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    ChildEventListener mChild;
    int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("분석");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        TextView water = (TextView) findViewById(R.id.water);
        TextView elec = (TextView) findViewById(R.id.elec);
        TextView air = (TextView) findViewById(R.id.air);
        TextView plant = (TextView) findViewById(R.id.plant);
        TextView volun = (TextView) findViewById(R.id.volunteer);
        TextView zero = (TextView) findViewById(R.id.zero_waste);
        TextView harm = (TextView) findViewById(R.id.toxic);
        TextView eat = (TextView) findViewById(R.id.eat);
        initDatabase();
        mRef=mDatabase.getReference("Tag");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot messageData : snapshot.getChildren())
                    {
                        String msg=messageData.getValue().toString();
                        if(count==0) {
                            air.setText("#깨끗한공기 : " + msg.substring(0,1) + "회");
                            count++;
                        }
                        else if(count==1) {
                            eat.setText("#친환경식습관 : " + msg.substring(0,1) + "회");
                            count++;
                        }
                        else if(count==2){
                            elec.setText("#전기절약 : "+msg.substring(0,1)+"회");
                            count++;
                        }
                        else if(count==3){
                            harm.setText("#유해물질안전폐기 : "+msg.substring(0,1)+"회");
                            count++;
                        }
                        else if(count==4){
                            plant.setText("#식물심기 : "+msg.substring(0,1)+"회");
                            count++;
                        }
                        else if(count==5){
                            volun.setText("#환경자원봉사 : "+msg.substring(0,1)+"회");
                            count++;
                        }
                        else if(count==6){
                            water.setText("#물절약 : "+msg.substring(0,1)+"회");
                            count++;
                        }
                        else if(count==7){
                            zero.setText("#제로웨이스트 : "+msg.substring(0,1)+"회");
                            count++;
                        }
                    }
                }
                else
                {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initDatabase() {

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("log2");
        mRef.child("log").setValue("check");

        mChild = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mRef.addChildEventListener(mChild);
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
            Intent homeIntent = new Intent(this, MainActivity.class);
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



        return super.onOptionsItemSelected(item);
    }
}