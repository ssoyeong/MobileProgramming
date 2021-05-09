package gachon.mpclass.pearth;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
public class RecordActivity extends AppCompatActivity {
    public String context;
    private FloatingActionButton sendbt;
    private TextView userID;
    public String user;
    int cnt=0;
    int water=0;
    int elec=0;
    int plant=0;
    int air=0;
    int volun=0;
    int zero=0;
    int harm=0;
    int eat=0;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    DatabaseReference mDB=FirebaseDatabase.getInstance().getReference().child("Tag");
    private ListView listView;
    ArrayList<ListViewItem> item=new ArrayList<ListViewItem>();
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        getSupportActionBar().setTitle("기록 게시판");

        //글쓰기 화면으로 넘어가는 버튼
        sendbt = (FloatingActionButton) findViewById(R.id.button2);

        //글쓰기 버튼을 누르면 글 작성 화면으로 넘어감

        sendbt.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SubActivity.class);
            startActivityForResult(intent,1);
        });


        listView = (ListView) findViewById(R.id.listviewmsg);
        initDatabase();

        ListViewAdapter adapter=new ListViewAdapter(item);
        listView.setAdapter(adapter);

        mReference=mDatabase.getReference("board");
        //새롭게 받은 방법,,
        mReference.addChildEventListener(new ChildEventListener() {
            //새로 추가된 것만 줌 ValueListener는 하나의 값만 바뀌어도 처음부터 다시 값을 줌
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //새로 추가된 데이터(값 : MessageItem객체) 가져오기
                ListViewItem listViewItem= dataSnapshot.getValue(ListViewItem.class);

                //새로운 메세지를 리스뷰에 추가하기 위해 ArrayList에 추가
                item.add(listViewItem);
                G.keyList.add(dataSnapshot.getKey());
                if (listViewItem.getTag().indexOf("#물절약") >= 0) {
                    water = water + 1;
                }
                if (listViewItem.getTag().indexOf("#전기절약") >= 0) {
                    elec = elec + 1;
                }
                if (listViewItem.getTag().indexOf("#식물심기") >= 0) {
                    plant = plant + 1;
                }
                if (listViewItem.getTag().indexOf("#깨끗한공기") >= 0) {
                    air = air + 1;
                }
                if (listViewItem.getTag().indexOf("#환경자원봉사") >= 0) {
                    volun = volun + 1;
                }
                if (listViewItem.getTag().indexOf("#제로웨이스트") >= 0) {
                    zero = zero + 1;
                }
                if (listViewItem.getTag().indexOf("#유해물질안전폐기") >= 0) {
                    harm = harm + 1;
                }
                if (listViewItem.getTag().indexOf("#친환경식습관") >= 0) {
                    eat = eat + 1;
                }
                mDB.child("water").setValue(water);
                mDB.child("electricity").setValue(elec);
                mDB.child("plant").setValue(plant);
                mDB.child("air").setValue(air);
                mDB.child("volunteer").setValue(volun);
                mDB.child("zero-waste").setValue(zero);
                mDB.child("harm").setValue(harm);
                mDB.child("eat").setValue(eat);
                //리스트뷰를 갱신
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount()-1); //리스트뷰의 마지막 위치로 스크롤 위치 이동
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                int index = G.keyList.indexOf(dataSnapshot.getKey());
                item.remove(index);
                G.keyList.remove(index);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void initDatabase () {

        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference("log");
        mReference.child("log").setValue("check");

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
        mReference.addChildEventListener(mChild);
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        mReference.removeEventListener(mChild);
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



        return super.onOptionsItemSelected(item);
    }
}
