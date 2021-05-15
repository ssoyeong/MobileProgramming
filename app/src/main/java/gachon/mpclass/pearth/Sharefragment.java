package gachon.mpclass.pearth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Sharefragment extends Fragment {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    ArrayList<ListViewItem_shareboard> item=new ArrayList<ListViewItem_shareboard>();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public String uid;
    private ChildEventListener mChild;
    DatabaseReference mDB2=FirebaseDatabase.getInstance().getReference().child("Users");
    int count=0;
    ListViewItem_shareboard listViewItem2;


    public Sharefragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        item.clear();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sharefragment, container, false);
            String uid = user.getUid();
            ListView listView = (ListView) rootView.findViewById(R.id.listview1);
            initDatabase();

            ListViewAdapter_shareboard adapter = new ListViewAdapter_shareboard(item);
            listView.setAdapter(adapter);

            mReference = mDatabase.getReference("share");
            //새롭게 받은 방법,,
            mReference.addChildEventListener(new ChildEventListener() {
                //새로 추가된 것만 줌 ValueListener는 하나의 값만 바뀌어도 처음부터 다시 값을 줌
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ListViewItem_shareboard listViewItem = dataSnapshot.getValue(ListViewItem_shareboard.class);

                    //새로 추가된 데이터(값 : MessageItem객체) 가져오기
                        String Uid = listViewItem.getUid();
                        //새로운 메세지를 리스뷰에 추가하기 위해 ArrayList에 추가
                            if (Uid != null) {
                                if (Uid.equals(uid)) {
                                    item.add(listViewItem);
                                    G.keyList.add(dataSnapshot.getKey());
                                    int index = G.keyList.indexOf(dataSnapshot.getKey());
                                    count = count + 1;
                                }
                            }

                    adapter.notifyDataSetChanged();
                    listView.setSelection(adapter.getCount() - 1); //리스트뷰의 마지막 위치로 스크롤 위치 이동
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
                    count = count - 1;
                    mDB2.child(user.getUid()).child("share").setValue(count);
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return rootView;
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