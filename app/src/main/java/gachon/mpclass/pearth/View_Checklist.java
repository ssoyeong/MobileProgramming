package gachon.mpclass.pearth;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class View_Checklist extends Fragment {

    ListView listView;
    ArrayList<CheckListData> items;
    Button add;
    EditText new_activity;
    int index=0;
    FirebaseAuth firebaseAuth;
    String date;
    String uid;
    String add_todo;

    public View_Checklist(){

    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        View v=inflater.inflate(R.layout.fragment_view__checklist,container,false);
        Bundle bundle=getArguments();
        uid=bundle.getString("Uid");
        date=bundle.getString("Date");
        items=new ArrayList<CheckListData>();
        items=bundle.getParcelableArrayList("list");

        listView=v.findViewById(R.id.listView);
        CheckList_Adapter mAdapter=new CheckList_Adapter(uid,date,items);
        listView.setAdapter(mAdapter);

        new_activity=v.findViewById(R.id.new_activity);
        add=v.findViewById(R.id.add_btn);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                add_todo=new_activity.getText().toString();
                if(add_todo.matches("")){
                    Toast.makeText(getContext(),"실천할 활동을 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Map<String,Object> hashMap = new HashMap<>();
                    hashMap.put(add_todo,false);
                    reference.child("Checklist").child(uid).child(date).updateChildren(hashMap);
                    new_activity.setText("");


                    reference.child("Checklist").child(uid).child(date).addValueEventListener(new ValueEventListener() {
                        @Override

                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            items.clear();
                            if(snapshot.exists())
                            {
                                for(DataSnapshot itemData:snapshot.getChildren())
                                {

                                    String todo=itemData.getKey().toString(); //할 일 내용
                                    Log.v("todo",todo);
                                    boolean done=Boolean.parseBoolean(itemData.getValue().toString());
                                    Log.v("done",String.valueOf(done));
                                    CheckListData item=new CheckListData(todo,done);
                                    Log.v("CheckList_item", item.CheckList_item);
                                    Log.v("CheckList_done",String.valueOf(item.CheckList_done));
                                    items.add(item);

                                }


                                CheckList_Adapter mAdapter=new CheckList_Adapter(uid,date,items);
                                listView.setAdapter(mAdapter);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        return v;
    }


}