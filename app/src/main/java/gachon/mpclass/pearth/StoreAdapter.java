package gachon.mpclass.pearth;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;




public class StoreAdapter extends BaseAdapter {

    private ArrayList<Store> listStore = new ArrayList<>();

    LayoutInflater inflater;

    public StoreAdapter(LayoutInflater inflater, ArrayList<Store> listStore){
        this.listStore = listStore;
        this.inflater = inflater;
    }


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


        Store store = (Store) listStore.get(position);

        viewHolder.name.setText(store.getName());
        viewHolder.type.setText(store.getType());
        viewHolder.address.setText(store.getAddr());

        return convertView;
    }

    public void clearData(){
        listStore.clear();
    }

}