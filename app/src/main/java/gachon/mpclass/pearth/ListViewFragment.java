package gachon.mpclass.pearth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ListViewFragment extends Fragment {
    private static final String STORESKEY = "stores_key";
    private ArrayList<Store> listStores = new ArrayList<Store>();

    ListView listView;
    ScrollView scrollView;
    String SIGUN = "";
    String DONG = "";
    TextView list_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = (ViewGroup) inflater.inflate(R.layout.fragment_listview, container, false);

        listView = (ListView)rootView.findViewById(R.id.listview);
        scrollView = (ScrollView)rootView.findViewById(R.id.scrollView);
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.listview_header_listview, listView, false);

        scrollView.setScrollbarFadingEnabled(true);



        StoreAdapter storeAdapter = new StoreAdapter();
        listView.addHeaderView(header);
        listView.setAdapter(storeAdapter);

        setData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String item = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void setData(){

    }
}

