package gachon.mpclass.pearth;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;



public class StoreAdapter extends BaseAdapter {

    private ArrayList<Store> listStore = new ArrayList<>();

    @Override
    public int getCount() {
        return listStore.size();
    }

    public Object getItem(int position) {
        return listStore.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class UserViewHolder {
    public TextView name;
    public TextView type;
    public TextView address;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        }

        UserViewHolder viewHolder;
        viewHolder = new UserViewHolder();
        viewHolder.name = (TextView) convertView.findViewById(R.id.store_list_name);
        viewHolder.type = (TextView) convertView.findViewById(R.id.store_list_type);
        viewHolder.address = (TextView) convertView.findViewById(R.id.store_list_address);


        //Voca 객체 리스트의 position 위치에 있는 Voca 객체를 가져옵니다.
        Store store = (Store) listStore.get(position);


        //현재 선택된 Vocal 객체를 화면에 보여주기 위해서 앞에서 미리 찾아 놓은 뷰에 데이터를 집어넣습니다.
        viewHolder.name.setText(store.getName());
        viewHolder.type.setText(store.getType());
        viewHolder.address.setText(store.getAddr());

        return convertView;
    }

}