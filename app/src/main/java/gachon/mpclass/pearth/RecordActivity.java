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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    public String uid;
    int cnt=0;
    int water=0;
    int elec=0;
    int plant=0;
    int air=0;
    int volun=0;
    int zero=0;
    int harm=0;
    int eat=0;
    int count =0;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    DatabaseReference mDB2=FirebaseDatabase.getInstance().getReference().child("Users");
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private FirebaseAuth firebaseAuth;
    DatabaseReference mDB=FirebaseDatabase.getInstance().getReference().child("Tag");
    private ListView listView;
    ArrayList<ListViewItem> item=new ArrayList<ListViewItem>();
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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

        uid = user.getUid();
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
                int index = G.keyList.indexOf(dataSnapshot.getKey());
                String Uid =  listViewItem.getUid();
                if(Uid != null){
                    if(Uid.equals(uid)){
                        count=count+1;
                        }
                }
                mDB2.child(user.getUid()).child("report").setValue(count);
                //리스트뷰를 갱신
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount()-1); //리스트뷰의 마지막 위치로 스크롤 위치 이동
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                int index = G.keyList.indexOf(dataSnapshot.getKey());
//                if(listViewItem.getTitle().equals(uid)){
//                    count=3;
//                }
//                mDB2.child(user.getUid()).child("report").push().setValue(count);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                int index = G.keyList.indexOf(dataSnapshot.getKey());
                item.remove(index);
                G.keyList.remove(index);
                adapter.notifyDataSetChanged();
                count=count-1;
                mDB2.child(user.getUid()).child("report").setValue(count);
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
        if (id == R.id.action_plant) {
            Intent plantIntent = new Intent(this, GrowingPlantActivity.class);
            startActivity(plantIntent);
        }
        if(id==R.id.action_checklist)
        {
            Intent intent=new Intent(RecordActivity.this,CheckListActivity.class);
            String uid = user.getUid();
            intent.putExtra("uid",uid);
            startActivity(intent);
        }
        if(id==R.id.action_UserProfile)
        {
            Intent intent=new Intent(RecordActivity.this,UserProfileActivity.class);
            String uid = user.getUid();
            intent.putExtra("uid",uid);
            startActivity(intent);
        }
        if(id==R.id.action_post){
            Intent PostIntent = new Intent(this, Userpost.class);
            startActivity(PostIntent);
        }
        if(id==R.id.action_store) {
            Intent intent = new Intent(this, SetLocationActivity.class);
            String uid = firebaseAuth.getCurrentUser().getUid();
            intent.putExtra("uid", uid);
            startActivity(intent);
        }
        if (id == R.id.action_back) {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}
