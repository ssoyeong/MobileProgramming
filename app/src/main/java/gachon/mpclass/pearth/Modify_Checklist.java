package gachon.mpclass.pearth;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//체크리스트 수정(삭제) 가능하게 하기 위한 fragment - PreparationFragment.java

public class Modify_Checklist extends Fragment {

    ListView listView;
    ArrayList<CheckListData> items;
    Button delete;
    EditText new_activity;
    int index=0;
    FirebaseAuth firebaseAuth;
    String date;
    String uid;
    String add_todo;
    SparseBooleanArray checkedItems=new SparseBooleanArray();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();

    public Modify_Checklist(){

    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
        Log.v("fragment","view_checklist");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        View v=inflater.inflate(R.layout.fragment_modify__checklist,container,false);
        Bundle bundle=getArguments();
        uid=bundle.getString("Uid");
        date=bundle.getString("Date");
        items=new ArrayList<CheckListData>();
        items=bundle.getParcelableArrayList("list");
        //initItems();
        listView=v.findViewById(R.id.listView);
        CheckList_Adapter mAdapter=new CheckList_Adapter(uid,date,items);
        listView.setAdapter(mAdapter);


        delete=(Button)v.findViewById(R.id.delete_btn);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                reference.child("Checklist").child(uid).child(date).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            for(DataSnapshot itemData:snapshot.getChildren())
                            {
                                String todo=itemData.getKey().toString(); //할 일 내용
                                Log.v("todo",todo);
                                boolean done=Boolean.parseBoolean(itemData.getValue().toString());
                                Log.v("done",String.valueOf(done));
                                //체크박스 true인 목록(데이터 삭제)
                                if(done==true)
                                {
                                    reference.child("Checklist").child(uid).child(date).child(todo).setValue(null);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        return v;

    }
}