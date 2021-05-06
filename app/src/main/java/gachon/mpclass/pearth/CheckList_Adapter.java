package gachon.mpclass.pearth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

//import com.fb.firebase_step1.Checklist_Item;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

// 체크리스트 adapter

public class CheckList_Adapter extends BaseAdapter {
    ArrayList<CheckListData> list;
    String user_id;
    String d;
    Context context;
    CheckBox checkBox;

    CheckList_Adapter(String uid,String date,ArrayList<CheckListData> item){
        user_id=uid;
        list=item;
        d=date;
    }

    public int getCount(){
        return list.size();
    }
    public Object getItem(int position){
        return list.get(position);
    }
    public long getItemId(int position){
        return 0;
    }
    public boolean isChecked(int position){
        return list.get(position).CheckList_done;
    }
    public void clearItem(){
        list.clear();
    }
    public View getView(final int position, View convertView, ViewGroup parent){
        Context context=parent.getContext();
        if(convertView==null){
//            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView=inflater.inflate(R.layout.checklist_item,parent,false);
            convertView=LayoutInflater.from(context).inflate(R.layout.checklist_item,null);
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        TextView tv_checklist=convertView.findViewById(R.id.checklist_item);
        checkBox=convertView.findViewById(R.id.checkbox);
        checkBox.setChecked(list.get(position).CheckList_done);
        tv_checklist.setText(list.get(position).CheckList_item);
        checkBox.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view){
                boolean newState= !list.get(position).isChecked();
                list.get(position).CheckList_done=newState;
                reference.child("Checklist").child(user_id).child(d).child(list.get(position).CheckList_item).setValue(list.get(position).CheckList_done);
            }
        });
        checkBox.setChecked(isChecked(position));

        return convertView;
    }
}
