package gachon.mpclass.pearth;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private static final String SETTINGS_PLAYER_JSON = "settings_item_json";
    ListView listView;
    //    ScrollView scrollView;
    ArrayList<Store> stores;
    String SIGUN = "";
    String DONG = "";
    TextView list_title;
    Store bundleStore;
    int flag;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = (ViewGroup) inflater.inflate(R.layout.fragment_listview, container, false);

        listView = (ListView) rootView.findViewById(R.id.listview);
        //        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.listview_header_favorite, listView, false);

        //        scrollView.setScrollbarFadingEnabled(true);








//        ArrayAdapter<Store> adapter = new StoreAdapter(getActivity(), stores, listView);
//        listView.addHeaderView(header);
//        listView.setAdapter(adapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent intent = new Intent(getActivity(), PopUpActivity.class);
//
//                Store obj = (Store) listView.getAdapter().getItem(position);
//
//                SIGUN = obj.getSigun();
//
//                Bundle mybundle = new Bundle();
//                mybundle.putParcelable("store", obj);
//                mybundle.putString("flag", SIGUN);
//                mybundle.putString("DONG", DONG);
//                intent.putExtras(mybundle);
//                startActivity(intent);
//
//
//            }
//        });


        return rootView;
    }

}


