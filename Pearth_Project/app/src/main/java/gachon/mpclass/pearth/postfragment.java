package gachon.mpclass.pearth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class postfragment extends Fragment implements View.OnClickListener{
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    ArrayList<ListViewItem_shareboard> item=new ArrayList<ListViewItem_shareboard>();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public String uid;
    private ChildEventListener mChild;
    DatabaseReference mDB2=FirebaseDatabase.getInstance().getReference().child("Users");
    int count=0;
    ListViewItem_shareboard listViewItem2;
    private ListView listView;
    String key;
    EditText keyword;

    public postfragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        item.clear();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.frament_post_search, container, false);

        Button search = (Button) rootView.findViewById(R.id.search);
        FloatingActionButton refresh = (FloatingActionButton) rootView.findViewById(R.id.refresh);
        keyword = (EditText) rootView.findViewById(R.id.keyword);
        G.keyword = keyword.getText().toString();
        search.setOnClickListener(this);
        refresh.setOnClickListener(this);


        listView = (ListView) rootView.findViewById(R.id.listview1);
        initDatabase();

//        searchActivity activity = (searchActivity) getActivity();
//        activity.onFragmentChanged(1);

        //새롭게 받은 방법,,

//        if(count==0){
//            searchActivity activity = (searchActivity) getActivity();
//            activity.onFragmentChanged(0);
//        }

        return rootView;
    }
    @Override
    public void onClick(View v){
        switch (v.getId())
        {
            case R.id.refresh:
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
                keyword.setText("");
                break;
            }
            case R.id.search:
            {

                G.keyword = keyword.getText().toString();

                ListViewAdapter_search adapter=new ListViewAdapter_search(item);
                listView.setAdapter(adapter);

                mReference=mDatabase.getReference("share");
                mReference.addChildEventListener(new ChildEventListener() {
                    //새로 추가된 것만 줌 ValueListener는 하나의 값만 바뀌어도 처음부터 다시 값을 줌
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        //새로 추가된 데이터(값 : MessageItem객체) 가져오기
                        ListViewItem_shareboard listViewItem= dataSnapshot.getValue(ListViewItem_shareboard.class);
                        String title = listViewItem.getTitle();
                        String content = listViewItem.getContent();
                        String Uid = listViewItem.getUid();
                        //새로운 메세지를 리스뷰에 추가하기 위해 ArrayList에 추가
                        if(G.keyword!=""){

                            if(title.contains(G.keyword)||content.contains(G.keyword)){
                                item.add(listViewItem);
                                G.keyList.add(dataSnapshot.getKey());
                                count = count+1;
                                int index = G.keyList.indexOf(dataSnapshot.getKey());

                            }
                        }
//                if(Uid != null){
//                    if(Uid.equals(uid)){
//                        count=count+1;}
//                }
//                mDB2.child(user.getUid()).child("share").setValue(count);
                        //리스트뷰를 갱신
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                        listView.setSelection(adapter.getCount()-1); //리스트뷰의 마지막 위치로 스크롤 위치 이동

                    }



                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            }
        }
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




}