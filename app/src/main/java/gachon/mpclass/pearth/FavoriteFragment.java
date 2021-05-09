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
    ScrollView scrollView;
    ArrayList<Store> stores;
    String SIGUN = "";
    String DONG = "";
    TextView list_title;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = (ViewGroup) inflater.inflate(R.layout.fragment_listview, container, false);

        listView = (ListView) rootView.findViewById(R.id.listview);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.listview_header_favorite, listView, false);

        scrollView.setScrollbarFadingEnabled(true);

//        stores = new ArrayList<Store>();
//        if (getStoreArrayPref(getActivity(), SETTINGS_PLAYER_JSON) != null) {
//            stores = getStoreArrayPref(getActivity(), SETTINGS_PLAYER_JSON);
//        } else {
//            TextView loadingMessage = getActivity().findViewById(R.id.loadingMessage);
//            loadingMessage.setText("즐겨찾기 매장이 존재해지 않습니다.");
//        }
//
//        ArrayAdapter<Store> adapter = new StoreAdapter(getActivity(), stores, listView);
//        listView.addHeaderView(header);
//        listView.setAdapter(adapter);
//
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
//                mybundle.putString("SIGUN", SIGUN);
//                mybundle.putString("DONG", DONG);
//                intent.putExtras(mybundle);
//                startActivity(intent);
//
//
//            }
//        });

        /*ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), R.layout.listviewitem, names);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), PopUpActivity.class);

                Object obj = listView.getAdapter().getItem(position);
                String value = obj.toString();

                Bundle mybundle = new Bundle();
                mybundle.putString("name", value);
                intent.putExtras(mybundle);
                startActivity(intent);


            }
        });*/
        return rootView;
    }

//    private ArrayList<Store> getStoreArrayPref(Context context, String key) {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        String json = prefs.getString(key, null);
//        Gson gson = new Gson();
//        TypeToken<List<Store>> typeToken = new TypeToken<List<Store>>() {
//        };
//        ArrayList<Store> data = gson.fromJson(json, typeToken.getType());
//
//        return data;
//    }
}


