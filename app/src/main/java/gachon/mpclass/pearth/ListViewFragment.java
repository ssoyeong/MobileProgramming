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
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ListViewFragment extends Fragment {
    private static final String STORESKEY = "stores_key";
    private ArrayList<Store> listStores = new ArrayList<Store>();

    ListView listView;
    //    ScrollView scrollView;
    String SIGUN = "";
    String DONG = "";
    TextView list_title;
    View rootView;
    StoreAdapter storeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_listview, container, false);

//        ActionBar actionBar = ((MapActivity)getActivity()).getSupportActionBar();
//        actionBar.setTitle("리스트 보기");
//        actionBar.setDisplayHomeAsUpEnabled(false);
        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();


        if(listStores.size() == 0) {

            listStores.add(new Store("걸구쟁이네", "37.464159", "127.12277", "한식당", "서울특별시 송파구 문정동 송파대로 111", "02-401-4320"));
            listStores.add(new Store("스윗솔", "37.50872", "127.08157", "비건 채식 레스토랑", "서울특별시 송파구 잠실동 225번지 자연빌라 2층 201호", "070-8888-3816"));
            listStores.add(new Store("블렌드랩", "37.50129", "127.10353", "카페", "서울특별시 송파구 석촌동 257 1층", "070-4922-2700"));
            listStores.add(new Store("씨젬므주르", "37.50817", "127.1069", "비건 프렌치 레스토랑", "서울특별시 송파구 송파동 백제고분로41길 25", "050713474142"));
            listStores.add(new Store("제로비건", "37.51308", "127.09642", "채식 전문식당", "서울특별시 송파구 잠실3동 올림픽로 240", "02-2143-1609"));
            listStores.add(new Store("해피비건", "37.49582", "127.12215", "화장품 산업", "서울특별시 송파구 가락동 송파대로28길 43", "070-8800-7766"));
            listStores.add(new Store("닥터비건", "37.52199", "127.04464", "비건 채식 레스토랑", "서울특별시 강남구 청담동 17-7", "02-543-2030"));
            listStores.add(new Store("비건이삼", "37.51508", "127.04876", "비건 베이커리", "서울특별시 강남구 삼성동 26-33", "050713634460"));

        }



        listView = (ListView)rootView.findViewById(R.id.listView);
        storeAdapter = new StoreAdapter(getLayoutInflater(), listStores);
        listView.setAdapter(storeAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    Intent intent = new Intent(getActivity(), PopUpActivity.class);

                    Store obj = (Store) listView.getAdapter().getItem(position);

                    Bundle mybundle = new Bundle();
                    mybundle.putParcelable("store", obj);
                    mybundle.putString("name", obj.getName());
                    mybundle.putString("address", obj.getAddr());
                    mybundle.putString("telephone", obj.getTel());
                    mybundle.putString("type", obj.getType());
                    intent.putExtras(mybundle);
                    startActivity(intent);
                }

            }
        });
    }
}
