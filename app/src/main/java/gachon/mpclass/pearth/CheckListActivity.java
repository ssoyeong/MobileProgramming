package gachon.mpclass.pearth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

//체크리스트 전체 activity
public class CheckListActivity extends AppCompatActivity {

    CalendarView calendar;
    FragmentManager manager;
    FragmentTransaction ft;
    String date;
    Modify_Checklist modify_checklist;
    View_Checklist view_checklist;
    BlankFragment blank;
    TextView date_tv;
    FirebaseAuth firebaseAuth;
    ArrayList<CheckListData> items;
    Button modify;
    String s;
    String list;
    String todo;
    boolean done;
    CheckList checkList;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        manager = getSupportFragmentManager();
        modify_checklist = new Modify_Checklist();
        view_checklist = new View_Checklist();
        blank = new BlankFragment();
        modify = (Button) findViewById(R.id.modify);

        date_tv = (TextView) findViewById(R.id.date);

        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        items = new ArrayList<>();


        calendar = (CalendarView) findViewById(R.id.calendar);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {


                ft = manager.beginTransaction();
                ft.replace(R.id.fragment_container, blank);
                ft.commit();

                items.clear();
                date = String.format("%d %d %d", year, month + 1, dayOfMonth);
                Log.v("date", date);
                date_tv.setText(year+"년 "+(month+1)+"월 "+dayOfMonth+"일");
                Bundle bundle = new Bundle();
                bundle.putString("Uid", uid);
                bundle.putString("Date", date);
                date = date.toString();

                reference.child("Checklist").child(uid).child(date).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        items.clear(); //여기엔 필요한지 안필요한지 아직 모르겠다 . todo
                        if (!snapshot.exists()) {
                            //data doesn't exist
                            //reference.child("Checklist").child(uid).setValue(date);
                            //items=new ArrayList<Checklist_Item>();
                            TypedArray arrayText = getResources().obtainTypedArray(R.array.checklist);
                            Random random = new Random();
                            int a[] = new int[3];
                            int index;
                            HashMap<String, Boolean> hashMap = new HashMap<>();
                            for (int i = 0; i < 3; i++) {
                                a[i] = random.nextInt(16);
                                for (int j = 0; j < i; j++) {
                                    if (a[i] == a[j]) {
                                        i--;
                                    }
                                }
                                boolean done = false;
                                s = arrayText.getString(a[i]);
                                //String input = s + "@" + done;
                                Log.v("체크리스트", s);
                                //String number = String.valueOf(i);
                                hashMap.put(s, done);

                            }
                            reference.child("Checklist").child(uid).child(date).setValue(hashMap);
                            arrayText.recycle();
                            Log.v("view_checklist", "data doesn't exist");

                        }
                        else {
                            //data exists
                            Log.v("view_checklist", "data exists");
                            bundle.putString("Uid", uid);
                            bundle.putString("Date", date);
                            reference.child("Checklist").child(uid).child(date).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    items.clear();
                                    if (snapshot.exists()) {
                                        for (DataSnapshot itemData : snapshot.getChildren()) {
                                            todo = itemData.getKey().toString(); //할 일 내용
                                            Log.v("todo", todo);
                                            boolean done = Boolean.parseBoolean(itemData.getValue().toString());
                                            Log.v("done", String.valueOf(done));
                                            CheckListData item = new CheckListData(todo, done);
                                            Log.v("CheckList_item", item.CheckList_item);
                                            Log.v("CheckList_done", String.valueOf(item.CheckList_done));
                                            items.add(item);

                                        }
                                        for (CheckListData it : items) {
                                            Log.v("content", it.CheckList_item);
                                            Log.v("done", String.valueOf(it.CheckList_done));
                                        }
//                                            checkList=new CheckList();
//                                            checkList.setCheckListData(items);
                                        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) items);
                                        view_checklist.setArguments(bundle);
                                        ft = manager.beginTransaction();
                                        ft.replace(R.id.fragment_container, view_checklist);
                                        ft.commit();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        modify.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                items.clear();
                Bundle bundle=new Bundle();
                bundle.putString("Uid", uid);
                bundle.putString("Date", date);
                reference.child("Checklist").child(uid).child(date).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        items.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot itemData : snapshot.getChildren()) {
                                todo = itemData.getKey().toString(); //할 일 내용
                                Log.v("m_todo", todo);
                                boolean done = Boolean.parseBoolean(itemData.getValue().toString());
                                Log.v("m_done", String.valueOf(done));
                                CheckListData item = new CheckListData(todo, done);

                                items.add(item);

                            }

                            bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) items);
                            modify_checklist.setArguments(bundle);
                            ft = manager.beginTransaction();
                            ft.replace(R.id.fragment_container, modify_checklist);
                            ft.commit();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}